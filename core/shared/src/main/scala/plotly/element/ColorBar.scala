package plotly
package element

import dataclass.data
import plotly.layout.{Font, Title}

/** The color bar of a trace having a color scale (`colorbar` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class ColorBar(
    title: Option[Title] = None,
    orientation: Option[Orientation] = None,
    thickness: Option[Double] = None,
    thicknessmode: Option[SizeUnit] = None,
    len: Option[Double] = None,
    lenmode: Option[SizeUnit] = None,
    x: Option[Double] = None,
    y: Option[Double] = None,
    xanchor: Option[Anchor] = None,
    yanchor: Option[Anchor] = None,
    xpad: Option[Double] = None,
    ypad: Option[Double] = None,
    bgcolor: Option[Color] = None,
    bordercolor: Option[Color] = None,
    borderwidth: Option[Double] = None,
    outlinecolor: Option[Color] = None,
    outlinewidth: Option[Double] = None,
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
    showticklabels: Option[Boolean] = None
)
