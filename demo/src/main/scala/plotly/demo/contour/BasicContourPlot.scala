package plotly.demo.contour

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicContourPlot extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/contour-plots/#basic-contour-plot"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = BasicContourPlotSource.source

  // demo source start

  val xs = (-30 to 30).map(_ / 10.0)
  val ys = (-20 to 20).map(_ / 10.0)

  // two hills and a valley
  def height(x: Double, y: Double) = {
    def bump(cx: Double, cy: Double, h: Double) =
      h * math.exp(-((x - cx) * (x - cx) + (y - cy) * (y - cy)))
    bump(-1.2, 0.4, 3) + bump(1.4, -0.3, 2) - bump(0.2, 1.4, 1.5)
  }

  val data = Seq(
    Contour(ys.map(y => xs.map(x => height(x, y))), xs, ys).updated(
      colorscale = ColorScale.NamedScale("Earth"),
      contours = Contours().updated(start = -1.5, end = 3.0, size = 0.25),
      colorbar = ColorBar().updated(title = Title.from(text = "Altitude", side = Side.Right))
    )
  )

  val layout = Layout().updated(title = "Two Hills and a Valley")

  // demo source end

}
