
import sbt._

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object Deps {

  import Def.setting

  def almondScalaApi = "sh.almond" %% "jupyter-api" % "0.13.8"
  def argonautShapeless = setting("com.github.alexarchambault" %%% "argonaut-shapeless_6.3" % "1.3.1")
  def dataClass = "io.github.alexarchambault" %% "data-class" % "0.2.6"
  def jodaTime = "joda-time" % "joda-time" % "2.12.4"
  def rhino = "org.mozilla" % "rhino" % "1.7.14"
  def shapeless = setting("com.chuusai" %%% "shapeless" % "2.3.7")
  def scalacheckShapeless = setting("com.github.alexarchambault" %%% "scalacheck-shapeless_1.15" % "1.3.0")
  def scalajsDom = setting("org.scala-js" %%% "scalajs-dom" % "2.4.0")
  def scalatags = setting("com.lihaoyi" %%% "scalatags" % "0.12.0")
  def scalaTest = "org.scalatest" %% "scalatest" % "3.2.15"

}
