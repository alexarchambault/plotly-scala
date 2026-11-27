package plotly
package layout

import dataclass.{data, since}
import plotly.element._

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Layout(
    title: Option[Title] = None,
    legend: Option[Legend] = None,
    width: Option[Int] = None,
    height: Option[Int] = None,
    showlegend: Option[Boolean] = None,
    xaxis: Option[Axis] = None,
    yaxis: Option[Axis] = None,
    xaxis1: Option[Axis] = None,
    xaxis2: Option[Axis] = None,
    xaxis3: Option[Axis] = None,
    xaxis4: Option[Axis] = None,
    yaxis1: Option[Axis] = None,
    yaxis2: Option[Axis] = None,
    yaxis3: Option[Axis] = None,
    yaxis4: Option[Axis] = None,
    barmode: Option[BarMode] = None,
    autosize: Option[Boolean] = None,
    margin: Option[Margin] = None,
    annotations: Option[Seq[Annotation]] = None,
    plot_bgcolor: Option[Color] = None,
    paper_bgcolor: Option[Color] = None,
    font: Option[Font] = None,
    bargap: Option[Double] = None,
    bargroupgap: Option[Double] = None,
    hovermode: Option[HoverMode] = None,
    boxmode: Option[BoxMode] = None,
    scene: Option[Scene] = None,
    @since("0.8.0")
    dragmode: Option[String] = None,
    shapes: Option[Seq[Shape]] = None,
    @since("0.8.2")
    grid: Option[Grid] = None,
    @since("0.8.6")
    scattermode: Option[ScatterLayoutMode] = None,
    scattergap: Option[Double] = None,
    // in pixels (plotly.js also accepts a percentage of the bar width, like "25%")
    barcornerradius: Option[Double] = None,
    // default colors of the sectors of pie traces
    piecolorway: Option[Seq[Color]] = None,
    hiddenlabels: Option[Seq[String]] = None,
    violinmode: Option[BoxMode] = None,
    violingap: Option[Double] = None,
    violingroupgap: Option[Double] = None,
    waterfallmode: Option[BoxMode] = None,
    waterfallgap: Option[Double] = None,
    waterfallgroupgap: Option[Double] = None,
    funnelmode: Option[BarMode] = None,
    funnelgap: Option[Double] = None,
    funnelgroupgap: Option[Double] = None,
    funnelareacolorway: Option[Seq[Color]] = None,
    polar: Option[Polar] = None,
    polar2: Option[Polar] = None,
    polar3: Option[Polar] = None,
    polar4: Option[Polar] = None,
    sunburstcolorway: Option[Seq[Color]] = None,
    treemapcolorway: Option[Seq[Color]] = None,
    iciclecolorway: Option[Seq[Color]] = None,
    extendsunburstcolors: Option[Boolean] = None,
    extendtreemapcolors: Option[Boolean] = None,
    extendiciclecolors: Option[Boolean] = None,
    extendpiecolors: Option[Boolean] = None,
    extendfunnelareacolors: Option[Boolean] = None,
    coloraxis: Option[ColorAxis] = None,
    coloraxis2: Option[ColorAxis] = None
)
