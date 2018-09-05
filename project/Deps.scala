
import sbt._

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object Deps {

  import Def.setting

  private val circeVersion = "0.9.1"


  def almondScalaApi = "sh.almond" % "scala-kernel-api" % "0.1.5" cross CrossVersion.full
  def circeCore = setting("io.circe" %%% "circe-core" % circeVersion)
  def circeLiteral = setting("io.circe" %% "circe-literal" % circeVersion)
  def circeScalaJs = setting("io.circe" %%% "circe-scalajs" % circeVersion)
  def circeParser = setting("io.circe" %%% "circe-parser" % circeVersion)
  def jodaTime = "joda-time" % "joda-time" % "2.9.1"
  def rhino = "org.mozilla" % "rhino" % "1.7.7.1"
  def shapeless = setting("com.chuusai" %%% "shapeless" % "2.3.2")
  def scalacheckShapeless = setting("com.github.alexarchambault" %%% "scalacheck-shapeless_1.13" % "1.1.7")
  def scalajsDom = setting("org.scala-js" %%% "scalajs-dom" % "0.9.3")
  def scalatags = setting("com.lihaoyi" %%% "scalatags" % "0.6.7")
  def scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"
  def utest = setting("com.lihaoyi" %%% "utest" % "0.5.4")

}
