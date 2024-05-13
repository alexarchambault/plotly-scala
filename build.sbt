import java.nio.charset.StandardCharsets
import java.nio.file.Files

import Settings._

import sbtcrossproject.CrossPlugin.autoImport.crossProject

inThisBuild(
  List(
    organization := "org.plotly-scala",
    homepage     := Some(url("https://github.com/alexarchambault/plotly-scala")),
    licenses     := Seq("LGPL 3.0" -> url("http://opensource.org/licenses/LGPL-3.0")),
    developers := List(
      Developer(
        "alexarchambault",
        "Alexandre Archambault",
        "",
        url("https://github.com/alexarchambault")
      )
    )
  )
)

val previousVersions = Set.empty[String]
lazy val mimaSettings = Def.settings(
  mimaPreviousArtifacts := previousVersions.map(organization.value %% moduleName.value % _)
)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .jsConfigure(_.disablePlugins(MimaPlugin))
  .settings(
    shared,
    plotlyPrefix,
    libraryDependencies += Deps.dataClass % Provided
  )
  .jvmSettings(
    mimaSettings
  )

lazy val coreJvm = core.jvm
lazy val coreJs  = core.js

lazy val `joda-time` = project
  .dependsOn(coreJvm)
  .settings(
    shared,
    mimaSettings,
    plotlyPrefix,
    libraryDependencies += Deps.jodaTime
  )

lazy val render = crossProject(JVMPlatform, JSPlatform)
  .jvmConfigure(_.enablePlugins(ShadingPlugin))
  .jsConfigure(_.disablePlugins(MimaPlugin))
  .dependsOn(core)
  .settings(
    shared,
    plotlyPrefix
  )
  .jvmSettings(
    mimaSettings,
    mimaCurrentClassfiles := shadedPackageBin.value,
    Mima.renderFilters,
    shadedModules += Deps.argonautShapeless.value.module,
    shadingRules ++= {
      val shadeUnder      = "plotly.internals.shaded"
      val shadeNamespaces = Seq("argonaut", "macrocompat", "shapeless")
      for (ns <- shadeNamespaces)
        yield ShadingRule.moveUnder(ns, shadeUnder),
    },
    validNamespaces += "plotly",
    libraryDependencies ++= Seq(
      Deps.argonautShapeless.value,
      // depending on that one so that it doesn't get shaded
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      WebDeps.plotlyJs,
      Deps.scalaTest % "test"
    ),
    (Compile / resourceGenerators) += Def.task {
      import sys.process._

      val log = state.value.log

      val dir = (Compile / classDirectory).value / "plotly"
      val ver = version.value

      val f = dir / "plotly-scala.properties"
      dir.mkdirs()

      val props = Seq(
        "plotly-js-version" -> WebDeps.Versions.plotlyJs,
        "version"           -> ver,
        "commit-hash"       -> Seq("git", "rev-parse", "HEAD").!!.trim
      )

      val b = props
        .map { case (k, v) =>
          assert(!v.contains("\n"), s"Invalid ${"\\n"} character in property $k")
          s"$k=$v"
        }
        .mkString("\n")
        .getBytes(StandardCharsets.UTF_8)

      val currentContentOpt = Some(f.toPath)
        .filter(Files.exists(_))
        .map(p => Files.readAllBytes(p))

      if (currentContentOpt.forall(b0 => !java.util.Arrays.equals(b, b0))) {
        val w = new java.io.FileOutputStream(f)
        w.write(b)
        w.close()

        log.info(s"Wrote $f")
      }

      Nil
    }
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      Deps.argonautShapeless.value,
      Deps.scalajsDom.value
    )
  )

lazy val renderJvm = render.jvm
lazy val renderJs  = render.js

lazy val demo = project
  .enablePlugins(JSDependenciesPlugin, ScalaJSPlugin)
  .disablePlugins(MimaPlugin)
  .dependsOn(renderJs)
  .settings(
    shared,
    (publish / skip) := true,
    plotlyPrefix,
    (Test / test)     := {},
    (Test / testOnly) := {},
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
    (publish / skip) := true,
    plotlyPrefix,
    fetchTestData,
    libraryDependencies ++= Seq(
      Deps.scalaTest % "test",
      Deps.rhino     % "test"
    )
  )

lazy val almond = project
  .dependsOn(coreJvm, renderJvm)
  .settings(
    shared,
    mimaSettings,
    plotlyPrefix,
    libraryDependencies += Deps.almondScalaApi % "provided"
  )

crossScalaVersions := Nil
(publish / skip)   := true
disablePlugins(MimaPlugin)
