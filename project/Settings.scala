
import sbt._
import sbt.Keys._

import Aliases._

object Settings {

  lazy val customSourceGenerators = TaskKey[Seq[sbt.File]]("custom-source-generators")

  lazy val generateCustomSources = Seq(
    customSourceGenerators := {
      var dir = target.value
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

  lazy val shared = Seq(
    organization := "org.plotly-scala",
    scalacOptions ++= {
      if (scalaBinaryVersion.value == "2.12")
        Seq()
      else
        Seq("-target:jvm-1.7")
    },
    resolvers ++= Seq(
      "Webjars Bintray" at "https://dl.bintray.com/webjars/maven/",
      Resolver.sonatypeRepo("releases")
    ),
    publishMavenStyle := true,
    licenses := Seq("LGPL 3.0" -> url("http://opensource.org/licenses/LGPL-3.0")),
    homepage := Some(url("https://github.com/alexarchambault/plotly-scala")),
    pomExtra := {
      <scm>
        <connection>scm:git:github.com/alexarchambault/plotly-scala.git</connection>
        <developerConnection>scm:git:git@github.com:alexarchambault/plotly-scala.git</developerConnection>
        <url>github.com/alexarchambault/plotly-scala.git</url>
      </scm>
        <developers>
          <developer>
            <id>alexarchambault</id>
            <name>Alexandre Archambault</name>
            <url>https://github.com/alexarchambault</url>
          </developer>
        </developers>
    },
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    credentials ++= {
      Seq("SONATYPE_USER", "SONATYPE_PASS").map(sys.env.get) match {
        case Seq(Some(user), Some(pass)) =>
          Seq(Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, pass))
        case _ =>
          Seq()
      }
    }
  )

  lazy val dontPublish = Seq(
    publish := {},
    publishLocal := {},
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
