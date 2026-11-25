package plotly

import com.github.plokhotnyuk.jsoniter_scala.core.{writeToString, JsonValueCodec}
import plotly.Codecs._
import plotly.layout._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import scala.scalajs.js.JSON

object Plotly {

  // None fields aren't written by the codecs, so the resulting JS objects don't have null fields
  private def stripNulls[J: JsonValueCodec](value: J): js.Any = JSON.parse(writeToString(value))

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
  }

  implicit class TraceSeqOps(val traces: Seq[Trace]) extends AnyVal {
    def plot(div: String, layout: Layout): Unit =
      Plotly.plot(div, traces, layout)
  }

}
