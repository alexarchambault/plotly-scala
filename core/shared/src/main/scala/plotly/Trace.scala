package plotly

import scala.language.implicitConversions

import dataclass._
import plotly.element._

sealed abstract class Trace extends Product with Serializable

@data(optionSetters = true) final case class Scatter(
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
    groupnorm: Option[GroupNorm] = None,
    @since("0.8.2")
    hovertemplate: Option[OneOrSeq[String]] = None
) extends Trace
    with ScatterSetters

object Scatter extends ScatterDeprecatedApply {
  def apply(x: Sequence, y: Sequence): Scatter =
    Scatter().withX(x).withY(y)

  def apply(y: Sequence): Scatter =
    Scatter().withY(y)
}

@data(optionSetters = true) final case class Box(
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
    showlegend: Option[Boolean] = None,
    @since("0.8.2")
    hovertemplate: Option[OneOrSeq[String]] = None
) extends Trace
    with BoxSetters

object Box extends BoxDeprecatedApply {
  def apply(y: Sequence): Box =
    Box().withY(y)

  def apply(y: Sequence, x: Sequence): Box =
    Box().withY(y).withX(x)
}

@data(optionSetters = true) final case class Image(
    z: Seq[Seq[Seq[Double]]],
    x0: Option[Element] = None,
    y0: Option[Element] = None,
    name: Option[String] = None,
    text: Option[Seq[String]] = None,
    opacity: Option[Double] = None,
    ids: Option[Seq[String]] = None,
    dx: Option[Double] = None,
    dy: Option[Double] = None,
    source: Option[String] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[Seq[String]] = None,
    meta: Option[String] = None,
    customdata: Option[Seq[String]] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None,
    colormodel: Option[ColorModel] = None,
    zmax: Option[Seq[Double]] = None,
    zmin: Option[Seq[Double]] = None,
    hoverlabel: Option[HoverLabel] = None
) extends Trace
    with ImageSetters

@data(optionSetters = true) final case class Bar(
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
    base: Option[OneOrSeq[Double]] = None,
    @since("0.8.2")
    hovertemplate: Option[OneOrSeq[String]] = None
) extends Trace
    with BarSetters

object Bar extends BarDeprecatedApply

@data(optionSetters = true) final case class Histogram(
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
    histfunc: Option[HistFunc] = None,
    @since("0.8.2")
    hovertemplate: Option[OneOrSeq[String]] = None,
    @since("0.8.5")
    yaxis: Option[String] = None,
    hovertext: Option[OneOrSeq[String]] = None
) extends Trace
    with HistogramSetters

object Histogram extends HistogramDeprecatedApply {
  def apply(x: Sequence): Histogram =
    Histogram().withX(x)

  def apply(x: Sequence, y: Sequence): Histogram =
    Histogram().withX(x).withY(y)
}

@data(optionSetters = true) final case class Surface(
    x: Option[Sequence] = None,
    y: Option[Sequence] = None,
    z: Option[Sequence] = None,
    showscale: Option[Boolean] = None,
    opacity: Option[Double] = None,
    @since("0.8.2")
    hovertemplate: Option[OneOrSeq[String]] = None
) extends Trace
    with SurfaceSetters

object Surface extends SurfaceDeprecatedApply

@data(optionSetters = true) final case class Heatmap(
    y: Option[Sequence] = None,
    x: Option[Sequence] = None,
    z: Option[Sequence] = None,
    autocolorscale: Option[Boolean] = None,
    colorscale: Option[ColorScale] = None,
    showscale: Option[Boolean] = None,
    name: Option[String] = None,
    @since("0.8.2")
    hovertemplate: Option[OneOrSeq[String]] = None,
    hoverongaps: Option[Boolean] = None
) extends Trace
    with HeatmapSetters

object Heatmap extends HeatmapDeprecatedApply {
  def apply(z: Sequence): Heatmap =
    Heatmap().withZ(z)

  def apply(z: Sequence, x: Sequence, y: Sequence): Heatmap =
    Heatmap().withZ(z).withX(x).withY(y)
}

@data(optionSetters = true) final case class Candlestick(
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
    with CandlestickSetters

object Candlestick extends CandlestickDeprecatedApply
