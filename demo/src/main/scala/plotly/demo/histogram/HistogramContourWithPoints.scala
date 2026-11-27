package plotly.demo.histogram

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object HistogramContourWithPoints extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/2d-density-plots/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = HistogramContourWithPointsSource.source

  // demo source start

  // two clusters of points
  val (xs, ys) = {
    // fixed seed, so that the plot is the same every time
    val random   = new scala.util.Random(7)
    val clusters = Seq((-1.5, -1.0, 0.7), (1.2, 1.0, 0.9))
    Seq
      .fill(1500) {
        val (cx, cy, sd) = clusters(random.nextInt(clusters.length))
        (cx + sd * random.nextGaussian(), cy + sd * random.nextGaussian())
      }
      .unzip
  }

  val points = Scatter(xs, ys).updated(
    name = "points",
    mode = ScatterMode(ScatterMode.Markers),
    marker = Marker().updated(size = 3, color = Color.RGBA(30, 30, 30, 0.35))
  )

  val density = Histogram2dContour(xs, ys).updated(
    name = "density",
    ncontours = 15,
    colorscale = ColorScale.NamedScale("Hot"),
    reversescale = true,
    showscale = false,
    contours = Contours().updated(coloring = ContourColoring.Fill)
  )

  // the density below, the points above
  val data = Seq(density, points)

  val layout = Layout().updated(
    title = "Density of Two Clusters",
    showlegend = false,
    xaxis = Axis().updated(showgrid = false, zeroline = false),
    yaxis = Axis().updated(showgrid = false, zeroline = false)
  )

  // demo source end

}
