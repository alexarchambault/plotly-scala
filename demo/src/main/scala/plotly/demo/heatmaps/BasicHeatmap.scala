package plotly.demo.heatmaps

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicHeatmap extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/heatmaps/#basic-heatmap"
  def id           = "basic-heatmap"
  def source       = BasicHeatmapSource.source

  // demo source start

  // ripples spreading from the center of a 60×60 grid
  val z = Seq.tabulate(60, 60) { (i, j) =>
    val r = math.hypot(i - 30, j - 30)
    math.cos(r / 2.5) * math.exp(-r / 25)
  }

  val data = Seq(
    Heatmap().updated(z = z, colorscale = ColorScale.NamedScale("Viridis"))
  )

  val layout = Layout().updated(title = "Ripples")

  // demo source end

}
