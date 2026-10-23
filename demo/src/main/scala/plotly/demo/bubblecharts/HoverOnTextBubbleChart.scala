package plotly.demo.bubblecharts

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object HoverOnTextBubbleChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bubble-charts/#hover-text-on-bubble-charts"
  def id           = "hover-on-text-bubble-chart"
  def source       = HoverOnTextBubbleChartSource.source

  // demo source start

  val planets = Seq("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")

  val distance = Seq(0.39, 0.72, 1.0, 1.52, 5.2, 9.58, 19.2, 30.05)      // from the Sun, in AU
  val period   = Seq(0.24, 0.62, 1.0, 1.88, 11.86, 29.46, 84.01, 164.8)  // in years
  val radius   = Seq(2440, 6052, 6371, 3390, 69911, 58232, 25362, 24622) // in km

  val colors = Seq("#9e9e9e", "#e3bb76", "#4f8fdc", "#c1440e", "#d39c6a", "#e3cf96", "#7de3f4", "#4b70dd")

  val trace1 = Scatter(distance, period)
    .withValues(
      text = planets.zip(radius).map { case (planet, r) => s"<b>$planet</b><br>radius: $r km" },
      mode = ScatterMode(ScatterMode.Markers),
      marker = Marker().withValues(
        color = colors.map(Color.StringColor(_)),
        size = radius.map(r => 10 + r / 1400),
        line = Line().withValues(color = Color.StringColor("white"), width = 1.0)
      )
    )

  val data = Seq(trace1)

  // on log-log axes, Kepler's third law shows up as a straight line
  val layout = Layout().withValues(
    title = "Orbits of the Planets (hover on the bubbles)",
    xaxis = Axis().withValues(title = "Distance from the Sun (AU)", `type` = AxisType.Log, dtick = 1.0),
    yaxis = Axis().withValues(title = "Orbital period (years)", `type` = AxisType.Log, dtick = 1.0),
    showlegend = false
  )

  // demo source end

}
