package plotly.demo.heatmaps

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object SharedColorAxis extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/colorscales/#share-color-axis"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = SharedColorAxisSource.source

  // demo source start

  val hours = (0 until 24).map(h => s"${h}h")
  val days  = Seq("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

  // simulated temperatures, in °C, of two cities, over a week
  def temperatures(mean: Double, amplitude: Double, seed: Int) = {
    val random = new scala.util.Random(seed) // fixed seed, so that the plot is the same every time
    days.indices.map { d =>
      val dayMean = mean + 2 * random.nextGaussian()
      hours.indices.map(h => dayMean + amplitude * math.sin((h - 9) * math.Pi / 12))
    }
  }

  // both heatmaps use the color scale and bounds of the same color axis, so that their colors
  // compare, and share its color bar
  val data = Seq(
    Heatmap(temperatures(8.0, 5.0, 1), hours, days).withValues(name = "Oslo", coloraxis = "coloraxis"),
    Heatmap(temperatures(24.0, 6.0, 2), hours, days).withValues(
      name = "Seville",
      coloraxis = "coloraxis",
      xaxis = AxisReference.X2,
      yaxis = AxisReference.Y2
    )
  )

  val layout = Layout().withValues(
    title = "Temperatures in Oslo and Seville",
    coloraxis = ColorAxis().withValues(
      colorscale = ColorScale.NamedScale("RdBu"),
      colorbar = ColorBar().withValues(ticksuffix = "°C", thickness = 15.0)
    ),
    grid = Grid().withValues(rows = 1, columns = 2, pattern = Pattern.Independent),
    xaxis = Axis().withValues(title = "Oslo", dtick = 6.0),
    xaxis2 = Axis().withValues(title = "Seville", dtick = 6.0)
  )

  // demo source end

}
