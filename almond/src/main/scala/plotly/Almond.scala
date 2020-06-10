package plotly

import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}

import almond.interpreter.api.{DisplayData, OutputHandler}

import scala.util.Random
import plotly.element._
import plotly.layout._

object Almond {

  object Internal {
    @volatile var initialized = false
  }

  def init(offline: Boolean = false)(implicit publish: OutputHandler): Unit = {

    // offline mode like in plotly-python

    val requireInit =
      if (offline)
       s"""define('plotly', function(require, exports, module) {
          |  ${Plotly.plotlyMinJs}
          |});
        """.stripMargin
      else
        s"""require.config({
          |  paths: {
          |    d3: 'https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min',
          |    plotly: 'https://cdn.plot.ly/plotly-${Plotly.plotlyVersion}.min',
          |    jquery: 'https://code.jquery.com/jquery-3.3.1.min'
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

    Internal.initialized = true

    publish.html(html)
  }

  def plotJs(
    data: Seq[Trace],
    layout: Layout,
    config: Config,
    div: String = ""
  )(implicit
    publish: OutputHandler
  ): String = {

    val (div0, divPart) =
      if (div.isEmpty) {
        val d = randomDiv()
        (d, s"""<div class="chart" id="$d"></div>""")
      } else
        (div, "")

    val baseJs = Plotly.jsSnippet(div0, data, layout, config)
    val json = Plotly.jsonSnippet(data, layout, config)

    val js =
     s"""require(['plotly'], function(Plotly) {
        |  $baseJs
        |});
      """.stripMargin

    val data0 = DisplayData(
      data = Map(
        "text/html" ->
          s"""$divPart
             |<script>$js</script>
           """.stripMargin,
        "application/vnd.plotly.v1+json" -> json
      )
    )

    publish.display(data0)

    div0
  }

  def randomDiv(): String =
    almond.display.UpdatableDisplay.generateDiv("plot-")

  def plot(
    data: Seq[Trace],
    layout: Layout = Layout(),
    config: Config = Config(),
    div: String = ""
  )(implicit
    publish: OutputHandler
  ): String = {

    if (!Internal.initialized)
      Internal.synchronized {
        if (!Internal.initialized) {
          init()
          Internal.initialized = true
        }
      }

    plotJs(data, layout, config)
  }

  implicit class DataOps(val data: Trace) extends AnyVal {

    @deprecated("Create a Layout and / or a Config, and call one of the other plot methods instead", "0.8.0")
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
                     editable: JBoolean        = null,
                   responsive: JBoolean        = null,
        showEditInChartStudio: JBoolean        = null,
              plotlyServerURL: String          = null,
                          div: String          = ""
    )(implicit
      publish: OutputHandler
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
        Config()
          .withEditable(Option(editable).map[Boolean](identity))
          .withResponsive(Option(responsive).map[Boolean](identity))
          .withShowEditInChartStudio(Option(showEditInChartStudio).map[Boolean](identity))
          .withPlotlyServerURL(Option(plotlyServerURL)),
        div
      )

    def plot(
      layout: Layout,
      config: Config,
      div: String
    )(implicit
      publish: OutputHandler
    ): String =
      Almond.plot(Seq(data), layout, config, div = div)

    def plot()(implicit
      publish: OutputHandler
    ): String =
      plot(Layout(), Config(), "")

    def plot(
      layout: Layout
    )(implicit
      publish: OutputHandler
    ): String =
      plot(layout, Config(), "")

    def plot(
      config: Config
    )(implicit
      publish: OutputHandler
    ): String =
      plot(Layout(), config, "")

    def plot(
      layout: Layout,
      config: Config
    )(implicit
      publish: OutputHandler
    ): String =
      plot(layout, config, "")
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
                   editable: JBoolean        = null,
                 responsive: JBoolean        = null,
      showEditInChartStudio: JBoolean        = null,
            plotlyServerURL: String          = null,
                        div: String          = ""
    )(implicit
      publish: OutputHandler
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
        Config()
          .withEditable(Option(editable).map[Boolean](identity))
          .withResponsive(Option(responsive).map[Boolean](identity))
          .withShowEditInChartStudio(Option(showEditInChartStudio).map[Boolean](identity))
          .withPlotlyServerURL(plotlyServerURL),
        div
      )

    def plot(
      layout: Layout,
      config: Config,
      div: String
    )(implicit
      publish: OutputHandler
    ): String =
      Almond.plot(data, layout, config, div = div)
  }

}
