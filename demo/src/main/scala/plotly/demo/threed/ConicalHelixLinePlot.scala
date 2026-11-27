package plotly.demo.threed

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object ConicalHelixLinePlot extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/3d-line-plots/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = ConicalHelixLinePlotSource.source

  // demo source start

  // a conical helix, colored by height
  val ts = (0 to 400).map(_ * 0.05)
  val xs = ts.map(t => (1 + t / 4) * math.cos(t))
  val ys = ts.map(t => (1 + t / 4) * math.sin(t))
  val zs = ts

  val data = Seq(
    Scatter3d(xs, ys, zs).updated(
      mode = ScatterMode(ScatterMode.Lines),
      line = Line().updated(
        width = 8.0,
        // numeric colors, mapped to colors by the color scale
        color = zs.map(Color.Number(_)),
        colorscale = ColorScale.NamedScale("Viridis"),
        showscale = true,
        colorbar = ColorBar().updated(title = "height", thickness = 15.0)
      )
    )
  )

  val layout = Layout().updated(
    title = "Conical Helix",
    margin = Margin(0, 0, 80, 0)
  )

  // demo source end

}
