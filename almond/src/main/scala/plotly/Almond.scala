package plotly

import almond.interpreter.api.{DisplayData, OutputHandler}

import scala.util.Random
import plotly.layout._

object Almond {

  object Internal {
    @volatile var initialized = false
  }

  /** Whether RequireJS is available, like in the classic Jupyter Notebook. It isn't in JupyterLab
    * or in Jupyter Notebook >= 7, where plotly.js gets loaded via a script element instead.
    */
  private val hasRequireJs = "typeof require === 'function' && typeof require.config === 'function'"

  /** JavaScript calling `plot` with the Plotly object, once plotly.js is loaded. Without RequireJS,
    * this loads plotly.js from the CDN if [[init]] didn't load it already (if the page got reloaded
    * since, for example).
    */
  private def withPlotly(plot: String): String =
    s"""if ($hasRequireJs)
       |  require(['plotly'], $plot);
       |else {
       |  if (!window.plotlyScalaPlotly)
       |    window.plotlyScalaPlotly = window.Plotly ?
       |      Promise.resolve(window.Plotly) :
       |      new Promise(function (resolve, reject) {
       |        var script = document.createElement('script');
       |        script.src = 'https://cdn.plot.ly/plotly-${Plotly.plotlyVersion}.min.js';
       |        script.onload = function () { resolve(window.Plotly); };
       |        script.onerror = function (e) {
       |          window.plotlyScalaPlotly = undefined;
       |          reject(e);
       |        };
       |        document.head.appendChild(script);
       |      });
       |  window.plotlyScalaPlotly.then($plot);
       |}""".stripMargin

  def init(offline: Boolean = false)(implicit publish: OutputHandler): Unit = {

    // offline mode like in plotly-python

    // offline, plotly.js sees the CommonJS module of define, and sets its exports
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
           |  },
           |  // plotly.js >= 3 doesn't define an AMD module, it only sets window.Plotly
           |  shim: {
           |    plotly: { exports: 'Plotly' }
           |  }
           |});
        """.stripMargin

    // offline, without RequireJS, plotly.js defines window.Plotly itself
    val noRequireInit =
      if (offline)
        s"""(function () {
           |  ${Plotly.plotlyMinJs}
           |})();
           |window.plotlyScalaPlotly = Promise.resolve(window.Plotly);
        """.stripMargin
      else
        withPlotly("function () {}")

    val html = s"""
      <script type="text/javascript">
        if ($hasRequireJs) {
          $requireInit

          require(['plotly'], function(Plotly) {
            window.Plotly = Plotly;
          });
        }
        else {
          $noRequireInit
        }
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

    val js = withPlotly(
      s"""function(Plotly) {
         |  $baseJs
         |}""".stripMargin
    )

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
