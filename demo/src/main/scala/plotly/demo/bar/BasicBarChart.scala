package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicBarChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bar-charts/#basic-bar-chart"
  def id           = "basic-bar-chart"
  def source       = BasicBarChartSource.source

  // demo source start

  val planets = Seq("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")

  // mean radius, in km
  val radius = Seq(2440, 6052, 6371, 3390, 69911, 58232, 25362, 24622)

  val data = Seq(Bar(planets, radius))

  val layout = Layout().updated(
    title = "Size of the Planets",
    yaxis = Axis().updated(title = "Mean radius (km)")
  )

  // demo source end

}
