package plotly

import scala.language.implicitConversions

import dataclass._
import plotly.element._
import plotly.layout.{Font, Title}

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
    scalegroup: Option[String] = None,
    title: Option[Title] = None
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

/** A 2D histogram, counting (or aggregating) the points in each cell of a grid, colored by the count */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Histogram2d(
    x: Option[Sequence] = None,
    y: Option[Sequence] = None,
    z: Option[Sequence] = None,
    histfunc: Option[HistFunc] = None,
    histnorm: Option[HistNorm] = None,
    nbinsx: Option[Int] = None,
    nbinsy: Option[Int] = None,
    xbins: Option[Bins] = None,
    ybins: Option[Bins] = None,
    autobinx: Option[Boolean] = None,
    autobiny: Option[Boolean] = None,
    name: Option[String] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    hoverlabel: Option[HoverLabel] = None,
    zsmooth: Option[ZSmooth] = None,
    xgap: Option[Double] = None,
    ygap: Option[Double] = None,
    colorscale: Option[ColorScale] = None,
    autocolorscale: Option[Boolean] = None,
    reversescale: Option[Boolean] = None,
    showscale: Option[Boolean] = None,
    colorbar: Option[ColorBar] = None,
    zmin: Option[Double] = None,
    zmax: Option[Double] = None,
    zmid: Option[Double] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None
) extends Trace

object Histogram2d {
  def apply(x: Sequence, y: Sequence): Histogram2d =
    Histogram2d().copy(x = Some(x), y = Some(y))
}

/** A 2D histogram, drawn as contours */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Histogram2dContour(
    x: Option[Sequence] = None,
    y: Option[Sequence] = None,
    z: Option[Sequence] = None,
    histfunc: Option[HistFunc] = None,
    histnorm: Option[HistNorm] = None,
    nbinsx: Option[Int] = None,
    nbinsy: Option[Int] = None,
    xbins: Option[Bins] = None,
    ybins: Option[Bins] = None,
    autobinx: Option[Boolean] = None,
    autobiny: Option[Boolean] = None,
    name: Option[String] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    hoverlabel: Option[HoverLabel] = None,
    autocontour: Option[Boolean] = None,
    ncontours: Option[Int] = None,
    contours: Option[Contours] = None,
    line: Option[Line] = None,
    colorscale: Option[ColorScale] = None,
    autocolorscale: Option[Boolean] = None,
    reversescale: Option[Boolean] = None,
    showscale: Option[Boolean] = None,
    colorbar: Option[ColorBar] = None,
    zmin: Option[Double] = None,
    zmax: Option[Double] = None,
    zmid: Option[Double] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None
) extends Trace

object Histogram2dContour {
  def apply(x: Sequence, y: Sequence): Histogram2dContour =
    Histogram2dContour().copy(x = Some(x), y = Some(y))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Violin(
    y: Option[Sequence] = None,
    x: Option[Sequence] = None,
    x0: Option[Element] = None,
    y0: Option[Element] = None,
    name: Option[String] = None,
    orientation: Option[Orientation] = None,
    side: Option[ViolinSide] = None,
    bandwidth: Option[Double] = None,
    span: Option[Seq[Double]] = None,
    spanmode: Option[SpanMode] = None,
    scalegroup: Option[String] = None,
    scalemode: Option[ViolinScaleMode] = None,
    width: Option[Double] = None,
    points: Option[BoxPoints] = None,
    jitter: Option[Double] = None,
    pointpos: Option[Double] = None,
    box: Option[ViolinBox] = None,
    meanline: Option[MeanLine] = None,
    marker: Option[Marker] = None,
    line: Option[Line] = None,
    fillcolor: Option[Color] = None,
    opacity: Option[Double] = None,
    text: Option[OneOrSeq[String]] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    offsetgroup: Option[String] = None,
    alignmentgroup: Option[String] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None
) extends Trace

object Violin {
  def apply(y: Sequence): Violin =
    Violin().copy(y = Some(y))

