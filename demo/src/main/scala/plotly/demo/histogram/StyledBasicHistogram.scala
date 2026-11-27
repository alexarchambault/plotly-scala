package plotly.demo.histogram

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object StyledBasicHistogram extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/histograms/#basic-histogram"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = StyledBasicHistogramSource.source

  // demo source start

  // simulated heights of 1,000 women and 1,000 men, in cm
  val (women, men) = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(42)
    (
      Seq.fill(1000)(163.5 + 7 * random.nextGaussian()),
      Seq.fill(1000)(177 + 7.5 * random.nextGaussian())
    )
  }

  def histogram(name: String, values: Seq[Double], color: String) =
    Histogram(values)
      .updated(
        name = name,
        opacity = 0.65,
        xbins = Bins(135, 210, 2.5),
        marker = new Marker().updated(
          color = Color.StringColor(color),
          line = Line().updated(color = Color.StringColor("white"), width = 0.5)
        )
      )

  val data = Seq(
    histogram("Women", women, "#ab63fa"),
    histogram("Men", men, "#19d3f3")
  )

  val xAxis = new Axis().updated(title = "Height (cm)", rangeslider = RangeSlider())

  val yAxis = new Axis().updated(title = "Count", fixedrange = true)

  val layout = new Layout().updated(
    xaxis = xAxis,
    yaxis = yAxis,
    title = "Heights of Women and Men (simulated)",
    barmode = BarMode.Overlay,
    bargap = 0.05
  )

  // demo source end

}
