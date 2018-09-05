
import Aliases._
import Settings._


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


lazy val core = crossProject
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
    libs += Deps.jodaTime
  )

lazy val `circe-simple-generic` = crossProject
  .settings(
    shared,
    libs ++= Seq(
      Deps.circeCore.value,
      Deps.circeParser.value,
      Deps.shapeless.value,
      Deps.scalacheckShapeless.value % "test"
    ),
    utest
  )
  .jsSettings(
    scalaJSStage in Global := FastOptStage
  )

lazy val circeSimpleGenericJvm = `circe-simple-generic`.jvm
lazy val circeSimpleGenericJs = `circe-simple-generic`.js

lazy val render = crossProject
  .dependsOn(core, `circe-simple-generic`)
  .settings(
    shared,
    plotlyPrefix
  )
  .jvmSettings(
    libs += WebDeps.plotlyJs
  )
  .jsSettings(
    libs ++= Seq(
      Deps.circeScalaJs.value,
      Deps.scalajsDom.value
    )
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
    libs += Deps.scalatags.value,
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
    libs ++= Seq(
      Deps.circeLiteral.value % "test",
      Deps.scalaTest % "test",
      Deps.rhino % "test"
    )
  )

lazy val almond = project
  .dependsOn(coreJvm, renderJvm)
  .settings(
    shared,
    plotlyPrefix,
    libs ++= Seq(
      Deps.almondScalaApi % "provided"
    )
  )


lazy val `plotly-scala` = project
  .in(file("."))
  .aggregate(
    coreJvm,
    coreJs,
    `joda-time`,
    circeSimpleGenericJvm,
    circeSimpleGenericJs,
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
