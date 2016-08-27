package plotly.demo.horizontalbarcharts

import plotly.Bar
import plotly.demo.DemoChart
import plotly.element.{Color, Marker, Orientation}
import plotly.layout.{BarMode, Layout}

object ColoredBarChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/horizontal-bar-charts/#colored-bar-chart"
  def id = "colored-bar-chart"
  def source = ColoredBarChartSource.source

  // demo source start

  val trace1 = Bar(
    Seq(20, 14, 23),
    Seq("giraffes", "orangutans", "monkeys"),
    name = "SF Zoo",
    orientation = Orientation.Horizontal,
    marker = Marker(
      color = Color.RGBA(55, 128, 191, 0.6),
      width = 1
    )
  )

  val trace2 = Bar(
    Seq(12, 18, 29),
    Seq("giraffes", "orangutans", "monkeys"),
    name = "LA Zoo",
    orientation = Orientation.Horizontal,
    marker = Marker(
      color = Color.RGBA(255, 153, 51, 0.6),
      width = 1
    )
  )

  val data = Seq(trace1, trace2)

  val layout = Layout(
    title = "Colored Bar Chart",
    barmode = BarMode.Stack
  )

  // demo source end

}
