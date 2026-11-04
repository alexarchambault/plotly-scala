package plotly
package layout

import dataclass.{data, since}
import plotly.element._

@data(optionSetters = true) final case class Axis(
    title: Option[String] = None,
    titlefont: Option[Font] = None,
    showgrid: Option[Boolean] = None,
    gridwidth: Option[Int] = None,
    gridcolor: Option[Color] = None,
    showline: Option[Boolean] = None,
    showticklabels: Option[Boolean] = None,
    linecolor: Option[Color] = None,
    linewidth: Option[Int] = None,
    autotick: Option[Boolean] = None,
    tickcolor: Option[Color] = None,
    tickwidth: Option[Int] = None,
    tickangle: Option[Double] = None,
    dtick: Option[Double] = None,
    ticklen: Option[Int] = None,
    tickfont: Option[Font] = None,
    tickprefix: Option[String] = None,
    ticksuffix: Option[String] = None,
    zeroline: Option[Boolean] = None,
    zerolinewidth: Option[Double] = None,
    zerolinecolor: Option[Color] = None,
    range: Option[Range] = None,
    autorange: Option[Boolean] = None,
    ticks: Option[Ticks] = None,
    domain: Option[Range] = None,
    side: Option[Side] = None,
    anchor: Option[AxisAnchor] = None,
    `type`: Option[AxisType] = None,
    overlaying: Option[AxisAnchor] = None,
    position: Option[Double] = None,
    tickmode: Option[TickMode] = None,
    tickvals: Option[Sequence] = None,
    ticktext: Option[Sequence] = None,
    nticks: Option[Int] = None,
    automargin: Option[Boolean] = None,
    @since("0.8.0")
    rangeslider: Option[RangeSlider] = None,
    @since("0.8.2")
    width: Option[Int] = None,
    height: Option[Int] = None,
    autosize: Option[Boolean] = None,
    @since("0.8.5")
    tickformat: Option[String] = None,
    fixedrange: Option[Boolean] = None
) extends AxisSetters

object Axis extends AxisDeprecatedApply
