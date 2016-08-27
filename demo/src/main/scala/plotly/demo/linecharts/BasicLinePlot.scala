package plotly.demo.linecharts

import plotly.Scatter
import plotly.demo.NoLayoutDemoChart

object BasicLinePlot extends NoLayoutDemoChart {

  def id = "basic-line-plot"

  def plotlyDocUrl = "https://plot.ly/javascript/line-charts/#basic-line-plot"

  def source = BasicLinePlotSource.source

  // demo source start

  val trace1 = Scatter(
    Seq(1, 2, 3, 4),
    Seq(10, 15, 13, 17)
  )

  val trace2 = Scatter(
    Seq(1, 2, 3, 4),
    Seq(16, 5, 11, 9)
  )

  val data = Seq(trace1, trace2)

  // demo source end

}
