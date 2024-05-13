package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BarChartWithDirectLabels extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bar-charts/#bar-chart-with-direct-labels"
  def id           = "bar-chart-with-direct-labels"
  def source       = BarChartWithDirectLabelsSource.source

  // demo source start

  val xValue = Seq("Product A", "Product B", "Product C")

  val yValue = Seq(20, 14, 23)

  val trace1 = Bar(xValue, yValue)
    .withText(Seq("27% market share", "24% market share", "19% market share"))
    .withMarker(
      Marker()
        .withColor(Color.RGB(158, 202, 225))
        .withOpacity(0.6)
        .withLine(
          Line()
            .withColor(Color.RGB(8, 48, 107))
            .withWidth(1.5)
        )
    )

  val data = Seq(trace1)

  val annotations = xValue.zip(yValue).map { case (x, y) =>
    Annotation()
      .withX(x)
      .withY(y)
      .withText(y.toString)
      .withXanchor(Anchor.Center)
      .withYanchor(Anchor.Bottom)
      .withShowarrow(false)
  }

  val layout = Layout()
    .withTitle("January 2013 Sales Report")
    .withAnnotations(annotations)

  // demo source end

}
