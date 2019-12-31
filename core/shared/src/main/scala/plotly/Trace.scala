package plotly

import scala.language.implicitConversions

import java.lang.{ Boolean => JBoolean, Double => JDouble }

import dataclass.data
import plotly.element._

sealed abstract class Trace extends Product with Serializable

@data class Scatter(
             x: Option[Sequence],
             y: Option[Sequence],
          text: Option[OneOrSeq[String]],
          mode: Option[ScatterMode],
        marker: Option[Marker],
          line: Option[Line],
  textposition: Option[TextPosition],
      textfont: Option[TextFont],
          name: Option[String],
   connectgaps: Option[Boolean],
         xaxis: Option[AxisReference],
         yaxis: Option[AxisReference],
          fill: Option[Fill],
       error_x: Option[Error],
       error_y: Option[Error],
    showlegend: Option[Boolean],
     fillcolor: Option[OneOrSeq[Color]],
     hoverinfo: Option[HoverInfo],
       hoveron: Option[HoverOn],
    stackgroup: Option[String],
     groupnorm: Option[GroupNorm]
) extends Trace

object Scatter {
  def apply(
          values: Sequence         = null,
    secondValues: Sequence         = null,
            text: OneOrSeq[String] = null,
            mode: ScatterMode      = null,
          marker: Marker           = null,
            line: Line             = null,
    textposition: TextPosition     = null,
        textfont: TextFont         = null,
            name: String           = null,
     connectgaps: JBoolean         = null,
           xaxis: AxisReference    = null,
           yaxis: AxisReference    = null,
            fill: Fill             = null,
         error_x: Error            = null,
         error_y: Error            = null,
      showlegend: JBoolean         = null,
       fillcolor: OneOrSeq[Color]  = null,
       hoverinfo: HoverInfo        = null,
         hoveron: HoverOn          = null,
      stackgroup: String           = null,
       groupnorm: GroupNorm        = null
  ): Scatter = {

    val (xOpt, yOpt) = Option(secondValues) match {
      case Some(y) => (Option(values), Some(y))
      case None => (None, Option(values))
    }

    Scatter(
      xOpt,
      yOpt,
      Option(text),
      Option(mode),
      Option(marker),
      Option(line),
      Option(textposition),
      Option(textfont),
      Option(name),
      Option(connectgaps) .map(x => x: Boolean),
      Option(xaxis),
      Option(yaxis),
      Option(fill),
      Option(error_x),
      Option(error_y),
      Option(showlegend)  .map(b => b: Boolean),
      Option(fillcolor),
      Option(hoverinfo),
      Option(hoveron),
      Option(stackgroup),
      Option(groupnorm)
    )
  }
}

@data class Box(
             y: Option[Sequence],
             x: Option[Sequence],
     boxpoints: Option[BoxPoints],
        jitter: Option[Double],
      pointpos: Option[Double],
          name: Option[String],
        marker: Option[Marker],
   orientation: Option[Orientation],
  whiskerwidth: Option[Double],
       boxmean: Option[BoxMean],
     fillcolor: Option[OneOrSeq[Color]],
          line: Option[Line],
    showlegend: Option[Boolean]
) extends Trace

object Box {
  def apply(
               y: Sequence        = null,
               x: Sequence        = null,
       boxpoints: BoxPoints       = null,
          jitter: JDouble         = null,
        pointpos: JDouble         = null,
            name: String          = null,
          marker: Marker          = null,
     orientation: Orientation     = null,
    whiskerwidth: JDouble         = null,
         boxmean: BoxMean         = null,
       fillcolor: OneOrSeq[Color] = null,
            line: Line            = null,
      showlegend: JBoolean        = null
  ): Box =
    Box(
      Option(y),
      Option(x),
      Option(boxpoints),
      Option(jitter)       .map(d => d: Double),
      Option(pointpos)     .map(d => d: Double),
      Option(name),
      Option(marker),
      Option(orientation),
      Option(whiskerwidth) .map(d => d: Double),
      Option(boxmean),
      Option(fillcolor),
      Option(line),
      Option(showlegend)   .map(b => b: Boolean)
    )
}

