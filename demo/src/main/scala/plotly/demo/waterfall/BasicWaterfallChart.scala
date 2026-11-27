package plotly.demo.waterfall

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicWaterfallChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/waterfall-charts/#basic-waterfall-chart"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = BasicWaterfallChartSource.source

  // demo source start

  // a profit and loss statement, in thousands of dollars:
  // changes of the running total, and the running totals themselves
  val steps = Seq(
    ("Sales", WaterfallMeasure.Relative, 60.0),
    ("Consulting", WaterfallMeasure.Relative, 80.0),
    ("Net revenue", WaterfallMeasure.Total, 0.0),
    ("Purchases", WaterfallMeasure.Relative, -40.0),
    ("Other expenses", WaterfallMeasure.Relative, -20.0),
    ("Profit before tax", WaterfallMeasure.Total, 0.0)
  )

  val data = Seq(
    Waterfall(steps.map(_._1), steps.map(_._3), steps.map(_._2)).updated(
      textposition = BarTextPosition.Outside,
      text = steps.map { case (_, measure, value) =>
        if (measure == WaterfallMeasure.Total) "" else f"$value%+.0f"
      },
      connector = Connector().updated(
        line = Line().updated(color = Color.RGB(63, 63, 63), dash = Dash.Dot)
      ),
      increasing = BarStyle().updated(marker = Marker().updated(color = Color.RGB(0, 204, 150))),
      decreasing = BarStyle().updated(marker = Marker().updated(color = Color.RGB(239, 85, 59))),
      totals = BarStyle().updated(marker = Marker().updated(color = Color.RGB(99, 110, 250)))
    )
  )

  val layout = Layout().updated(
    title = "Profit and Loss Statement",
    yaxis = Axis().updated(tickprefix = "$", ticksuffix = "K"),
    showlegend = false
  )

  // demo source end

}
