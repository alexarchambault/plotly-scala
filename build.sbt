
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
  .settings(
    shared,
    plotlyPrefix
  )

lazy val coreJvm = core.jvm
lazy val coreJs = core.js

lazy val `joda-time` = project
  .dependsOn(coreJvm)
  .settings(
    shared,
    plotlyPrefix,
    libraryDependencies += Deps.jodaTime
  )

lazy val render = crossProject(JVMPlatform, JSPlatform)
  .dependsOn(core)
  .settings(
    shared,
    plotlyPrefix,
    libraryDependencies += Deps.argonautShapeless.value
  )
  .jvmSettings(
    libraryDependencies += WebDeps.plotlyJs
  )
  .jsSettings(
    libraryDependencies += Deps.scalajsDom.value
  )

lazy val renderJvm = render.jvm
lazy val renderJs = render.js

lazy val demo = project
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(renderJs)
  .settings(
    shared,
    dontPublish,
    plotlyPrefix,
    test in Test := (),
    testOnly in Test := (),
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
  .dependsOn(coreJvm, renderJvm)
  .settings(
    shared,
    dontPublish,
    plotlyPrefix,
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
    libraryDependencies += Deps.almondScalaApi % "provided"
  )


lazy val `plotly-scala` = project
  .in(file("."))
  .aggregate(
    coreJvm,
    coreJs,
    `joda-time`,
    renderJvm,
    renderJs,
    demo,
    tests,
    almond
  )
  .settings(
    shared,
    dontPublish
  )
