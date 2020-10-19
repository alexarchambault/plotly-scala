package plotly

import scala.language.implicitConversions

import java.lang.{ Boolean => JBoolean, Double => JDouble }

import dataclass._
import plotly.element._

sealed abstract class Trace extends Product with Serializable

@data(optionSetters = true) class Scatter(
             x: Option[Sequence] = None,
             y: Option[Sequence] = None,
          text: Option[OneOrSeq[String]] = None,
          mode: Option[ScatterMode] = None,
        marker: Option[Marker] = None,
          line: Option[Line] = None,
  textposition: Option[TextPosition] = None,
      textfont: Option[TextFont] = None,
          name: Option[String] = None,
   connectgaps: Option[Boolean] = None,
         xaxis: Option[AxisReference] = None,
         yaxis: Option[AxisReference] = None,
          fill: Option[Fill] = None,
       error_x: Option[Error] = None,
       error_y: Option[Error] = None,
    showlegend: Option[Boolean] = None,
     fillcolor: Option[OneOrSeq[Color]] = None,
     hoverinfo: Option[HoverInfo] = None,
       hoveron: Option[HoverOn] = None,
    stackgroup: Option[String] = None,
     groupnorm: Option[GroupNorm] = None
) extends Trace

object Scatter {
  def apply(x: Sequence, y: Sequence): Scatter =
    Scatter().withX(x).withY(y)

  def apply(y: Sequence): Scatter =
    Scatter().withY(y)

  @deprecated("Use Scatter() and chain-call .with* methods on it instead", "0.8.0")
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

@data(optionSetters = true) class Box(
             y: Option[Sequence] = None,
             x: Option[Sequence] = None,
     boxpoints: Option[BoxPoints] = None,
        jitter: Option[Double] = None,
      pointpos: Option[Double] = None,
          name: Option[String] = None,
        marker: Option[Marker] = None,
   orientation: Option[Orientation] = None,
  whiskerwidth: Option[Double] = None,
       boxmean: Option[BoxMean] = None,
     fillcolor: Option[OneOrSeq[Color]] = None,
          line: Option[Line] = None,
    showlegend: Option[Boolean] = None
) extends Trace

object Box {
  def apply(y: Sequence): Box =
    Box().withY(y)

  def apply(y: Sequence, x: Sequence): Box =
    Box().withY(y).withX(x)

  @deprecated("Use Box() and chain-call .with* methods on it instead", "0.8.0")
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

@data(optionSetters = true) class Bar(
             x: Sequence,
             y: Sequence,
  @since
          name: Option[String] = None,
          text: Option[Seq[String]] = None,
        marker: Option[Marker] = None,
   orientation: Option[Orientation] = None,
         xaxis: Option[AxisReference] = None,
         yaxis: Option[AxisReference] = None,
       error_y: Option[Error] = None,
    showlegend: Option[Boolean] = None,
     hoverinfo: Option[HoverInfo] = None,
  textposition: Option[BarTextPosition] = None,
       opacity: Option[Double] = None,
         width: Option[OneOrSeq[Double]] = None,
          base: Option[OneOrSeq[Double]] = None
) extends Trace

object Bar {
  @deprecated("Use Bar() and chain-call .with* methods on it instead", "0.8.0")
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
      Option(showlegend) .map(b => b: Boolean),
      Option(hoverinfo),
      Option(textposition),
      Option(opacity)    .map(d => d: Double),
      Option(width),
      Option(base)
    )
}

@data(optionSetters = true) class Histogram(
          x: Option[Sequence] = None,
          y: Option[Sequence] = None,
    opacity: Option[Double] = None,
       name: Option[String] = None,
   autobinx: Option[Boolean] = None,
     marker: Option[Marker] = None,
      xbins: Option[Bins] = None,
   histnorm: Option[HistNorm] = None,
 showlegend: Option[Boolean] = None,
 cumulative: Option[Cumulative] = None,
   histfunc: Option[HistFunc] = None
) extends Trace

object Histogram {
  def apply(x: Sequence): Histogram =
    Histogram().withX(x)

  def apply(x: Sequence, y: Sequence): Histogram =
    Histogram().withX(x).withY(y)

  @deprecated("Use Histogram() and chain-call .with* methods on it instead", "0.8.0")
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

@data(optionSetters = true) class Surface(
          x: Option[Sequence] = None,
          y: Option[Sequence] = None,
          z: Option[Sequence] = None,
  showscale: Option[Boolean] = None,
    opacity: Option[Double] = None
) extends Trace

object Surface {
  @deprecated("Use Surface() and chain-call .with* methods on it instead", "0.8.0")
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

@data(optionSetters = true) class Heatmap(
             y: Option[Sequence] = None,
             x: Option[Sequence] = None,
             z: Option[Sequence] = None,
autocolorscale: Option[Boolean] = None,
    colorscale: Option[ColorScale] = None,
     showscale: Option[Boolean] = None,
          name: Option[String] = None
) extends Trace

object Heatmap {
  def apply(z: Sequence): Heatmap =
    Heatmap().withZ(z)

  def apply(z: Sequence, x: Sequence, y: Sequence): Heatmap =
    Heatmap().withZ(z).withX(x).withY(y)

  @deprecated("Use Heatmap() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
               y: Sequence        = null,
               x: Sequence        = null,
               z: Sequence        = null,
  autocolorscale: JBoolean        = null,
      colorscale: ColorScale      = null,
       showscale: JBoolean        = null,
            name: String          = null
  ): Heatmap =
    Heatmap(
      Option(y),
      Option(x),
      Option(z),
      Option(autocolorscale) .map(b => b: Boolean),
      Option(colorscale),
      Option(showscale)      .map(b => b: Boolean),
      Option(name)
    )
}

@data(optionSetters = true) class Candlestick(
           x: Option[Sequence] = None,
       close: Option[Sequence] = None,
        high: Option[Sequence] = None,
         low: Option[Sequence] = None,
        open: Option[Sequence] = None,
  decreasing: Option[Marker] = None,
  increasing: Option[Marker] = None,
        line: Option[Marker] = None,
       xaxis: Option[AxisReference] = None,
       yaxis: Option[AxisReference] = None
) extends Trace

object Candlestick {
  @deprecated("Use Candlestick() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
             x: Sequence      = null,
         close: Sequence      = null,
          high: Sequence      = null,
           low: Sequence      = null,
          open: Sequence      = null,
    decreasing: Marker        = null,
    increasing: Marker        = null,
          line: Marker        = null,
         xaxis: AxisReference = null,
         yaxis: AxisReference = null
  ): Candlestick =
    Candlestick(
      Option(x),
      Option(close),
      Option(high),
      Option(low),
      Option(open),
      Option(decreasing),
      Option(increasing),
      Option(line),
      Option(xaxis),
      Option(yaxis)
    )
}
