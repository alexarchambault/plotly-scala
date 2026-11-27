package plotly.demo.violin

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicViolinPlot extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/violin/#multiple-traces"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = BasicViolinPlotSource.source

  // demo source start

  // simulated response times of three services, in ms
  val random   = new scala.util.Random(3) // fixed seed, so that the plot is the same every time
  val services = Seq(
    "auth"   -> Seq.fill(300)(math.exp(3.0 + 0.3 * random.nextGaussian())),
    "search" -> Seq.fill(300)(math.exp(3.6 + 0.5 * random.nextGaussian())),
    // a bimodal one: cache hits and misses
    "catalog" -> Seq.fill(300)(
      if (random.nextDouble() < 0.6) 15 + 4 * random.nextGaussian()
      else 70 + 12 * random.nextGaussian()
    )
  )

  val data = services.map { case (name, times) =>
    Violin(times).withValues(
      name = name,
      box = ViolinBox().withValues(visible = true, width = 0.15),
      meanline = MeanLine().withValues(visible = true),
      points = BoxPoints.Outliers,
      spanmode = SpanMode.Hard
    )
  }

  val layout = Layout().withValues(
    title = "Response Times",
    yaxis = Axis().withValues(title = "ms", zeroline = false),
    showlegend = false
  )

  // demo source end

}
