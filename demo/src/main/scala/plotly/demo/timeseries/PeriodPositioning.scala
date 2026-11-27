package plotly.demo.timeseries

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object PeriodPositioning extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/reference/bar/#bar-xperiod"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = PeriodPositioningSource.source

  // demo source start

  // monthly sales, and their running average: the values of each month are drawn in the middle
  // of their month, rather than at its first day
  val months  = (1 to 12).map(m => LocalDateTime(2025, m, 1, 0, 0, 0))
  val sales   = Seq(42.0, 38.0, 51.0, 55.0, 61.0, 70.0, 74.0, 69.0, 58.0, 54.0, 63.0, 80.0)
  val average = sales.scanLeft(0.0)(_ + _).tail.zipWithIndex.map { case (sum, i) => sum / (i + 1) }

  val data = Seq(
    Bar(months, sales).withValues(
      name = "sales",
      xperiod = "M1",
      xperiodalignment = PeriodAlignment.Middle
    ),
    Scatter(months, average).withValues(
      name = "running average",
      mode = ScatterMode(ScatterMode.Lines, ScatterMode.Markers),
      xperiod = "M1",
      xperiodalignment = PeriodAlignment.Middle
    )
  )

  val layout = Layout().withValues(
    title = "Monthly Sales",
    yaxis = Axis().withValues(ticksuffix = "K"),
    xaxis = Axis().withValues(tickformat = "%b")
  )

  // demo source end

}
