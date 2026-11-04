package plotly
package layout

import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}
import plotly.element._
import plotly.internals.DeprecatedLayout

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait LayoutDeprecatedApply {
  @deprecated("Use Layout() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      title: String = null,
      legend: Legend = null,
      width: JInt = null,
      height: JInt = null,
      showlegend: JBoolean = null,
      xaxis: Axis = null,
      yaxis: Axis = null,
      xaxis1: Axis = null,
      xaxis2: Axis = null,
      xaxis3: Axis = null,
      xaxis4: Axis = null,
      yaxis1: Axis = null,
      yaxis2: Axis = null,
      yaxis3: Axis = null,
      yaxis4: Axis = null,
      barmode: BarMode = null,
      autosize: JBoolean = null,
      margin: Margin = null,
      annotations: Seq[Annotation] = null,
      plot_bgcolor: Color = null,
      paper_bgcolor: Color = null,
      font: Font = null,
      bargap: JDouble = null,
      bargroupgap: JDouble = null,
      hovermode: HoverMode = null,
      boxmode: BoxMode = null,
      scene: Scene = null
  ): Layout =
    DeprecatedLayout(
      title,
      legend,
      width,
      height,
      showlegend,
      xaxis,
      yaxis,
      xaxis1,
      xaxis2,
      xaxis3,
      xaxis4,
      yaxis1,
      yaxis2,
      yaxis3,
      yaxis4,
      barmode,
      autosize,
      margin,
      annotations,
      plot_bgcolor,
      paper_bgcolor,
      font,
      bargap,
      bargroupgap,
      hovermode,
      boxmode,
      scene
    )
}
