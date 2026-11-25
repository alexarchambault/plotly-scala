package plotly

import almond.interpreter.api.{DisplayData, OutputHandler}

import scala.util.Random
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
           |    plotly: 'https://cdn.plot.ly/plotly-${Plotly.plotlyVersion}.min'
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
    val json   = Plotly.jsonSnippet(data, layout, config)

    val js =
      s"""require(['plotly'], function(Plotly) {
         |  $baseJs
         |});
      """.stripMargin

    val data0 = DisplayData()
      .add(
        "text/html",
        s"""$divPart
           |<script>$js</script>
         """.stripMargin
      )
      .addStringifiedJson("application/vnd.plotly.v1+json", json)

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
        layout: Layout,
        config: Config,
        div: String
    )(implicit
        publish: OutputHandler
    ): String =
      Almond.plot(data, layout, config, div = div)

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

}
