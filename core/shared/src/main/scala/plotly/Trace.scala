package plotly

import scala.language.implicitConversions

import dataclass._
import plotly.element._
import plotly.layout.Font

sealed abstract class Trace extends Product with Serializable

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Scatter(
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

object Scatter {
  def apply(x: Sequence, y: Sequence): Scatter =
    Scatter().copy(x = Some(x), y = Some(y))

  def apply(y: Sequence): Scatter =
    Scatter().copy(y = Some(y))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Box(
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

object Box {
  def apply(y: Sequence): Box =
    Box().copy(y = Some(y))

  def apply(y: Sequence, x: Sequence): Box =
    Box().copy(y = Some(y), x = Some(x))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Image(
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

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Bar(
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

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Histogram(
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

object Histogram {
  def apply(x: Sequence): Histogram =
    Histogram().copy(x = Some(x))

  def apply(x: Sequence, y: Sequence): Histogram =
    Histogram().copy(x = Some(x), y = Some(y))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Surface(
    x: Option[Sequence] = None,
    y: Option[Sequence] = None,
    z: Option[Sequence] = None,
    showscale: Option[Boolean] = None,
    opacity: Option[Double] = None,
    @since("0.8.2")
    hovertemplate: Option[OneOrSeq[String]] = None
) extends Trace

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Heatmap(
    y: Option[Sequence] = None,
    x: Option[Sequence] = None,
    z: Option[Sequence] = None,
    autocolorscale: Option[Boolean] = None,
    colorscale: Option[ColorScale] = None,
    showscale: Option[Boolean] = None,
    name: Option[String] = None,
    @since("0.8.2")
    hovertemplate: Option[OneOrSeq[String]] = None,
    hoverongaps: Option[Boolean] = None,
    @since("0.8.6")
    connectgaps: Option[Boolean] = None,
    zsmooth: Option[ZSmooth] = None,
    zmin: Option[Double] = None,
    zmax: Option[Double] = None,
    zmid: Option[Double] = None,
    reversescale: Option[Boolean] = None,
    colorbar: Option[ColorBar] = None,
    xgap: Option[Double] = None,
    ygap: Option[Double] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None
) extends Trace

object Heatmap {
  def apply(z: Sequence): Heatmap =
    Heatmap().copy(z = Some(z))

  def apply(z: Sequence, x: Sequence, y: Sequence): Heatmap =
    Heatmap().copy(z = Some(z), x = Some(x), y = Some(y))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Candlestick(
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

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Pie(
    values: Option[Sequence] = None,
    labels: Option[Sequence] = None,
    name: Option[String] = None,
    text: Option[OneOrSeq[String]] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    hole: Option[Double] = None,
    pull: Option[OneOrSeq[Double]] = None,
    rotation: Option[Double] = None,
    direction: Option[RotationDirection] = None,
    sort: Option[Boolean] = None,
    marker: Option[Marker] = None,
    textinfo: Option[TextInfo] = None,
    textposition: Option[BarTextPosition] = None,
    textfont: Option[Font] = None,
    insidetextfont: Option[Font] = None,
    outsidetextfont: Option[Font] = None,
    insidetextorientation: Option[InsideTextOrientation] = None,
    texttemplate: Option[OneOrSeq[String]] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    domain: Option[Domain] = None,
    automargin: Option[Boolean] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    opacity: Option[Double] = None,
    scalegroup: Option[String] = None
) extends Trace

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Contour(
    z: Option[Sequence] = None,
    x: Option[Sequence] = None,
    y: Option[Sequence] = None,
    x0: Option[Element] = None,
    dx: Option[Double] = None,
    y0: Option[Element] = None,
    dy: Option[Double] = None,
    transpose: Option[Boolean] = None,
    name: Option[String] = None,
    text: Option[Sequence] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    hoverongaps: Option[Boolean] = None,
    connectgaps: Option[Boolean] = None,
    autocontour: Option[Boolean] = None,
    ncontours: Option[Int] = None,
    contours: Option[Contours] = None,
    line: Option[Line] = None,
    fillcolor: Option[Color] = None,
    colorscale: Option[ColorScale] = None,
    autocolorscale: Option[Boolean] = None,
    reversescale: Option[Boolean] = None,
    showscale: Option[Boolean] = None,
    colorbar: Option[ColorBar] = None,
    zmin: Option[Double] = None,
    zmax: Option[Double] = None,
    zmid: Option[Double] = None,
    zauto: Option[Boolean] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None
) extends Trace

object Contour {
  def apply(z: Sequence): Contour =
    Contour().copy(z = Some(z))

  def apply(z: Sequence, x: Sequence, y: Sequence): Contour =
    Contour().copy(z = Some(z), x = Some(x), y = Some(y))
}

object Pie {
  def apply(values: Sequence, labels: Sequence): Pie =
    Pie().copy(values = Some(values), labels = Some(labels))
}
