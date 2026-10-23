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
    .copy(
      text = Some(Seq("27% market share", "24% market share", "19% market share")),
      marker = Some(
        Marker().copy(
          color = Some(Color.RGB(158, 202, 225)),
          opacity = Some(0.6),
          line = Some(
            Line().copy(color = Some(Color.RGB(8, 48, 107)), width = Some(1.5))
          )
        )
      )
    )

  val data = Seq(trace1)

  val annotations = xValue.zip(yValue).map { case (x, y) =>
    Annotation().copy(
      x = Some(x),
      y = Some(y),
      text = Some(y.toString),
      xanchor = Some(Anchor.Center),
      yanchor = Some(Anchor.Bottom),
      showarrow = Some(false)
    )
  }

  val layout = Layout().copy(title = Some("January 2013 Sales Report"), annotations = Some(annotations))

  // demo source end

}
