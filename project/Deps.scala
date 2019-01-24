
import sbt._

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object Deps {

  import Def.setting

  def almondScalaApi = "sh.almond" % "scala-kernel-api" % "0.2.2" cross CrossVersion.full
  def argonautShapeless = setting("com.github.alexarchambault" %%% "argonaut-shapeless_6.2" % "1.2.0-M9")
  def jodaTime = "joda-time" % "joda-time" % "2.10.1"
  def rhino = "org.mozilla" % "rhino" % "1.7.10"
  def shapeless = setting("com.chuusai" %%% "shapeless" % "2.3.3")
  def scalacheckShapeless = setting("com.github.alexarchambault" %%% "scalacheck-shapeless_1.14" % "1.2.0-1")
  def scalajsDom = setting("org.scala-js" %%% "scalajs-dom" % "0.9.6")
  def scalatags = setting("com.lihaoyi" %%% "scalatags" % "0.6.7")
  def scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  def utest = setting("com.lihaoyi" %%% "utest" % "0.6.6")

}
