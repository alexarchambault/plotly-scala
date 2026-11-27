package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object PatternedBars extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/bar-charts/#bar-chart-with-pattern-fills"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = PatternedBarsSource.source

  // demo source start

  val quarters = Seq("Q1", "Q2", "Q3", "Q4")

  // revenue by quarter, in millions of dollars: the forecasts are hatched, unlike the actual figures
  val data = Seq(
    ("Product A", Seq(12.0, 15.0, 14.0, 18.0), Color.RGB(99, 110, 250)),
    ("Product B", Seq(8.0, 9.0, 11.0, 13.0), Color.RGB(0, 204, 150))
  ).map { case (name, revenue, color) =>
    Bar(quarters, revenue).updated(
      name = name,
      marker = Marker().updated(
        color = color,
        pattern = FillPattern().updated(
          shape =
            Seq[PatternShape](PatternShape.None, PatternShape.None, PatternShape.DiagonalUp, PatternShape.DiagonalUp),
          fillmode = PatternFillMode.Overlay,
          fgcolor = Color.StringColor("white"),
          solidity = 0.3
        )
      )
    )
  }

  val layout = Layout().updated(
    title = "Revenue (Q3 and Q4 Forecasted)",
    barmode = BarMode.Group,
    yaxis = Axis().updated(tickprefix = "$", ticksuffix = "M")
  )

  // demo source end

}
