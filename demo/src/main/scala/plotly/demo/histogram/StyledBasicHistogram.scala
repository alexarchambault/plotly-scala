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
      .withValues(
        marker = new Marker().withValues(color = Color.StringColor("#004A72")),
        hovertext = categories.map(c => s"$c with hover text")
      )
  )

  val xAxis = new Axis().withValues(range = (0d, 2d), rangeslider = RangeSlider())

  val yAxis = new Axis().withValues(title = "Count", fixedrange = true, tickformat = ".1f")

  val layout = new Layout().withValues(
    xaxis = xAxis,
    yaxis = yAxis,
    title = "Styled Basic Histogram",
    showlegend = false,
    height = 400,
    width = 600
  )

  // demo source end
}
