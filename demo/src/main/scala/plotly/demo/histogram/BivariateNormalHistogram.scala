package plotly.demo.histogram

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BivariateNormalHistogram extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/2D-Histogram/#2d-histogram-of-a-bivariate-normal-distribution"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = BivariateNormalHistogramSource.source

  // demo source start

  // 5,000 points of a bivariate normal distribution, with correlated coordinates
  val (xs, ys) = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(42)
    Seq
      .fill(5000) {
        val x = random.nextGaussian()
        val y = 0.6 * x + 0.8 * random.nextGaussian()
        (x, y)
      }
      .unzip
  }

  val data = Seq(
    Histogram2d(xs, ys).updated(
      xbins = Bins(-4.0, 4.0, 0.25),
      ybins = Bins(-4.0, 4.0, 0.25),
      colorscale = ColorScale.NamedScale("Blues"),
      reversescale = true,
      colorbar = ColorBar().updated(title = "Points")
    )
  )

  val layout = Layout().updated(
    title = "Bivariate Normal Distribution",
    xaxis = Axis().updated(title = "x"),
    yaxis = Axis().updated(title = "y")
  )

  // demo source end

}
