package plotly
package layout

import java.lang.{ Integer => JInt, Double => JDouble, Boolean => JBoolean }

import plotly.element._
import dataclass.data
import scala.collection.Seq

@data class Layout(
          title: Option[String],
         legend: Option[Legend],
          width: Option[Int],
         height: Option[Int],
     showlegend: Option[Boolean],
          xaxis: Option[Axis],
          yaxis: Option[Axis],
         xaxis1: Option[Axis],
         xaxis2: Option[Axis],
         xaxis3: Option[Axis],
         xaxis4: Option[Axis],
         yaxis1: Option[Axis],
         yaxis2: Option[Axis],
         yaxis3: Option[Axis],
         yaxis4: Option[Axis],
        barmode: Option[BarMode],
       autosize: Option[Boolean],
         margin: Option[Margin],
    annotations: Option[Seq[Annotation]],
   plot_bgcolor: Option[Color],
  paper_bgcolor: Option[Color],
           font: Option[Font],
         bargap: Option[Double],
    bargroupgap: Option[Double],
      hovermode: Option[HoverMode],
        boxmode: Option[BoxMode],
          scene: Option[Scene]

)

object Layout {
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
            scene: Scene           = null
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
      Option(scene)
    )
}
