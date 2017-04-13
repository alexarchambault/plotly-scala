
import sbt._

object WebDeps {

  private val plotlyVersion = "1.24.1"

  def bootstrap = "org.webjars.bower" % "bootstrap" % "3.3.6"
  def jquery = "org.webjars.bower" % "jquery" % "2.2.4"
  def plotlyJs = "org.webjars.bower" % "plotly.js" % plotlyVersion
  def prism = "org.webjars.bower" % "prism" % "1.5.0"

}
