package plotly
package layout

import dataclass.{data, since}
import plotly.element._

@data(optionSetters = true) final case class Layout(
    title: Option[String] = None,
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
    barcornerradius: Option[Double] = None
) extends LayoutSetters

object Layout extends LayoutDeprecatedApply
