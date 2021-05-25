
import com.jsuereth.sbtpgp._
import sbt._
import sbt.Keys._

object Settings {

  lazy val customSourceGenerators = TaskKey[Seq[sbt.File]]("custom-source-generators")

  lazy val generateCustomSources = Seq(
    customSourceGenerators := {
      val dir = target.value
      val f = dir / "Properties.scala"
      dir.mkdirs()

      def gitCommit =
        sys.process.Process(Seq("git", "rev-parse", "HEAD")).!!.trim

      val w = new java.io.FileOutputStream(f)
      w.write(
        s"""package plotly.demo
           |
           |object Properties {
           |
           |  val version = "${version.value}"
           |  val commitHash = "$gitCommit"
           |
           |}
         """
          .stripMargin
          .getBytes("UTF-8")
      )
      w.close()

      println(s"Wrote $f")

      val files = new collection.mutable.ArrayBuffer[File]
      files += f

      val tq = "\"\"\""

      def process(destDir: File, pathComponents: Seq[String], file: File): Unit = {
        if (file.isDirectory) {
          val destDir0 = destDir / file.getName
          val pathComponents0 = pathComponents :+ file.getName
          for (f <- file.listFiles())
            process(destDir0, pathComponents0, f)
        } else {
          val lines = new String(java.nio.file.Files.readAllBytes(file.toPath), "UTF-8")
            .linesIterator
            .toVector

          val demoLines = lines
            .dropWhile(!_.contains("demo source start"))
            .drop(1)
            .takeWhile(!_.contains("demo source end"))
            .map(l => l.replace(tq, tq + " + \"\\\"\\\"\\\"\" + " + tq))

          if (demoLines.nonEmpty) {
            val dest = destDir / (file.getName.stripSuffix(".scala") + "Source.scala")
            destDir.mkdirs()
            val w = new java.io.FileOutputStream(dest)
            w.write(
              s"""package plotly${pathComponents.map("." + _).mkString}
                 |
                 |object ${file.getName.stripSuffix(".scala")}Source {
                 |
                 |  val source = $tq${demoLines.mkString("\n")}$tq
                 |
                 |}
               """
                .stripMargin
                .getBytes("UTF-8")
            )
            w.close()

            files += dest
          }
        }
      }

      process(dir / "plotly", Vector(), scalaSource.in(Compile).value / "plotly" / "demo")

      files
    },
    sourceGenerators.in(Compile) += customSourceGenerators.taskValue
  )

  private val scala212 = "2.12.13"
  private val scala213 = "2.13.6"

  private lazy val isAtLeastScala213 = Def.setting {
    import Ordering.Implicits._
    CrossVersion.partialVersion(scalaVersion.value).exists(_ >= (2, 13))
  }

  lazy val shared = Def.settings(
    crossScalaVersions := Seq(scala213, scala212),
    scalaVersion := scala213,
    resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies ++= {
      if (isAtLeastScala213.value) Nil
      else Seq(compilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full))
    },
    scalacOptions ++= Seq("-deprecation", "-feature"),
    scalacOptions ++= {
      if (isAtLeastScala213.value) Seq("-Ymacro-annotations")
      else Nil
    }
  )

  lazy val plotlyPrefix = {
    name := "plotly-" + name.value
  }

  val gitLock = new Object

  def runCommand(cmd: Seq[String], dir: File): Unit = {
    val b = new ProcessBuilder(cmd: _*)
    b.directory(dir)
    b.inheritIO()
    val p = b.start()
    val retCode = p.waitFor()
    if (retCode != 0)
      sys.error(s"Command ${cmd.mkString(" ")} failed (return code $retCode)")
  }


  lazy val fetchTestData = {
    unmanagedResources.in(Test) ++= {
      val log = streams.value.log
      val baseDir = baseDirectory.in(LocalRootProject).value
      val testsPostsDir = baseDir / "plotly-documentation" / "_posts"
      if (!testsPostsDir.exists())
        gitLock.synchronized {
          if (!testsPostsDir.exists()) {
            val cmd = Seq("git", "submodule", "update", "--init", "--recursive", "--", "plotly-documentation")
            log.info("Fetching submodule plotly-documentation (this may take some time)")
            runCommand(cmd, baseDir)
            log.info("Successfully fetched submodule plotly-documentation")
          }
        }
      Nil
    }
  }
}
