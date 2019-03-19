
import sbt._

object WebDeps {

  object Versions {
    def plotlyJs = "1.45.2"
  }

  def bootstrap = "org.webjars.bower" % "bootstrap" % "3.3.7"
  def jquery = "org.webjars.bower" % "jquery" % "2.2.4"
  def plotlyJs = "org.webjars.bower" % "plotly.js" % Versions.plotlyJs
  def prism = "org.webjars.bower" % "prism" % "1.5.1"

}
