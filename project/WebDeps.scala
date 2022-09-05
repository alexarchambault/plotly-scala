
import sbt._

object WebDeps {

  object Versions {
    def plotlyJs = "1.54.1"
  }

  def bootstrap = "org.webjars.bower" % "bootstrap" % "5.2.0"
  def jquery = "org.webjars.bower" % "jquery" % "3.6.1"
  def plotlyJs = "org.webjars.bower" % "plotly.js" % Versions.plotlyJs
  def prism = "org.webjars.bower" % "prism" % "1.16.0"

}
