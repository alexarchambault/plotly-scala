package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BarChartWithDirectLabels extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bar-charts/#bar-chart-with-direct-labels"
  def id = "bar-chart-with-direct-labels"
  def source = BarChartWithDirectLabelsSource.source

  // demo source start

  val xValue = Seq("Product A", "Product B", "Product C")

  val yValue = Seq(20, 14, 23)

  val trace1 = Bar(
    xValue,
    yValue,
    text = Seq("27% market share", "24% market share", "19% market share"),
    marker = Marker(
      color = Color.RGB(158, 202, 225),
      opacity = 0.6,
      line = Line(
        color = Color.RGB(8, 48, 107),
        width = 1.5
      )
    )
  )

  val data = Seq(trace1)

  val annotations = xValue.zip(yValue).map {
    case (x, y) =>
      Annotation(
        x = x,
        y = y,
        text = y.toString,
        xanchor = Anchor.Center,
        yanchor = Anchor.Bottom,
        showarrow = false
      )
  }

  val layout = Layout(
    title = "January 2013 Sales Report",
    annotations = annotations
  )

  // demo source end

}
