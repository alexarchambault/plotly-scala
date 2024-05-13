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

  private val categoryCount = 50

  private val indices    = LazyList.from(0).take(categoryCount)
  private val categories = indices.map(i => s"name-$i for $id")
  private val values     = indices.map(_ => math.random())

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
    .withTitle(id)
    .withShowlegend(false)
    .withHeight(400)
    .withWidth(600)

  // demo source end
}
