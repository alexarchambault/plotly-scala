package plotly.demo.threed

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object SurfaceWithContours extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/3d-surface-plots/#surface-plot-with-contours"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = SurfaceWithContoursSource.source

  // demo source start

  val xs = (-30 to 30).map(_ / 10.0)
  val ys = (-30 to 30).map(_ / 10.0)

  // a damped wave
  val z = ys.map(y => xs.map(x => math.cos(math.hypot(x, y) * 2) * math.exp(-math.hypot(x, y) / 2)))

  val data = Seq(
    Surface().withValues(
      x = xs,
      y = ys,
      z = z,
      colorscale = ColorScale.NamedScale("Viridis"),
      // the contour lines along z, drawn on the surface and projected on the floor of the scene
      contours = SurfaceContours().withValues(
        z = SurfaceContour().withValues(
          show = true,
          usecolormap = true,
          highlightcolor = Color.StringColor("white"),
          project = Projection().withValues(z = true)
        )
      ),
      lighting = Lighting().withValues(specular = 0.6, roughness = 0.4),
      colorbar = ColorBar().withValues(thickness = 15.0)
    )
  )

  val layout = Layout().withValues(
    title = "Damped Wave",
    margin = Margin(0, 0, 80, 0)
  )

  // demo source end

}
