
import sbt._

import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Deps {

  import Def.setting

  private val jupyterScalaVersion = "0.4.0"
  private val circeVersion = "0.9.1"


  def circeCore = setting("io.circe" %%% "circe-core" % circeVersion)
  def circeLiteral = setting("io.circe" %% "circe-literal" % circeVersion)
  def circeScalaJs = setting("io.circe" %%% "circe-scalajs" % circeVersion)
  def circeParser = setting("io.circe" %%% "circe-parser" % circeVersion)
  def jodaTime = "joda-time" % "joda-time" % "2.9.9"
  def jupyterScalaApi = "org.jupyter-scala" % "scala-api" % jupyterScalaVersion cross CrossVersion.full
  def rhino = "org.mozilla" % "rhino" % "1.7.7.2"
  def shapeless = setting("com.chuusai" %%% "shapeless" % "2.3.2")
  def scalacheckShapeless = setting("com.github.alexarchambault" %%% "scalacheck-shapeless_1.13" % "1.1.7")
  def scalajsDom = setting("org.scala-js" %%% "scalajs-dom" % "0.9.4")
  def scalatags = setting("com.lihaoyi" %%% "scalatags" % "0.6.7")
  def scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"
  def utest = setting("com.lihaoyi" %%% "utest" % "0.6.0")

}
