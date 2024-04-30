import sbt._

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object Deps {

  import Def.setting

  def almondScalaApi    = "sh.almond"                 %% "jupyter-api" % "0.13.14"
  def argonautShapeless = setting("com.github.alexarchambault" %%% "argonaut-shapeless_6.3" % "1.3.1")
  def dataClass         = "io.github.alexarchambault" %% "data-class"  % "0.2.6"
  def jodaTime          = "joda-time"                  % "joda-time"   % "2.12.7"
  def rhino             = "org.mozilla"                % "rhino"       % "1.7.15"
  def scalajsDom        = setting("org.scala-js" %%% "scalajs-dom" % "2.8.0")
  def scalatags         = setting("com.lihaoyi" %%% "scalatags" % "0.13.1")
  def scalaTest         = "org.scalatest"             %% "scalatest"   % "3.2.18"

}
