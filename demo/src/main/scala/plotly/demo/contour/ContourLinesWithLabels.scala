package plotly.demo.contour

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object ContourLinesWithLabels extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/contour-plots/#contour-lines"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = ContourLinesWithLabelsSource.source

  // demo source start

  val xs = (0 to 40).map(_ * math.Pi / 20)
  val ys = (0 to 40).map(_ * math.Pi / 20)

  // an egg-crate surface
  val z = ys.map(y => xs.map(x => math.sin(x) * math.cos(y)))

  val data = Seq(
    Contour(z, xs, ys).updated(
      ncontours = 12,
      // only lines, colored by level, and labelled with it
      contours = Contours().updated(
        coloring = ContourColoring.Lines,
        showlabels = true,
        labelfont = Font(12, "Inter, sans-serif", Color.StringColor("gray"))
      ),
      line = Line().updated(width = 2.0, smoothing = 0.85),
      colorscale = ColorScale.NamedScale("RdBu"),
      showscale = false
    )
  )

  val layout = Layout().updated(
    title = "sin(x) cos(y)",
    xaxis = Axis().updated(showgrid = false, zeroline = false),
    yaxis = Axis().updated(showgrid = false, zeroline = false)
  )

  // demo source end

}
