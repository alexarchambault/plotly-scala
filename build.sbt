
val jupyterScalaVersion = "0.3.0-M3"
val circeVersion = "0.6.0"
val plotlyVersion = "1.12.0"

lazy val core = crossProject
  .settings(commonSettings: _*)
  .settings(
    name := "plotly-core"
  )

lazy val coreJvm = core.jvm
lazy val coreJs = core.js

lazy val `joda-time` = project
  .dependsOn(coreJvm)
  .settings(commonSettings)
  .settings(
    name := "plotly-joda-time",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.1"
    )
  )

lazy val `circe-alt-generic` = crossProject
  .settings(commonSettings: _*)
  .settings(
    name := "circe-alt-generic",
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % "0.6.0",
      "io.circe" %%% "circe-parser" % "0.6.0",
      "org.typelevel" %% "cats" % "0.8.0",
      "com.chuusai" %%% "shapeless" % "2.3.1",
      "com.github.alexarchambault" %%% "scalacheck-shapeless_1.13" % "1.1.0-RC3" % "test",
      "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
    ),
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
  .jsSettings(
    jsEnv := NodeJSEnv().value,
    scalaJSStage in Global := FastOptStage,
    scalaJSUseRhino in Global := false
  )

lazy val circeAltGenericJvm = `circe-alt-generic`.jvm
lazy val circeAltGenericJs = `circe-alt-generic`.js

lazy val render = crossProject
  .dependsOn(core, `circe-alt-generic`)
  .settings(commonSettings: _*)
  .settings(
    name := "plotly-render"
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.webjars.bower" % "plotly.js" % plotlyVersion
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-scalajs" % circeVersion,
      "org.typelevel" %% "cats" % "0.8.0",
      "org.scala-js" %%% "scalajs-dom" % "0.9.0"
    )
  )

lazy val renderJvm = render.jvm
lazy val renderJs = render.js

lazy val demo = project
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(renderJs)
  .settings(commonSettings)
  .settings(noPublishSettings)
  .settings(
    name := "plotly-demo",
    test in Test := (),
    testOnly in Test := (),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.5.5"
    ),
    jsDependencies ++= Seq(
      ("org.webjars.bower" % "plotly.js" % plotlyVersion intransitive()) / "plotly.min.js" commonJSName "Plotly",
      ("org.webjars.bower" % "jquery" % "2.2.4" intransitive()) / "jquery.min.js" commonJSName "JQuery",
      ("org.webjars.bower" % "bootstrap" % "3.3.6" intransitive()) / "bootstrap.min.js" dependsOn "jquery.min.js" commonJSName "Bootstrap",
      ("org.webjars.bower" % "prism" % "1.5.0" intransitive()) / "prism.js" commonJSName "Prism",
      ("org.webjars.bower" % "prism" % "1.5.0" intransitive()) / "prism-clike.js" commonJSName "PrismCLike" dependsOn "prism.js",
      ("org.webjars.bower" % "prism" % "1.5.0" intransitive()) / "prism-java.js" commonJSName "PrismJava" dependsOn "prism-clike.js",
      ("org.webjars.bower" % "prism" % "1.5.0" intransitive()) / "prism-scala.js" commonJSName "PrismScala" dependsOn "prism-java.js"
    ),
    sourceGenerators.in(Compile) += {
      (target, version, scalaSource in Compile).map { (dir, ver, sourceDir) =>

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
            |  val version = "$ver"
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

        process(dir / "plotly", Vector(), sourceDir / "plotly" / "demo")

        files
      }.taskValue
    }
  )

lazy val tests = project
  .dependsOn(coreJvm, renderJvm)
  .settings(commonSettings)
  .settings(noPublishSettings)
  .settings(
    name := "plotly-tests",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-literal" % circeVersion % "test",
      "org.scalatest" %% "scalatest" % "3.0.0-M11" % "test",
      "org.mozilla" % "rhino" % "1.7.7.1" % "test"
    )
  )

lazy val `jupyter-scala` = project
  .dependsOn(coreJvm, renderJvm)
  .settings(commonSettings)
  .settings(
    name := "plotly-jupyter-scala",
    libraryDependencies ++= Seq(
      "com.github.alexarchambault.jupyter" % "scala-api" % jupyterScalaVersion % "provided" cross CrossVersion.full
    )
  )


lazy val `plotly-scala` = project
  .in(file("."))
  .aggregate(coreJvm, coreJs, `joda-time`, circeAltGenericJvm, circeAltGenericJs, renderJvm, renderJs, demo, tests, `jupyter-scala`)
  .settings(commonSettings)
  .settings(noPublishSettings)


lazy val commonSettings = Seq(
  organization := "org.plotly-scala",
  scalaVersion := "2.11.8",
  scalacOptions += "-target:jvm-1.7",
  resolvers ++= Seq(
    "Webjars Bintray" at "https://dl.bintray.com/webjars/maven/",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
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
  },
  scalacOptions += "-target:jvm-1.7"
)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

