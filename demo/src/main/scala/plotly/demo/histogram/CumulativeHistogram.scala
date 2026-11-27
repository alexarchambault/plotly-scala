package plotly.demo.histogram

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object CumulativeHistogram extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/histograms/#cumulative-histogram"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = CumulativeHistogramSource.source

  // demo source start

  // simulated durations of 1,000 phone calls, in minutes
  val durations = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(4)
    Seq.fill(1000)(-4 * math.log(1 - random.nextDouble()))
  }

  // for each duration, the share of the calls lasting longer: the bins are summed from the right
  val data = Seq(
    Histogram(durations).withValues(
      xbins = Bins(0.0, 20.0, 2.0),
      histnorm = HistNorm.Percent,
      cumulative = Cumulative(true).withValues(direction = CumulativeDirection.Decreasing),
      texttemplate = "%{y:.0f}%",
      textposition = BarTextPosition.Outside,
      marker = Marker().withValues(color = Color.RGB(25, 211, 243))
    )
  )

  val layout = Layout().withValues(
    title = "Calls Lasting Longer Than…",
    xaxis = Axis().withValues(title = "minutes", dtick = 2.0),
    yaxis = Axis().withValues(ticksuffix = "%", range = (0.0, 110.0)),
    bargap = 0.05
  )

  // demo source end

}
