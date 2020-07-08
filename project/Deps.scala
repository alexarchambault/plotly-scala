
import sbt._

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object Deps {

  import Def.setting

  def almondScalaApi = "sh.almond" %% "jupyter-api" % "0.10.2"
  def argonautShapeless = setting("com.github.alexarchambault" %%% "argonaut-shapeless_6.2" % "1.2.0")
  def dataClass = "io.github.alexarchambault" %% "data-class" % "0.2.3"
  def jodaTime = "joda-time" % "joda-time" % "2.10.6"
  def rhino = "org.mozilla" % "rhino" % "1.7.12"
  def shapeless = setting("com.chuusai" %%% "shapeless" % "2.3.3")
  def scalacheckShapeless = setting("com.github.alexarchambault" %%% "scalacheck-shapeless_1.14" % "1.2.0-1")
  def scalajsDom = setting("org.scala-js" %%% "scalajs-dom" % "1.0.0")
  def scalatags = setting("com.lihaoyi" %%% "scalatags" % "0.9.1")
  def scalaTest = "org.scalatest" %% "scalatest" % "3.2.0"
  def utest = setting("com.lihaoyi" %%% "utest" % "0.6.6")

}
