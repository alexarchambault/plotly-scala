package plotly.demo.heatmaps

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object AnnotatedHeatmap extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/heatmaps/#annotated-heatmap"
  def id           = "annotated-heatmap"
  def source       = AnnotatedHeatmapSource.source

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
    Heatmap(z, x, y)
      .copy(
        showscale = Some(false),
        colorscale = Some(
          ColorScale.CustomScale(
            Seq(
              (0, Color.StringColor("#3D9970")),
              (1, Color.StringColor("#001f3f"))
            )
          )
        )
      )
  )

  val layout = Layout().copy(
    title = Some("Annotated Heatmap"),
    xaxis = Some(Axis().copy(ticks = Some(Ticks.Empty), side = Some(Side.Top))),
    yaxis = Some(Axis().copy(ticks = Some(Ticks.Empty), ticksuffix = Some(" "))),
    annotations = Some(
      for {
        (xv, xi) <- x.zipWithIndex
        (yv, yi) <- y.zipWithIndex
      } yield Annotation().copy(
        x = Some(xv),
        y = Some(yv),
        xref = Some(Ref.Axis(AxisReference.X1)),
        yref = Some(Ref.Axis(AxisReference.Y1)),
        showarrow = Some(false),
        text = Some(z(yi)(xi).toString),
        font = Some(Font(Color.StringColor("white")))
      )
    )
  )

  // demo source end

}
