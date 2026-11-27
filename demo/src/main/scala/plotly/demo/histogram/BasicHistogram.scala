package plotly.demo.histogram

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicHistogram extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/histograms/#basic-histogram"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = BasicHistogramSource.source

  // demo source start

  // simulated heights of 2,000 adults, in cm
  val heights = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(42)
    Seq.fill(2000)(170 + 9 * random.nextGaussian())
  }

  val data = Seq(Histogram(heights))

  val layout = new Layout().updated(
    title = "Heights of 2,000 Adults (simulated)",
    xaxis = new Axis().updated(title = "Height (cm)"),
    yaxis = new Axis().updated(title = "Count")
  )

  // demo source end

}
