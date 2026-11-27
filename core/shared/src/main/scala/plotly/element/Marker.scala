package plotly
package element

import dataclass.{data, since}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Marker(
    size: Option[OneOrSeq[Int]] = None,
    color: Option[OneOrSeq[Color]] = None,
    opacity: Option[OneOrSeq[Double]] = None,
    line: Option[Line] = None,
    symbol: Option[OneOrSeq[Symbol]] = None,
    outliercolor: Option[Color] = None,
    sizeref: Option[Double] = None,
    sizemode: Option[SizeMode] = None,
    width: Option[OneOrSeq[Int]] = None,
    // the colors of the sectors of pie-like and hierarchical traces
    @since("0.8.6")
    colors: Option[Seq[Color]] = None,
    // the color scale the numeric colors are mapped with, and its bounds
    colorscale: Option[ColorScale] = None,
    autocolorscale: Option[Boolean] = None,
    reversescale: Option[Boolean] = None,
    cmin: Option[Double] = None,
    cmax: Option[Double] = None,
    cmid: Option[Double] = None,
    showscale: Option[Boolean] = None,
    colorbar: Option[ColorBar] = None,
    angle: Option[OneOrSeq[Double]] = None,
    angleref: Option[MarkerAngleRef] = None,
    standoff: Option[OneOrSeq[Double]] = None,
    gradient: Option[MarkerGradient] = None,
    maxdisplayed: Option[Int] = None,
    sizemin: Option[Double] = None,
    cauto: Option[Boolean] = None,
    coloraxis: Option[String] = None,
    pattern: Option[FillPattern] = None,
    cornerradius: Option[Element] = None
)
