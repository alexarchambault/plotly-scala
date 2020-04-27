package plotly.demo.heatmaps

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object AnnotatedHeatmap extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/heatmaps/#annotated-heatmap"
  def id = "annotated-heatmap"
  def source = AnnotatedHeatmapSource.source

  // demo source start

  val x = Seq("A", "B", "C", "D", "E");
  val y = Seq("W", "X", "Y", "Z");
  val z = Seq(
    Seq(0.00, 0.00, 0.75, 0.75, 0.00),
    Seq(0.00, 0.00, 0.75, 0.75, 0.00),
    Seq(0.75, 0.75, 0.75, 0.75, 0.75),
    Seq(0.00, 0.00, 0.00, 0.75, 0.00)
  )

  val data = Seq(
    Heatmap(
      z=z, x=x, y=y, showscale=false,
      colorscale = ColorScale.CustomScale(Seq(
        (0, Color.StringColor("#3D9970")),
        (1, Color.StringColor("#001f3f"))
      ))
    )
  )

  val layout = Layout(
    title = "Annotated Heatmap",
    xaxis = Axis().withTicks(Ticks.Empty).withSide(Side.Top),
    yaxis = Axis().withTicks(Ticks.Empty).withTicksuffix(" "),
    annotations = for {
      (xv, xi) <- x.zipWithIndex;
      (yv, yi) <- y.zipWithIndex
    } yield Annotation()
      .withX(xv)
      .withY(yv)
      .withXref(Ref.Axis(AxisReference.X1))
      .withYref(Ref.Axis(AxisReference.Y1))
      .withShowarrow(false)
      .withText(z(yi)(xi).toString)
      .withFont(Font(color=Color.StringColor("white")))
  )

  // demo source end

}