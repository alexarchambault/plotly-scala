package plotly.demo.heatmaps

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object CustomColorScaleHeatmap extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/colorscales/#custom-colorscale-for-contour-plot"
  def id           = "custom-colorscale-heatmap"
  def source       = CustomColorScaleHeatmapSource.source

  // demo source start

  // MATLAB's "peaks" function
  def peaks(x: Double, y: Double): Double =
    3 * math.pow(1 - x, 2) * math.exp(-x * x - math.pow(y + 1, 2)) -
      10 * (x / 5 - math.pow(x, 3) - math.pow(y, 5)) * math.exp(-x * x - y * y) -
      math.exp(-math.pow(x + 1, 2) - y * y) / 3

  val coords = (-30 to 30).map(_ / 10.0)

  val data = Seq(
    Heatmap().updated(
      z = coords.map(y => coords.map(x => peaks(x, y))),
      x = coords,
      y = coords,
      colorscale = ColorScale.CustomScale(
        Seq(
          (0, Color.RGB(18, 20, 43)),
          (0.3, Color.RGB(74, 85, 232)),
          (0.5, Color.RGB(171, 99, 250)),
          (0.7, Color.RGB(255, 102, 146)),
          (1, Color.RGB(255, 209, 102))
        )
      )
    )
  )

  val layout = Layout().updated(title = "The Peaks Function")

  // demo source end

}
