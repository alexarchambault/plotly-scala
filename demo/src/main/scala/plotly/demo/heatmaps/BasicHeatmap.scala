package plotly.demo.heatmaps

import plotly._
import plotly.demo.NoLayoutDemoChart
import plotly.element._

object BasicHeatmap extends NoLayoutDemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/heatmaps/#basic-heatmap"
  def id           = "basic-heatmap"
  def source       = BasicHeatmapSource.source

  // demo source start

  val data = Seq(
    Heatmap()
      .withZ(
        Seq(
          Seq(1, 20, 30),
          Seq(20, 1, 60),
          Seq(30, 60, 1)
        )
      )
      .withColorscale(ColorScale.NamedScale("Portland"))
  )

  // demo source end

}
