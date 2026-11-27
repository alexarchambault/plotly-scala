package plotly.demo.area

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object GradientAndPatternFills extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/filled-area-plots/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = GradientAndPatternFillsSource.source

  // demo source start

  val hours = (0 to 24).map(_.toDouble)

  // power produced by solar panels over a day, in kW, and the power consumed by a house
  val produced = hours.map(h => math.max(0.0, 5 * math.sin((h - 6) * math.Pi / 13)))
  val consumed =
    hours.map(h => 0.8 + 1.2 * math.exp(-math.pow(h - 8, 2) / 4) + 1.8 * math.exp(-math.pow(h - 19.5, 2) / 6))

  val data = Seq(
    Scatter(hours, produced).updated(
      name = "produced",
      fill = Fill.ToZeroY,
      // the higher the power, the more saturated the fill
      fillgradient = FillGradient().updated(
        `type` = GradientType.Vertical,
        colorscale = ColorScale.CustomScale(
          Seq(0.0 -> Color.RGBA(254, 203, 82, 0.1), 1.0 -> Color.RGBA(254, 160, 40, 0.9))
        )
      ),
      line = Line().updated(color = Color.RGB(254, 160, 40), shape = LineShape.Spline)
    ),
    Scatter(hours, consumed).updated(
      name = "consumed",
      fill = Fill.ToZeroY,
      fillcolor = Color.RGBA(99, 110, 250, 0.15),
      fillpattern = FillPattern().updated(
        shape = PatternShape.DiagonalUp,
        fgcolor = Color.RGB(99, 110, 250),
        size = 8.0,
        solidity = 0.15
      ),
      line = Line().updated(color = Color.RGB(99, 110, 250), shape = LineShape.Spline)
    )
  )

  val layout = Layout().updated(
    title = "Solar Power Over a Day",
    xaxis = Axis().updated(title = "hour", dtick = 3.0),
    yaxis = Axis().updated(title = "kW")
  )

  // demo source end

}
