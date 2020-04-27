package plotly.demo.linecharts

import plotly.Scatter
import plotly.demo.DemoChart
import plotly.element.ScatterMode
import plotly.layout.Layout

object LineAndScatterPlot extends DemoChart {

  def id = "line-and-scatter-plot"

  def plotlyDocUrl = "https://plot.ly/javascript/line-charts/#line-and-scatter-plot"

  def source = LineAndScatterPlotSource.source

  // demo source start

  val trace1 = Scatter(Seq(1, 2, 3, 4), Seq(10, 15, 13, 17))
    .withMode(ScatterMode(ScatterMode.Markers))

  val trace2 = Scatter(Seq(2, 3, 4, 5), Seq(16, 5, 11, 9))
    .withMode(ScatterMode(ScatterMode.Lines))

  val trace3 = Scatter(Seq(1, 2, 3, 4), Seq(12, 9, 15, 12))
    .withMode(ScatterMode(ScatterMode.Lines, ScatterMode.Markers))

  val data = Seq(trace1, trace2, trace3)

  val layout = Layout()
    .withTitle("Line and Scatter Plot")

  // demo source end

}
