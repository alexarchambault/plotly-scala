package plotly
package layout

import dataclass.data
import plotly.element._

/** The radial axis of a polar subplot (`radialaxis` of `polar` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class RadialAxis(
    visible: Option[Boolean] = None,
    `type`: Option[AxisType] = None,
    categoryorder: Option[CategoryOrder] = None,
    categoryarray: Option[Sequence] = None,
    color: Option[Color] = None,
    showgrid: Option[Boolean] = None,
    gridcolor: Option[Color] = None,
    gridwidth: Option[Double] = None,
    showline: Option[Boolean] = None,
    linecolor: Option[Color] = None,
    linewidth: Option[Double] = None,
    showticklabels: Option[Boolean] = None,
    tickmode: Option[TickMode] = None,
    nticks: Option[Int] = None,
    tick0: Option[Double] = None,
    dtick: Option[Double] = None,
    tickvals: Option[Sequence] = None,
    ticktext: Option[Sequence] = None,
    ticks: Option[Ticks] = None,
    ticklen: Option[Double] = None,
    tickwidth: Option[Double] = None,
    tickcolor: Option[Color] = None,
    tickangle: Option[Double] = None,
    tickfont: Option[Font] = None,
    tickformat: Option[String] = None,
    tickprefix: Option[String] = None,
    ticksuffix: Option[String] = None,
    title: Option[Title] = None,
    range: Option[Range] = None,
    autorange: Option[Boolean] = None,
    // the angle the axis is drawn at, in degrees
    angle: Option[Double] = None,
    // the side of the axis its ticks and labels are on
    side: Option[RotationDirection] = None
)
