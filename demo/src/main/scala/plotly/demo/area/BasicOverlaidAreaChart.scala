package plotly.demo.area

import plotly.Scatter
import plotly.demo.NoLayoutDemoChart
import plotly.element.Fill

object BasicOverlaidAreaChart extends NoLayoutDemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/filled-area-plots/#basic-overlaid-area-chart"
  def id           = "basic-overlaid-area-chart"
  def source       = BasicOverlaidAreaChartSource.source

  // demo source start

  val trace1 = Scatter(Seq(1, 2, 3, 4), Seq(0, 2, 3, 5))
    .withFill(Fill.ToZeroY)

  val trace2 = Scatter(Seq(1, 2, 3, 4), Seq(3, 5, 1, 7))
    .withFill(Fill.ToNextY)

  val data = Seq(trace1, trace2)

  // demo source end

}
