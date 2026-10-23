package plotly.demo.linecharts

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicLinePlot extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/line-charts/#basic-line-plot"
  def id           = "basic-line-plot"
  def source       = BasicLinePlotSource.source

  // demo source start

  val months = Seq("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

  // average monthly temperatures, in °C (approximate climate normals)
  val montreal = Seq(-9.7, -8.0, -2.4, 5.7, 13.0, 18.2, 21.0, 19.9, 15.3, 8.4, 2.1, -5.3)
  val sydney   = Seq(23.5, 23.4, 22.1, 19.6, 16.8, 14.2, 13.4, 14.4, 16.8, 18.9, 20.6, 22.2)

  val data = Seq(
    Scatter(months, montreal).withValues(name = "Montréal"),
    Scatter(months, sydney).withValues(name = "Sydney")
  )

  val layout = Layout().withValues(
    title = "Average Monthly Temperature",
    yaxis = Axis().withValues(title = "Temperature (°C)")
  )

  // demo source end

}
