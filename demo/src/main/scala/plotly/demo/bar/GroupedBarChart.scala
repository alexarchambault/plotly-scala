package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object GroupedBarChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bar-charts/#grouped-bar-chart"
  def id = "grouped-bar-chart"
  def source = GroupedBarChartSource.source

  // demo source start

  val trace1 = Bar(Seq("giraffes", "orangutans", "monkeys"), Seq(20, 14, 23))
    .withName("SF Zoo")

  val trace2 = Bar(Seq("giraffes", "orangutans", "monkeys"), Seq(12, 18, 29))
    .withName("LA Zoo")

  val data = Seq(trace1, trace2)

  val layout = Layout()
    .withBarmode(BarMode.Group)

  // demo source end

}
