package plotly.demo.threed

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object ClusteredScatterPlot extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/3d-scatter-plots/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = ClusteredScatterPlotSource.source

  // demo source start

  // three clusters of points around their centers
  val random   = new scala.util.Random(21) // fixed seed, so that the plot is the same every time
  val clusters = Seq(
    ("A", (0.0, 0.0, 0.0), Color.RGB(99, 110, 250)),
    ("B", (4.0, 1.0, 3.0), Color.RGB(239, 85, 59)),
    ("C", (1.0, 4.0, 5.0), Color.RGB(0, 204, 150))
  )

  val data = clusters.map { case (name, (cx, cy, cz), color) =>
    val points = Seq.fill(80)((cx + random.nextGaussian(), cy + random.nextGaussian(), cz + random.nextGaussian()))
    Scatter3d(points.map(_._1), points.map(_._2), points.map(_._3)).updated(
      name = s"cluster $name",
      mode = ScatterMode(ScatterMode.Markers),
      marker = Marker().updated(
        size = 4,
        color = color,
        opacity = 0.8,
        line = Line().updated(color = Color.StringColor("white"), width = 0.5)
      )
    )
  }

  val layout = Layout().updated(
    title = "Three Clusters",
    margin = Margin(0, 0, 80, 0)
  )

  // demo source end

}
