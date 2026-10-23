package plotly.demo.histogram

import plotly.Histogram
import plotly.demo.DemoChart
import plotly.layout.{Axis, Layout, RangeSlider}
import plotly.element._

object StyledBasicHistogram extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/histograms/#basic-histogram"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = StyledBasicHistogramSource.source

  // demo source start

  // fixed seed, so that the plot is the same every time
  val random = new scala.util.Random(42)

  val categoryCount = 50

  val indices    = 0 until categoryCount
  val categories = indices.map(i => s"name-$i")
  val values     = indices.map(_ => random.nextDouble())

  val data = Seq(
    Histogram(values, categories)
      .withMarker(new Marker().withColor(Color.StringColor("#004A72")))
      .withHovertext(categories.map(c => s"$c with hover text"))
  )

  val xAxis = new Axis()
    .withRange((0d, 2d))
    .withRangeslider(RangeSlider())

  val yAxis = new Axis()
    .withTitle("Count")
    .withFixedrange(true)
    .withTickformat(".1f")

  val layout = new Layout()
    .withXaxis(xAxis)
    .withYaxis(yAxis)
    .withTitle("Styled Basic Histogram")
    .withShowlegend(false)
    .withHeight(400)
    .withWidth(600)

  // demo source end
}
