package plotly
package layout

import java.lang.{ Integer => JInt, Double => JDouble, Boolean => JBoolean }

import dataclass.data
import plotly.element._

@data(optionSetters = true) class Layout(
          title: Option[String] = None,
         legend: Option[Legend] = None,
          width: Option[Int] = None,
         height: Option[Int] = None,
     showlegend: Option[Boolean] = None,
          xaxis: Option[Axis] = None,
          yaxis: Option[Axis] = None,
         xaxis1: Option[Axis] = None,
         xaxis2: Option[Axis] = None,
         xaxis3: Option[Axis] = None,
         xaxis4: Option[Axis] = None,
         yaxis1: Option[Axis] = None,
         yaxis2: Option[Axis] = None,
         yaxis3: Option[Axis] = None,
         yaxis4: Option[Axis] = None,
        barmode: Option[BarMode] = None,
       autosize: Option[Boolean] = None,
         margin: Option[Margin] = None,
    annotations: Option[Seq[Annotation]] = None,
   plot_bgcolor: Option[Color] = None,
  paper_bgcolor: Option[Color] = None,
           font: Option[Font] = None,
         bargap: Option[Double] = None,
    bargroupgap: Option[Double] = None,
      hovermode: Option[HoverMode] = None,
        boxmode: Option[BoxMode] = None,
          scene: Option[Scene] = None,
       dragmode: Option[String],
         shapes: Option[Seq[Shape]]
)

object Layout {
  @deprecated("Use Layout() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
            title: String          = null,
           legend: Legend          = null,
            width: JInt            = null,
           height: JInt            = null,
       showlegend: JBoolean        = null,
            xaxis: Axis            = null,
            yaxis: Axis            = null,
           xaxis1: Axis            = null,
           xaxis2: Axis            = null,
           xaxis3: Axis            = null,
           xaxis4: Axis            = null,
           yaxis1: Axis            = null,
           yaxis2: Axis            = null,
           yaxis3: Axis            = null,
           yaxis4: Axis            = null,
          barmode: BarMode         = null,
         autosize: JBoolean        = null,
           margin: Margin          = null,
      annotations: Seq[Annotation] = null,
     plot_bgcolor: Color           = null,
    paper_bgcolor: Color           = null,
             font: Font            = null,
           bargap: JDouble         = null,
      bargroupgap: JDouble         = null,
        hovermode: HoverMode       = null,
          boxmode: BoxMode         = null,
            scene: Scene           = null,
         dragmode: String          = null,
           shapes: Seq[Shape]      = null
  ): Layout =
    new Layout(
      Option(title),
      Option(legend),
      Option(width).map(x => x),
      Option(height).map(x => x),
      Option(showlegend).map(x => x),
      Option(xaxis),
      Option(yaxis),
      Option(xaxis1),
      Option(xaxis2),
      Option(xaxis3),
      Option(xaxis4),
      Option(yaxis1),
      Option(yaxis2),
      Option(yaxis3),
      Option(yaxis4),
      Option(barmode),
      Option(autosize).map(x => x),
      Option(margin),
      Option(annotations),
      Option(plot_bgcolor),
      Option(paper_bgcolor),
      Option(font),
      Option(bargap).map(x => x),
      Option(bargroupgap).map(x => x),
      Option(hovermode),
      Option(boxmode),
      Option(scene),
      Option(dragmode),
      Option(shapes)
    )
}
