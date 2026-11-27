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

  val products = Seq("Product A", "Product B", "Product C", "Product D")

  // units sold (fictional)
  val sales = Seq(320, 245, 190, 125)

  val marketShares = sales.map(s => f"${100.0 * s / sales.sum}%.0f%% share")

  val trace1 = Bar(products, sales)
    .updated(
      text = marketShares,
      textposition = BarTextPosition.Inside,
      marker = Marker().updated(
        color = Color.RGB(99, 110, 250),
        opacity = 0.85,
        line = Line().updated(color = Color.RGB(46, 54, 160), width = 1.5)
      )
    )

  val data = Seq(trace1)

  // unit counts, right above each bar
  val annotations = products.zip(sales).map { case (x, y) =>
    Annotation().updated(
      x = x,
      y = y,
      text = y.toString,
      xanchor = Anchor.Center,
      yanchor = Anchor.Bottom,
      showarrow = false
    )
  }

  val layout = Layout().updated(
    title = "January 2025 Sales Report",
    yaxis = Axis().updated(title = "Units sold"),
    annotations = annotations
  )

  // demo source end

}
