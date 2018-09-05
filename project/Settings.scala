
import sbt._
import sbt.Keys._

import Aliases._

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

  private val scala212 = "2.12.1"
  private val scala211 = "2.11.8"

  lazy val shared = Seq(
    crossScalaVersions := Seq(scala212, scala211),
    scalaVersion := scala212,
    scalacOptions ++= {
      if (scalaBinaryVersion.value == "2.12")
        Seq()
      else
        Seq("-target:jvm-1.7")
    },
    resolvers ++= Seq(
      "Webjars Bintray" at "https://dl.bintray.com/webjars/maven/",
      Resolver.sonatypeRepo("releases")
    )
  )

  lazy val dontPublish = Seq(
    publish := (),
    publishLocal := (),
    publishArtifact := false
  )

  lazy val plotlyPrefix = {
    name := "plotly-" + name.value
  }

  lazy val utest = Seq(
    libs += Deps.utest.value % "test",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

}
