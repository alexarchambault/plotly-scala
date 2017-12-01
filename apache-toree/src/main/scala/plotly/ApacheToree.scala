package plotly

import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}
import scala.util.Random
import plotly.element.{Color, _}
import plotly.layout.{Annotation, BoxMode, Font, HoverMode, _}
import org.apache.toree.kernel.api.KernelLike


object ApacheToree {

  def init(offline: Boolean = false)(implicit Kernel: KernelLike): Unit = {

    // offline mode like in plotly-python

    val requireInit =
      if (offline)
        s"""define('plotly', function(require, exports, module) {
            |  ${Plotly.plotlyMinJs}
            |});
        """
      else
        """require.config({
          |  paths: {
          |    d3: 'https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min',
          |    plotly: 'https://cdn.plot.ly/plotly-1.12.0.min'
          |  },
          |
          |  shim: {
          |    plotly: {
          |      deps: ['d3', 'jquery'],
          |      exports: 'plotly'
          |    }
          |  }
          |});
        """.stripMargin

    val html = s"""
      <script type="text/javascript">
        $requireInit

        require(['plotly'], function(Plotly) {
          window.Plotly = Plotly;
        });
      </script>
    """

    Kernel.display.html(html)
  }

  def plotJs(
              div: String,
              data: Seq[Trace],
              layout: Layout
            )(implicit
              Kernel: KernelLike
            ): Unit = {

    val baseJs = Plotly.jsSnippet(div, data, layout)

    val js =
      s"""requirejs(["plotly"], function(Plotly) {
          |  $baseJs
          |});
      """.stripMargin
    Kernel.display.javascript(js)
  }

  def randomDiv() = "plot-" + math.abs(Random.nextInt().toLong)

  def plot(
            data: Seq[Trace],
            layout: Layout = Layout(),
            div: String = ""
          )(implicit
            Kernel: KernelLike
          ): String = {

    val div0 =
      if (div.isEmpty)
        randomDiv()
      else
        div

    if (div.isEmpty)
      Kernel.display.html(s"""<div class="chart" id="$div0"></div>""")

    plotJs(div0, data, layout)

    div0
  }

  implicit class DataOps(val data: Trace) extends AnyVal {

    def plot(
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
              boxmode: BoxMode         = null,
              div: String          = ""
            )(implicit
              Kernel: KernelLike
            ): String =
      plot(
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
        ),
        div
      )

    def plot(
              layout: Layout,
              div: String
            )(implicit
              Kernel: KernelLike
            ): String =
      ApacheToree.plot(Seq(data), layout, div = div)
  }

  implicit class DataSeqOps(val data: Seq[Trace]) extends AnyVal {
    def plot(
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
              boxmode: BoxMode         = null,
              div: String          = ""
            )(implicit
              Kernel: KernelLike
            ): String =
      plot(
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
        ),
        div
      )

    def plot(
              layout: Layout,
              div: String
            )(implicit
              Kernel: KernelLike
            ): String =
      ApacheToree.plot(data, layout, div = div)
  }

}





