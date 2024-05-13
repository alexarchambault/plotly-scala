package plotly.demo.heatmaps

import plotly._
import plotly.demo.NoLayoutDemoChart
import plotly.element._

object CategoricalAxisHeatmap extends NoLayoutDemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/heatmaps/#heatmap-with-categorical-axis-labels"
  def id           = "categorical-axis-heatmap"
  def source       = CategoricalAxisHeatmapSource.source

  // demo source start

  val data = Seq(
    Heatmap()
      .withZ(
        Seq(
          Seq(1, null.asInstanceOf[Int], 30, 50, 1),
          Seq(20, 1, 60, 80, 30),
          Seq(30, 60, 1, -10, 20)
        )
      )
      .withX(Seq("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"))
      .withY(Seq("Morning", "Afternoon", "Evening"))
  )

  // demo source end

}
