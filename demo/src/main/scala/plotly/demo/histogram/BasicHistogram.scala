package plotly.demo.histogram

import plotly.Histogram
import plotly.demo.DemoChart
import plotly.layout.Layout

object BasicHistogram extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/histograms/#basic-histogram"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = BasicHistogramSource.source

  // demo source start

  val categoryCount = 50

  val indices    = 0 until categoryCount
  val categories = indices.map(i => s"name-$i")
  val values     = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(42)
    indices.map(_ => random.nextDouble())
  }

  val data = Seq(Histogram(values, categories))

  val layout = new Layout()
    .withTitle("Basic Histogram")
    .withShowlegend(false)
    .withHeight(400)
    .withWidth(600)

  // demo source end

}
