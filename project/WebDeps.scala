
import sbt._

object WebDeps {

  private val plotlyVersion = "1.35.2"

  def bootstrap = "org.webjars.bower" % "bootstrap" % "4.1.0"
  def jquery = "org.webjars.bower" % "jquery" % "3.3.1"
  def plotlyJs = "org.webjars.bower" % "plotly.js" % plotlyVersion
  def prism = "org.webjars.bower" % "prism" % "1.12.2"

}
