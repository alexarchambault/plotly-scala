
import Settings._

import sbtcrossproject.CrossPlugin.autoImport.crossProject


inThisBuild(List(
  organization := "org.plotly-scala",
  homepage := Some(url("https://github.com/alexarchambault/plotly-scala")),
  licenses := Seq("LGPL 3.0" -> url("http://opensource.org/licenses/LGPL-3.0")),
  developers := List(
    Developer(
      "alexarchambault",
      "Alexandre Archambault",
      "",
      url("https://github.com/alexarchambault")
    )
  )
))


lazy val core = crossProject(JVMPlatform, JSPlatform)
  .jsConfigure(_.disablePlugins(MimaPlugin))
  .settings(
    shared,
    plotlyPrefix,
    libraryDependencies += Deps.dataClass
  )
  .jvmSettings(
    Mima.settings
  )

lazy val coreJvm = core.jvm
lazy val coreJs = core.js

lazy val `joda-time` = project
  .dependsOn(coreJvm)
  .settings(
    shared,
    plotlyPrefix,
    libraryDependencies += Deps.jodaTime,
    Mima.settings
  )

lazy val render = crossProject(JVMPlatform, JSPlatform)
  .jvmConfigure(_.enablePlugins(ShadingPlugin))
  .jsConfigure(_.disablePlugins(MimaPlugin))
  .dependsOn(core)
  .jvmSettings(
    shading("plotly.internals.shaded"),
    Mima.renderFilters
  )
  .settings(
    shared,
    plotlyPrefix
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      Deps.argonautShapeless.value % "shaded",
      // depending on that one so that it doesn't get shaded
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      WebDeps.plotlyJs,
      Deps.scalaTest % "test"
    ),
    shadeNamespaces ++= Set(
      "argonaut",
      "macrocompat",
      "shapeless"
    ),
    resourceGenerators.in(Compile) += Def.task {
      import sys.process._

      val dir = classDirectory.in(Compile).value / "plotly"
      val ver = version.value

      val f = dir / "plotly-scala.properties"
      dir.mkdirs()

      val p = new java.util.Properties

      p.setProperty("plotly-js-version", WebDeps.Versions.plotlyJs)
      p.setProperty("version", ver)
      p.setProperty("commit-hash", Seq("git", "rev-parse", "HEAD").!!.trim)

      val w = new java.io.FileOutputStream(f)
      p.store(w, "plotly-scala properties")
      w.close()

      state.value.log.info(s"Wrote $f")

      Seq(f)
    },
    Mima.settings
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      Deps.argonautShapeless.value,
      Deps.scalajsDom.value
    )
  )

lazy val renderJvm = render.jvm
lazy val renderJs = render.js

lazy val demo = project
  .enablePlugins(JSDependenciesPlugin, ScalaJSPlugin)
  .disablePlugins(MimaPlugin)
  .dependsOn(renderJs)
  .settings(
    shared,
    skip.in(publish) := true,
    plotlyPrefix,
    test.in(Test) := {},
    testOnly.in(Test) := {},
    libraryDependencies += Deps.scalatags.value,
    jsDependencies ++= Seq(
      WebDeps.plotlyJs
        .intransitive()
        ./("plotly.min.js")
        .commonJSName("Plotly"),
      WebDeps.jquery
        .intransitive()
        ./("jquery.min.js")
        .commonJSName("JQuery"),
      WebDeps.bootstrap
        .intransitive()
        ./("bootstrap.min.js")
        .dependsOn("jquery.min.js")
        .commonJSName("Bootstrap"),
      WebDeps.prism
        .intransitive()
        ./("prism.js")
        .commonJSName("Prism"),
      WebDeps.prism
        .intransitive()
        ./("prism-clike.js")
        .commonJSName("PrismCLike")
        .dependsOn("prism.js"),
      WebDeps.prism
        .intransitive()
        ./("prism-java.js")
        .commonJSName("PrismJava")
        .dependsOn("prism-clike.js"),
      WebDeps.prism
        .intransitive()
        ./("prism-scala.js")
        .commonJSName("PrismScala")
        .dependsOn("prism-java.js")
    ),
    generateCustomSources
  )

lazy val tests = project
  .disablePlugins(MimaPlugin)
  .dependsOn(coreJvm, renderJvm)
  .settings(
    shared,
    skip.in(publish) := true,
    plotlyPrefix,
    fetchTestData,
    libraryDependencies ++= Seq(
      Deps.scalaTest % "test",
      Deps.rhino % "test"
    )
  )

lazy val almond = project
  .dependsOn(coreJvm, renderJvm)
  .settings(
    shared,
    plotlyPrefix,
    libraryDependencies += Deps.almondScalaApi % "provided",
    Mima.settings
  )

crossScalaVersions := Nil
skip.in(publish) := true
disablePlugins(MimaPlugin)
