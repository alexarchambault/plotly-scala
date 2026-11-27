package plotly

import almond.interpreter.api.{DisplayData, OutputHandler}

import scala.util.Random
import plotly.internals.RawJson
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
  ): String =
    display(div, Plotly.jsSnippet(_, data, layout, config), Plotly.jsonSnippet(data, layout, config))

  /** Displays a "raw" plotly.js figure, without loading plotly.js beforehand (see [[plotJson]])
    */
  def plotJsonJs(
      figure: String,
      div: String = ""
  )(implicit
      publish: OutputHandler
  ): String =
    // jsSnippetFromJson checks that figure is valid JSON, before it's passed to addStringifiedJson
    display(div, Plotly.jsSnippetFromJson(_, figure), figure)

  private def display(
      div: String,
      jsSnippet: String => String,
      json: String
  )(implicit
      publish: OutputHandler
  ): String = {

    val (div0, divPart) =
      if (div.isEmpty) {
        val d = randomDiv()
        (d, s"""<div class="chart" id="$d"></div>""")
      } else
        (div, "")

    val baseJs = jsSnippet(div0)

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

  private def ensureInitialized()(implicit publish: OutputHandler): Unit =
    if (!Internal.initialized)
      Internal.synchronized {
        if (!Internal.initialized) {
          init()
          Internal.initialized = true
        }
      }

  def plot(
      data: Seq[Trace],
      layout: Layout = Layout(),
      config: Config = Config(),
      div: String = ""
  )(implicit
      publish: OutputHandler
  ): String = {
    ensureInitialized()
    plotJs(data, layout, config)
  }

  /** Plots a "raw" plotly.js figure
    *
    * @param figure
    *   a plotly.js figure, as a JSON object (`{"data": […], "layout": {…}, "config": {…}}`), like
    *   what `fig.to_json()` returns in plotly-python. It's passed as is to plotly.js, so it can
    *   use any plotly.js feature, even those that plotly-scala doesn't support yet.
    * @param div
    *   id of an existing div element to plot into, or empty to create a new one
    * @return
    *   the id of the div element the figure is plotted into
    */
  def plotJson(
      figure: String,
      div: String = ""
  )(implicit
      publish: OutputHandler
  ): String = {
    RawJson.checkFigure(figure)
    ensureInitialized()
    plotJsonJs(figure, div)
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