@data class Bar(
             x: Sequence,
             y: Sequence,
          name: Option[String],
          text: Option[Seq[String]],
        marker: Option[Marker],
   orientation: Option[Orientation],
         xaxis: Option[AxisReference],
         yaxis: Option[AxisReference],
       error_y: Option[Error],
    showlegend: Option[Boolean],
     hoverinfo: Option[HoverInfo],
  textposition: Option[BarTextPosition],
       opacity: Option[Double],
         width: Option[OneOrSeq[Double]],
          base: Option[OneOrSeq[Double]]
) extends Trace

object Bar {
  def apply(
               x: Sequence,
               y: Sequence,
            name: String           = null,
            text: Seq[String]      = null,
          marker: Marker           = null,
     orientation: Orientation      = null,
           xaxis: AxisReference    = null,
           yaxis: AxisReference    = null,
         error_y: Error            = null,
      showlegend: JBoolean         = null,
       hoverinfo: HoverInfo        = null,
    textposition: BarTextPosition  = null,
         opacity: JDouble          = null,
           width: OneOrSeq[Double] = null,
            base: OneOrSeq[Double] = null
  ): Bar =
    Bar(
      x,
      y,
      Option(name),
      Option(text),
      Option(marker),
      Option(orientation),
      Option(xaxis),
      Option(yaxis),
      Option(error_y),
      Option(showlegend).map(b => b: Boolean),
      Option(hoverinfo),
      Option(textposition),
      Option(opacity).map(d => d: Double),
      Option(width),
      Option(base)
    )
}

@data class Histogram(
          x: Option[Sequence],
          y: Option[Sequence],
    opacity: Option[Double],
       name: Option[String],
   autobinx: Option[Boolean],
     marker: Option[Marker],
      xbins: Option[Bins],
   histnorm: Option[HistNorm],
 showlegend: Option[Boolean],
 cumulative: Option[Cumulative],
   histfunc: Option[HistFunc]
) extends Trace

object Histogram {
  def apply(
           x: Sequence   = null,
           y: Sequence   = null,
     opacity: JDouble    = null,
        name: String     = null,
    autobinx: JBoolean   = null,
      marker: Marker     = null,
       xbins: Bins       = null,
    histnorm: HistNorm   = null,
  showlegend: JBoolean   = null,
  cumulative: Cumulative = null,
    histfunc: HistFunc   = null
  ): Histogram =
    Histogram(
      Option(x),
      Option(y),
      Option(opacity)    .map(d => d: Double),
      Option(name),
      Option(autobinx)   .map(b => b: Boolean),
      Option(marker),
      Option(xbins),
      Option(histnorm),
      Option(showlegend) .map(b => b: Boolean),
      Option(cumulative),
      Option(histfunc)
    )
}

@data class Surface(
          x: Option[Sequence],
          y: Option[Sequence],
          z: Option[Sequence],
  showscale: Option[Boolean],
    opacity: Option[Double]
) extends Trace

object Surface {
  def apply(
            x: Sequence  = null,
            y: Sequence  = null,
            z: Sequence  = null,
    showscale: JBoolean  = null,
      opacity: JDouble   = null
  ): Surface =
    Surface(
      Option(x),
      Option(y),
      Option(z),
      Option(showscale) .map(b => b: Boolean),
      Option(opacity)   .map(d => d: Double)
    )
}

@data class Heatmap(
             y: Option[Sequence],
             x: Option[Sequence],
             z: Option[Sequence],
          name: Option[String]
) extends Trace

object Heatmap {
  def apply(
               y: Sequence        = null,
               x: Sequence        = null,
               z: Sequence        = null,
            name: String          = null
  ): Heatmap =
    Heatmap(
      Option(y),
      Option(x),
      Option(z),
      Option(name)
    )
}
