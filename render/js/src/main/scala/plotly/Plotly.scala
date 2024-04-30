package plotly

import argonaut.Argonaut._
import argonaut.{EncodeJson, PrettyParams}
import plotly.Codecs._
import plotly.element.Color
import plotly.internals.BetterPrinter
import plotly.layout._

import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import scala.scalajs.js.JSON

object Plotly {

  private val printer = BetterPrinter(PrettyParams.nospace.copy(dropNullKeys = true))

  // Remove empty objects
  private def stripNulls[J: EncodeJson](value: J): js.Any = JSON.parse(printer.render(value.asJson))

  trait PlotlyDyn {
    def plotFn: js.Dynamic

    def apply(div: String, data: Seq[Trace], layout: Layout, config: Config): Unit =
      plotFn(
        div,
        stripNulls(data),
        stripNulls(layout),
        stripNulls(config)
      )

    def apply(div: String, data: Seq[Trace], layout: Layout): Unit =
      plotFn(
        div,
        stripNulls(data),
        stripNulls(layout)
      )

    def apply(div: String, data: Seq[Trace]): Unit =
      plotFn(div, stripNulls(data))

    def apply(div: String, data: Trace, layout: Layout): Unit =
      plotFn(div, stripNulls(data), stripNulls(layout))

    def apply(div: String, data: Trace): Unit =
      plotFn(div, stripNulls(data))
  }

  object newPlot extends PlotlyDyn {
    val plotFn: js.Dynamic = g.Plotly.newPlot
  }

  object plot extends PlotlyDyn {
    val plotFn: js.Dynamic = g.Plotly.newPlot
  }

  object react extends PlotlyDyn {
    val plotFn: js.Dynamic = g.Plotly.react
  }

  def relayout(div: String, layout: Layout): Unit =
    g.Plotly.relayout(div, stripNulls(layout))

  def purge(div: String): Unit =
    g.Plotly.purge(div)

  def validate(data: Seq[Trace], layout: Layout, config: Config): Unit =
    g.Plotly.validate(stripNulls(data), stripNulls(layout))

  implicit class TraceOps(val trace: Trace) extends AnyVal {
    def plot(div: String, layout: Layout): Unit =
      Plotly.plot(div, trace, layout)

    @deprecated("Create a Layout and call plot(div, layout) instead", "0.8.0")
    def plot(
        div: String,
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
        boxmode: BoxMode = null
    ): Unit =
      plot(
        div,
        Layout(
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
          boxmode
        )
      )
  }

  implicit class TraceSeqOps(val traces: Seq[Trace]) extends AnyVal {
    def plot(div: String, layout: Layout): Unit =
      Plotly.plot(div, traces, layout)

    @deprecated("Create a Layout and call plot(div, layout) instead", "0.8.0")
    def plot(
        div: String,
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
        boxmode: BoxMode = null
    ): Unit =
      plot(
        div,
        Layout(
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
          boxmode
        )
      )
  }

}
