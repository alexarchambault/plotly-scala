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

  val categoryCount = 50

  val indices    = 0 until categoryCount
  val categories = indices.map(i => s"name-$i")
  val values     = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(42)
    indices.map(_ => random.nextDouble())
  }

  val data = Seq(
    Histogram(values, categories)
      .copy(
        marker = Some(new Marker().copy(color = Some(Color.StringColor("#004A72")))),
        hovertext = Some(categories.map(c => s"$c with hover text"))
      )
  )

  val xAxis = new Axis().copy(range = Some((0d, 2d)), rangeslider = Some(RangeSlider()))

  val yAxis = new Axis().copy(title = Some("Count"), fixedrange = Some(true), tickformat = Some(".1f"))

  val layout = new Layout().copy(
    xaxis = Some(xAxis),
    yaxis = Some(yAxis),
    title = Some("Styled Basic Histogram"),
    showlegend = Some(false),
    height = Some(400),
    width = Some(600)
  )

  // demo source end
}
