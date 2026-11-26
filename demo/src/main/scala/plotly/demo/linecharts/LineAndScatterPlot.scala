package plotly.demo.linecharts

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object LineAndScatterPlot extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/line-charts/#line-and-scatter-plot"
  def id           = "line-and-scatter-plot"
  def source       = LineAndScatterPlotSource.source

  // demo source start

  val days = (1 to 30).map(_.toDouble)

  // a noisy, growing signal
  val signups = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(7)
    days.map(day => 40 + 1.5 * day + 6 * random.nextGaussian())
  }

  // least squares fit of the signal
  val meanDay    = days.sum / days.length
  val meanSignup = signups.sum / signups.length
  val slope      = days.zip(signups).map { case (d, s) => (d - meanDay) * (s - meanSignup) }.sum /
    days.map(d => (d - meanDay) * (d - meanDay)).sum
  def fit(day: Double) = meanSignup + slope * (day - meanDay)

  val forecastDays = (30 to 40).map(_.toDouble)

  val data = Seq(
    Scatter(days, signups).withValues(
      name = "Daily sign-ups",
      mode = ScatterMode(ScatterMode.Markers),
      marker = Marker().withValues(size = 9, color = Color.StringColor("#636efa"), opacity = 0.75)
    ),
    Scatter(days, days.map(fit)).withValues(
      name = "Trend",
      mode = ScatterMode(ScatterMode.Lines),
      line = Line().withValues(color = Color.StringColor("#00cc96"), width = 3.0)
    ),
    Scatter(forecastDays, forecastDays.map(fit)).withValues(
      name = "Forecast",
      mode = ScatterMode(ScatterMode.Lines, ScatterMode.Markers),
      line = Line().withValues(color = Color.StringColor("#ef553b"), dash = Dash.Dot, width = 2.0)
    )
  )

  val layout = Layout().withValues(
    title = "Daily Sign-ups, Trend and Forecast",
    xaxis = Axis().withValues(title = "Day"),
    yaxis = Axis().withValues(title = "Sign-ups")
  )

  // demo source end

}
