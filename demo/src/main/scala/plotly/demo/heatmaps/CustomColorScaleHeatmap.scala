package plotly.demo.heatmaps

import plotly._
import plotly.demo.NoLayoutDemoChart
import plotly.element._

object CustomColorScaleHeatmap extends NoLayoutDemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/colorscales/#custom-colorscale-for-contour-plot"
  def id = "custom-colorscale-heatmap"
  def source = CustomColorScaleHeatmapSource.source

  // demo source start

  val data = Seq(
    Heatmap(
      z=Seq(
        Seq(10.0, 10.625, 12.5, 15.625, 20.0),
        Seq(5.625, 6.25, 8.125, 11.25, 15.625),
        Seq(2.5, 3.125, 5.0, 8.125, 12.5),
        Seq(0.625, 1.25, 3.125, 6.25, 10.625),
        Seq(0.0, 0.625, 2.5, 5.625, 10.0)
      ),
      colorscale=ColorScale.CustomScale(Seq(
        (0, Color.RGB(166,206,227)),
        (0.25, Color.RGB(31,120,180)),
        (0.45, Color.RGB(178,223,138)),
        (0.65, Color.RGB(51,160,44)),
        (0.85, Color.RGB(251,154,153)),
        (1, Color.RGB(227,26,28))
      ))
    )
  )

  // demo source end

}