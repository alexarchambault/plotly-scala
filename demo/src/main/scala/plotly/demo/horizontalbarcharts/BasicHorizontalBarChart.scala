package plotly.demo.horizontalbarcharts

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicHorizontalBarChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/horizontal-bar-charts/#basic-horizontal-bar-chart"
  def id           = "basic-horizontal-bar-chart"
  def source       = BasicHorizontalBarChartSource.source

  // demo source start

  // length, in km
  val rivers = Seq(
    "Nile"                 -> 6650,
    "Amazon"               -> 6400,
    "Yangtze"              -> 6300,
    "Mississippi–Missouri" -> 6275,
    "Yenisei"              -> 5539,
    "Yellow River"         -> 5464,
    "Ob–Irtysh"            -> 5410
  ).reverse // horizontal bars are drawn from the bottom up

  val data = Seq(
    Bar(rivers.map(_._2), rivers.map(_._1))
      .updated(orientation = Orientation.Horizontal)
  )

  val layout = Layout().updated(
    title = "Longest Rivers in the World",
    xaxis = Axis().updated(title = "Length (km)"),
    yaxis = Axis().updated(automargin = true)
  )

  // demo source end

}
