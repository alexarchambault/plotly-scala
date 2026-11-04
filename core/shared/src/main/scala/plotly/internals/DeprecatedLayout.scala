package plotly.internals

import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}

import plotly.element._
import plotly.layout._

// Creates a Layout out of the nullable parameters of the deprecated Layout.apply and plot methods
private[plotly] object DeprecatedLayout {
  def apply(
      title: String,
      legend: Legend,
      width: JInt,
      height: JInt,
      showlegend: JBoolean,
      xaxis: Axis,
      yaxis: Axis,
      xaxis1: Axis,
      xaxis2: Axis,
      xaxis3: Axis,
      xaxis4: Axis,
      yaxis1: Axis,
      yaxis2: Axis,
      yaxis3: Axis,
      yaxis4: Axis,
      barmode: BarMode,
      autosize: JBoolean,
      margin: Margin,
      annotations: Seq[Annotation],
      plot_bgcolor: Color,
      paper_bgcolor: Color,
      font: Font,
      bargap: JDouble,
      bargroupgap: JDouble,
      hovermode: HoverMode,
      boxmode: BoxMode,
      scene: Scene
  ): Layout =
    new Layout(
      Option(title),
      Option(legend),
      Option(width).map(x => x: Int),
      Option(height).map(x => x: Int),
      Option(showlegend).map(x => x: Boolean),
      Option(xaxis),
      Option(yaxis),
      Option(xaxis1),
      Option(xaxis2),
      Option(xaxis3),
      Option(xaxis4),
      Option(yaxis1),
      Option(yaxis2),
      Option(yaxis3),
      Option(yaxis4),
      Option(barmode),
      Option(autosize).map(x => x: Boolean),
      Option(margin),
      Option(annotations),
      Option(plot_bgcolor),
      Option(paper_bgcolor),
      Option(font),
      Option(bargap).map(x => x: Double),
      Option(bargroupgap).map(x => x: Double),
      Option(hovermode),
      Option(boxmode),
      Option(scene)
    )
}
