package plotly

import io.circe.{Json, Printer}
import io.circe.syntax._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import scala.scalajs.js.JSON
import plotly.Codecs._
import plotly.element.Color
import plotly.layout._
import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}

object Plotly {

  private val printer = Printer.noSpaces.copy(dropNullKeys = true)
  private def stripNulls(json: Json): js.Any = {
    // Remove empty objects
    JSON.parse(printer.pretty(json))
  }

  def plot(div: String, data: Seq[Trace], layout: Layout): Unit = {
    g.Plotly.plot(
      div,
      stripNulls(data.asJson),
      stripNulls(layout.asJson)
    )
  }

  def plot(div: String, data: Seq[Trace]): Unit = {
    g.Plotly.plot(
      div,
      stripNulls(data.asJson)
    )
  }

  def plot(div: String, data: Trace, layout: Layout): Unit =
    g.Plotly.plot(
      div,
      stripNulls(data.asJson),
      stripNulls(layout.asJson)
    )

  def plot(div: String, data: Trace): Unit =
    g.Plotly.plot(
      div,
      stripNulls(data.asJson)
    )

  implicit class TraceOps(val trace: Trace) extends AnyVal {
    def plot(div: String, layout: Layout): Unit =
      Plotly.plot(div, trace, layout)

    def plot(
                div: String,
              title: String          = null,
             legend: Legend          = null,
              width: JInt            = null,
             height: JInt            = null,
         showlegend: JBoolean        = null,
              xaxis: Axis            = null,
              yaxis: Axis            = null,
             xaxis1: Axis            = null,
             xaxis2: Axis            = null,
             xaxis3: Axis            = null,
             xaxis4: Axis            = null,
             yaxis1: Axis            = null,
             yaxis2: Axis            = null,
             yaxis3: Axis            = null,
             yaxis4: Axis            = null,
            barmode: BarMode         = null,
           autosize: JBoolean        = null,
             margin: Margin          = null,
        annotations: Seq[Annotation] = null,
       plot_bgcolor: Color           = null,
      paper_bgcolor: Color           = null,
               font: Font            = null,
             bargap: JDouble         = null,
        bargroupgap: JDouble         = null,
          hovermode: HoverMode       = null,
            boxmode: BoxMode         = null
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

    def plot(
                div: String,
              title: String          = null,
             legend: Legend          = null,
              width: JInt            = null,
             height: JInt            = null,
         showlegend: JBoolean        = null,
              xaxis: Axis            = null,
              yaxis: Axis            = null,
             xaxis1: Axis            = null,
             xaxis2: Axis            = null,
             xaxis3: Axis            = null,
             xaxis4: Axis            = null,
             yaxis1: Axis            = null,
             yaxis2: Axis            = null,
             yaxis3: Axis            = null,
             yaxis4: Axis            = null,
            barmode: BarMode         = null,
           autosize: JBoolean        = null,
             margin: Margin          = null,
        annotations: Seq[Annotation] = null,
       plot_bgcolor: Color           = null,
      paper_bgcolor: Color           = null,
               font: Font            = null,
             bargap: JDouble         = null,
        bargroupgap: JDouble         = null,
          hovermode: HoverMode       = null,
            boxmode: BoxMode         = null
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
