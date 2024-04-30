package plotly.demo.histogram

import plotly.Histogram
import plotly.demo.DemoChart
import plotly.layout.Layout

object BasicHistogram extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/histograms/#basic-histogram"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = BasicHistogramSource.source

  // demo source start

  private val categoryCount = 50

  private val indices    = LazyList.from(0).take(categoryCount)
  private val categories = indices.map(i => s"name-$i")
  private val values     = indices.map(_ => math.random())

  val data = Seq(Histogram(values, categories))

  val layout = new Layout()
    .withTitle(id)
    .withShowlegend(false)
    .withHeight(400)
    .withWidth(600)

  // demo source end

}
