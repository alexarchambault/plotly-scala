package plotly.demo.horizontalbarcharts

import plotly.Bar
import plotly.demo.NoLayoutDemoChart
import plotly.element.Orientation

object BasicHorizontalBarChart extends NoLayoutDemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/horizontal-bar-charts/#basic-horizontal-bar-chart"
  def id = "basic-horizontal-bar-chart"
  def source = BasicHorizontalBarChartSource.source

  // demo source start

  val data = Seq(
    Bar(Seq(20, 14, 23), Seq("giraffes", "orangutans", "monkeys"))
      .withOrientation(Orientation.Horizontal)
  )

  // demo source end

}
