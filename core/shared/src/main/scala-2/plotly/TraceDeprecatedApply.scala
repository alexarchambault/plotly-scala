package plotly

import scala.language.implicitConversions
import java.lang.{Boolean => JBoolean, Double => JDouble}
import plotly.element._

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait ScatterDeprecatedApply {
  @deprecated("Use Scatter() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      values: Sequence = null,
      secondValues: Sequence = null,
      text: OneOrSeq[String] = null,
      mode: ScatterMode = null,
      marker: Marker = null,
      line: Line = null,
      textposition: TextPosition = null,
      textfont: TextFont = null,
      name: String = null,
      connectgaps: JBoolean = null,
      xaxis: AxisReference = null,
      yaxis: AxisReference = null,
      fill: Fill = null,
      error_x: Error = null,
      error_y: Error = null,
      showlegend: JBoolean = null,
      fillcolor: OneOrSeq[Color] = null,
      hoverinfo: HoverInfo = null,
      hoveron: HoverOn = null,
      stackgroup: String = null,
      groupnorm: GroupNorm = null
  ): Scatter = {

    val (xOpt, yOpt) = Option(secondValues) match {
      case Some(y) => (Option(values), Some(y))
      case None    => (None, Option(values))
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
      Option(error_y),
      Option(showlegend).map(b => b: Boolean),
      Option(fillcolor),
      Option(hoverinfo),
      Option(hoveron),
      Option(stackgroup),
      Option(groupnorm)
    )
  }
}

private[plotly] trait BoxDeprecatedApply {
  @deprecated("Use Box() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      y: Sequence = null,
      x: Sequence = null,
      boxpoints: BoxPoints = null,
      jitter: JDouble = null,
      pointpos: JDouble = null,
      name: String = null,
      marker: Marker = null,
      orientation: Orientation = null,
      whiskerwidth: JDouble = null,
      boxmean: BoxMean = null,
      fillcolor: OneOrSeq[Color] = null,
      line: Line = null,
      showlegend: JBoolean = null
  ): Box =
    Box(
      Option(y),
      Option(x),
      Option(boxpoints),
      Option(jitter).map(d => d: Double),
      Option(pointpos).map(d => d: Double),
      Option(name),
      Option(marker),
      Option(orientation),
      Option(whiskerwidth).map(d => d: Double),
      Option(boxmean),
      Option(fillcolor),
      Option(line),
      Option(showlegend).map(b => b: Boolean)
    )
}

private[plotly] trait BarDeprecatedApply {
  @deprecated("Use Bar() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      x: Sequence,
      y: Sequence,
      name: String = null,
      text: Seq[String] = null,
      marker: Marker = null,
      orientation: Orientation = null,
      xaxis: AxisReference = null,
      yaxis: AxisReference = null,
      error_y: Error = null,
      showlegend: JBoolean = null,
      hoverinfo: HoverInfo = null,
      textposition: BarTextPosition = null,
      opacity: JDouble = null,
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

private[plotly] trait HistogramDeprecatedApply {
  @deprecated("Use Histogram() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      x: Sequence = null,
      y: Sequence = null,
      opacity: JDouble = null,
      name: String = null,
      autobinx: JBoolean = null,
      marker: Marker = null,
      xbins: Bins = null,
      histnorm: HistNorm = null,
      showlegend: JBoolean = null,
      cumulative: Cumulative = null,
      histfunc: HistFunc = null
  ): Histogram =
    Histogram(
      Option(x),
      Option(y),
      Option(opacity).map(d => d: Double),
      Option(name),
      Option(autobinx).map(b => b: Boolean),
      Option(marker),
      Option(xbins),
      Option(histnorm),
      Option(showlegend).map(b => b: Boolean),
      Option(cumulative),
      Option(histfunc)
    )
}

private[plotly] trait SurfaceDeprecatedApply {
  @deprecated("Use Surface() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      x: Sequence = null,
      y: Sequence = null,
      z: Sequence = null,
      showscale: JBoolean = null,
      opacity: JDouble = null
  ): Surface =
    Surface(
      Option(x),
      Option(y),
      Option(z),
      Option(showscale).map(b => b: Boolean),
      Option(opacity).map(d => d: Double)
    )
}

private[plotly] trait HeatmapDeprecatedApply {
  @deprecated("Use Heatmap() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      y: Sequence = null,
      x: Sequence = null,
      z: Sequence = null,
      autocolorscale: JBoolean = null,
      colorscale: ColorScale = null,
      showscale: JBoolean = null,
      name: String = null
  ): Heatmap =
    Heatmap(
      Option(y),
      Option(x),
      Option(z),
      Option(autocolorscale).map(b => b: Boolean),
      Option(colorscale),
      Option(showscale).map(b => b: Boolean),
      Option(name)
    )
}

private[plotly] trait CandlestickDeprecatedApply {
  @deprecated("Use Candlestick() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      x: Sequence = null,
      close: Sequence = null,
      high: Sequence = null,
      low: Sequence = null,
      open: Sequence = null,
      decreasing: Marker = null,
      increasing: Marker = null,
      line: Marker = null,
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