  def apply(y: Sequence, x: Sequence): Violin =
    Violin().copy(y = Some(y), x = Some(x))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Waterfall(
    x: Option[Sequence] = None,
    y: Option[Sequence] = None,
    measure: Option[Seq[WaterfallMeasure]] = None,
    base: Option[Double] = None,
    name: Option[String] = None,
    orientation: Option[Orientation] = None,
    text: Option[OneOrSeq[String]] = None,
    textposition: Option[BarTextPosition] = None,
    textinfo: Option[TextInfo] = None,
    texttemplate: Option[OneOrSeq[String]] = None,
    textfont: Option[Font] = None,
    insidetextfont: Option[Font] = None,
    outsidetextfont: Option[Font] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    connector: Option[Connector] = None,
    increasing: Option[BarStyle] = None,
    decreasing: Option[BarStyle] = None,
    totals: Option[BarStyle] = None,
    width: Option[OneOrSeq[Double]] = None,
    offset: Option[OneOrSeq[Double]] = None,
    offsetgroup: Option[String] = None,
    alignmentgroup: Option[String] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None
) extends Trace

object Waterfall {
  def apply(x: Sequence, y: Sequence): Waterfall =
    Waterfall().copy(x = Some(x), y = Some(y))

  def apply(x: Sequence, y: Sequence, measure: Seq[WaterfallMeasure]): Waterfall =
    Waterfall().copy(x = Some(x), y = Some(y), measure = Some(measure))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Funnel(
    x: Option[Sequence] = None,
    y: Option[Sequence] = None,
    name: Option[String] = None,
    orientation: Option[Orientation] = None,
    text: Option[OneOrSeq[String]] = None,
    textposition: Option[BarTextPosition] = None,
    textinfo: Option[TextInfo] = None,
    texttemplate: Option[OneOrSeq[String]] = None,
    textfont: Option[Font] = None,
    insidetextfont: Option[Font] = None,
    outsidetextfont: Option[Font] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    marker: Option[Marker] = None,
    connector: Option[Connector] = None,
    width: Option[Double] = None,
    offset: Option[Double] = None,
    offsetgroup: Option[String] = None,
    alignmentgroup: Option[String] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None
) extends Trace

object Funnel {
  def apply(x: Sequence, y: Sequence): Funnel =
    Funnel().copy(x = Some(x), y = Some(y))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class FunnelArea(
    values: Option[Sequence] = None,
    labels: Option[Sequence] = None,
    name: Option[String] = None,
    title: Option[Title] = None,
    text: Option[OneOrSeq[String]] = None,
    textposition: Option[BarTextPosition] = None,
    textinfo: Option[TextInfo] = None,
    texttemplate: Option[OneOrSeq[String]] = None,
    textfont: Option[Font] = None,
    insidetextfont: Option[Font] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    marker: Option[Marker] = None,
    domain: Option[Domain] = None,
    aspectratio: Option[Double] = None,
    baseratio: Option[Double] = None,
    scalegroup: Option[String] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None
) extends Trace

object FunnelArea {
  def apply(values: Sequence, labels: Sequence): FunnelArea =
    FunnelArea().copy(values = Some(values), labels = Some(labels))
}

/** Open-high-low-close charts: one vertical line per period, from its low to its high, with ticks for
  * its open (on the left) and its close (on the right)
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Ohlc(
    x: Option[Sequence] = None,
    open: Option[Sequence] = None,
    high: Option[Sequence] = None,
    low: Option[Sequence] = None,
    close: Option[Sequence] = None,
    name: Option[String] = None,
    text: Option[OneOrSeq[String]] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    line: Option[Line] = None,
    increasing: Option[DirectionStyle] = None,
    decreasing: Option[DirectionStyle] = None,
    // the width of the open and close ticks, relative to the minimal interval between x values
    tickwidth: Option[Double] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    xaxis: Option[AxisReference] = None,
    yaxis: Option[AxisReference] = None
) extends Trace

object Ohlc {
  def apply(x: Sequence, open: Sequence, high: Sequence, low: Sequence, close: Sequence): Ohlc =
    Ohlc().copy(x = Some(x), open = Some(open), high = Some(high), low = Some(low), close = Some(close))
}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Scatter3d(
    x: Option[Sequence] = None,
    y: Option[Sequence] = None,
    z: Option[Sequence] = None,
    mode: Option[ScatterMode] = None,
    name: Option[String] = None,
    text: Option[OneOrSeq[String]] = None,
    textposition: Option[TextPosition] = None,
    textfont: Option[Font] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    marker: Option[Marker] = None,
    line: Option[Line] = None,
    connectgaps: Option[Boolean] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    // the 3D scene the trace is drawn in, like "scene" (the default) or "scene2"
    scene: Option[String] = None
) extends Trace

object Scatter3d {
  def apply(x: Sequence, y: Sequence, z: Sequence): Scatter3d =
    Scatter3d().copy(x = Some(x), y = Some(y), z = Some(z))
}

/** Points and lines in polar coordinates */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class ScatterPolar(
    r: Option[Sequence] = None,
    theta: Option[Sequence] = None,
    r0: Option[Element] = None,
    dr: Option[Double] = None,
    theta0: Option[Element] = None,
    dtheta: Option[Double] = None,
    thetaunit: Option[ThetaUnit] = None,
    mode: Option[ScatterMode] = None,
    name: Option[String] = None,
    text: Option[OneOrSeq[String]] = None,
    textposition: Option[TextPosition] = None,
    textfont: Option[Font] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    marker: Option[Marker] = None,
    line: Option[Line] = None,
    fill: Option[Fill] = None,
    fillcolor: Option[Color] = None,
    connectgaps: Option[Boolean] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    // the polar subplot the trace is drawn in, like "polar" (the default) or "polar2"
    subplot: Option[String] = None
) extends Trace

object ScatterPolar {
  def apply(r: Sequence, theta: Sequence): ScatterPolar =
    ScatterPolar().copy(r = Some(r), theta = Some(theta))
}

/** Points and lines in polar coordinates, drawn with WebGL */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class ScatterPolarGl(
    r: Option[Sequence] = None,
    theta: Option[Sequence] = None,
    r0: Option[Element] = None,
    dr: Option[Double] = None,
    theta0: Option[Element] = None,
    dtheta: Option[Double] = None,
    thetaunit: Option[ThetaUnit] = None,
    mode: Option[ScatterMode] = None,
    name: Option[String] = None,
    text: Option[OneOrSeq[String]] = None,
    textposition: Option[TextPosition] = None,
    textfont: Option[Font] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    marker: Option[Marker] = None,
    line: Option[Line] = None,
    fill: Option[Fill] = None,
    fillcolor: Option[Color] = None,
    connectgaps: Option[Boolean] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    // the polar subplot the trace is drawn in, like "polar" (the default) or "polar2"
    subplot: Option[String] = None
) extends Trace

object ScatterPolarGl {
  def apply(r: Sequence, theta: Sequence): ScatterPolarGl =
    ScatterPolarGl().copy(r = Some(r), theta = Some(theta))
}

/** Bars in polar coordinates, like wind roses */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class BarPolar(
    r: Option[Sequence] = None,
    theta: Option[Sequence] = None,
    r0: Option[Element] = None,
    dr: Option[Double] = None,
    theta0: Option[Element] = None,
    dtheta: Option[Double] = None,
    thetaunit: Option[ThetaUnit] = None,
    base: Option[OneOrSeq[Double]] = None,
    // the angular widths of the bars
    width: Option[OneOrSeq[Double]] = None,
    offset: Option[OneOrSeq[Double]] = None,
    name: Option[String] = None,
    text: Option[OneOrSeq[String]] = None,
    hovertext: Option[OneOrSeq[String]] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None,
    marker: Option[Marker] = None,
    opacity: Option[Double] = None,
    showlegend: Option[Boolean] = None,
    legendgroup: Option[String] = None,
    // the polar subplot the trace is drawn in, like "polar" (the default) or "polar2"
    subplot: Option[String] = None
) extends Trace

object BarPolar {
  def apply(r: Sequence, theta: Sequence): BarPolar =
    BarPolar().copy(r = Some(r), theta = Some(theta))
}

object Pie {
  def apply(values: Sequence, labels: Sequence): Pie =
    Pie().copy(values = Some(values), labels = Some(labels))
}
