package plotly

import scala.language.implicitConversions

import java.lang.{ Boolean => JBoolean, Double => JDouble }

import java.time.LocalDateTime

import plotly.element._

sealed abstract class Trace extends Product with Serializable

final case class Scatter(
             x: Option[Sequence],
             y: Option[Sequence],
          text: Option[Seq[String]],
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
       error_y: Option[Error]
) extends Trace

object Scatter {
  def apply(
          values: Sequence      = null,
    secondValues: Sequence      = null,
            text: Seq[String]   = null,
            mode: ScatterMode   = null,
          marker: Marker        = null,
            line: Line          = null,
    textposition: TextPosition  = null,
        textfont: TextFont      = null,
            name: String        = null,
     connectgaps: JBoolean      = null,
           xaxis: AxisReference = null,
           yaxis: AxisReference = null,
            fill: Fill          = null,
         error_x: Error         = null,
         error_y: Error         = null
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
      Option(connectgaps).map(x => x: Boolean),
      Option(xaxis),
      Option(yaxis),
      Option(fill),
      Option(error_x),
      Option(error_y)
    )
  }
}

case class Box(
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
          line: Option[Line]
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
            line: Line            = null
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
      Option(line)
    )
}

final case class Bar(
            x: Sequence,
            y: Sequence,
         name: Option[String],
         text: Option[Seq[String]],
       marker: Option[Marker],
  orientation: Option[Orientation],
        xaxis: Option[AxisReference],
        yaxis: Option[AxisReference],
      error_y: Option[Error]
) extends Trace

object Bar {
  def apply(
              x: Sequence,
              y: Sequence,
           name: String        = null,
           text: Seq[String]   = null,
         marker: Marker        = null,
    orientation: Orientation   = null,
          xaxis: AxisReference = null,
          yaxis: AxisReference = null,
        error_y: Error         = null
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
      Option(error_y)
    )
}

case class Histogram(
         x: Option[Sequence],
         y: Option[Sequence],
   opacity: Option[Double],
      name: Option[String],
  autobinx: Option[Boolean],
    marker: Option[Marker],
     xbins: Option[Bins],
  histnorm: Option[HistNorm]
) extends Trace

object Histogram {
  def apply(
           x: Sequence = null,
           y: Sequence = null,
     opacity: JDouble  = null,
        name: String   = null,
    autobinx: JBoolean = null,
      marker: Marker   = null,
       xbins: Bins     = null,
    histnorm: HistNorm = null
  ): Histogram =
    Histogram(
      Option(x),
      Option(y),
      Option(opacity)  .map(d => d: Double),
      Option(name),
      Option(autobinx) .map(b => b: Boolean),
      Option(marker),
      Option(xbins),
      Option(histnorm)
    )
}


case class Candlestick[T](
                    dates: Seq[LocalDateTime],
                    open: Seq[T],
                    high: Seq[T],
                    low: Seq[T],
                    close: Seq[T],
                    name: String
                  )