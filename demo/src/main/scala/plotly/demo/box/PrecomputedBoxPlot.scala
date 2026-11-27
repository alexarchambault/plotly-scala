package plotly.demo.box

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object PrecomputedBoxPlot extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/box-plots/#box-plot-with-precomputed-quartiles"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = PrecomputedBoxPlotSource.source

  // demo source start

  // monthly temperatures, in °C, summarized by their statistics rather than given as raw values
  val months = Seq("Jan", "Apr", "Jul", "Oct")

  val data = Seq(
    Box().updated(
      x = months,
      lowerfence = Seq(-18.0, -2.0, 12.0, -1.0),
      q1 = Seq(-12.0, 3.0, 17.0, 5.0),
      median = Seq(-9.0, 6.0, 21.0, 8.0),
      q3 = Seq(-6.0, 10.0, 24.0, 12.0),
      upperfence = Seq(2.0, 19.0, 31.0, 20.0),
      mean = Seq(-8.6, 6.4, 20.6, 8.3),
      name = "Montréal",
      marker = Marker().updated(color = Color.RGB(99, 110, 250))
    )
  )

  val layout = Layout().updated(
    title = "Daily Temperatures in Montréal",
    yaxis = Axis().updated(ticksuffix = "°C")
  )

  // demo source end

}
