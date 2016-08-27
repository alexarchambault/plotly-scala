package plotly.demo.bar

import plotly._
import plotly.demo.NoLayoutDemoChart
import plotly.element._

object BasicBarChart extends NoLayoutDemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bar-charts/#basic-bar-chart"
  def id = "basic-bar-chart"
  def source = BasicBarChartSource.source

  // demo source start

  val data = Seq(
    Bar(
      Seq("giraffes", "orangutans", "monkeys"),
      Seq(20, 14, 23)
    )
  )

  // demo source end

}
