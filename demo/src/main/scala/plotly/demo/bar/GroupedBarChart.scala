package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object GroupedBarChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bar-charts/#grouped-bar-chart"
  def id           = "grouped-bar-chart"
  def source       = GroupedBarChartSource.source

  // demo source start

  val quarters = Seq("Q1", "Q2", "Q3", "Q4")

  // revenue of a fictional company, in thousands of dollars
  val trace1 = Bar(quarters, Seq(420, 465, 440, 580))
    .withValues(name = "2024", marker = Marker().withValues(color = Color.StringColor("#b4b9fc")))

  val trace2 = Bar(quarters, Seq(510, 545, 590, 720))
    .withValues(name = "2025", marker = Marker().withValues(color = Color.StringColor("#636efa")))

  val data = Seq(trace1, trace2)

  val layout = Layout().withValues(
    title = "Quarterly Revenue",
    yaxis = Axis().withValues(tickprefix = "$", ticksuffix = "K"),
    barmode = BarMode.Group,
    bargroupgap = 0.1
  )

  // demo source end

}
