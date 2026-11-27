package plotly

import java.io.{ByteArrayOutputStream, File, InputStream}

import plotly.Codecs._
import plotly.layout._
import java.nio.file.Files

import com.github.plokhotnyuk.jsoniter_scala.core.writeToString
import plotly.internals.{Properties, RawJson}

import scala.annotation.tailrec

object Plotly {

  def jsonSnippet(data: Seq[Trace], layout: Layout, config: Config): String =
    s"""{"data":${writeToString(data)},"layout":${writeToString(layout)},"config":${writeToString(config)}}"""

  // JavaScript string literal for s
  private def jsString(s: String): String =
    "'" + s.replace("\\", "\\\\").replace("'", "\\'") + "'"

  def jsSnippet(div: String, data: Seq[Trace], layout: Layout, config: Config): String = {

    val b = new StringBuilder

    b ++= "(function () {\n"

    for ((d, idx) <- data.zipWithIndex) {
      b ++= s"  var data$idx = "
      b ++= writeToString(d)
      b ++= ";\n"
    }

    b ++= "\n  "
    b ++= data.indices.map(idx => s"data$idx").mkString("var data = [", ", ", "];")
    b ++= "\n"
    b ++= "  var layout = "
    b ++= writeToString(layout)
    b ++= ";\n var config = "
    b ++= writeToString(config)
    b ++= ";\n\n  Plotly.newPlot("
    b ++= jsString(div)
    b ++= ", data, layout, config);\n"

    b ++= "})();"

    b.result()
  }

  /** JavaScript snippet plotting a "raw" plotly.js figure in the div element with id `div`
    *
    * @param figure
    *   a plotly.js figure, as a JSON object (`{"data": […], "layout": {…}, "config": {…}}`), like
    *   what `fig.to_json()` returns in plotly-python. It's passed as is to plotly.js, so it can
    *   use any plotly.js feature, even those that plotly-scala doesn't support yet.
    */
  def jsSnippetFromJson(div: String, figure: String): String = {
    RawJson.checkFigure(figure)
    s"""(function () {
       |  var figure = ${RawJson.escapeForScript(figure)};
       |
       |  Plotly.newPlot(${jsString(div)}, figure);
       |})();""".stripMargin
  }

  private def readFully(is: InputStream): Array[Byte] = {
    val buffer = new ByteArrayOutputStream()
    val data   = Array.ofDim[Byte](16384)

    var nRead = is.read(data, 0, data.length)
    while (nRead != -1) {
      buffer.write(data, 0, nRead)
      nRead = is.read(data, 0, data.length)
    }

    buffer.flush()
    buffer.toByteArray
  }

  def plotlyVersion: String =
    Properties.plotlyJsVersion

  def plotlyMinJs: String = {
    var is: InputStream = null
    try {
      is = getClass.getClassLoader.getResourceAsStream(
        s"META-INF/resources/webjars/plotly.js-dist-min/$plotlyVersion/plotly.min.js"
      )
      if (is == null)
        throw new Exception(s"plotly.min.js resource not found")

      new String(readFully(is), "UTF-8")
    } finally {
      if (is != null)
        is.close()
    }
  }

  def plot(
      path: String,
      traces: Seq[Trace],
      layout: Layout,
      config: Config = Config(),
      useCdn: Boolean = true,
      openInBrowser: Boolean = true,
      addSuffixIfExists: Boolean = true
  ): File =
    writeHtml(
      path,
      layout.title.flatMap(_.text).getOrElse("plotly chart"),
      jsSnippet(_, traces, layout, config),
      useCdn = useCdn,
      openInBrowser = openInBrowser,
      addSuffixIfExists = addSuffixIfExists
    )

  /** Writes an HTML file plotting a "raw" plotly.js figure, and optionally opens it in a browser
    *
    * @param figure
    *   a plotly.js figure, as a JSON object (`{"data": […], "layout": {…}, "config": {…}}`), like
    *   what `fig.to_json()` returns in plotly-python
    * @param title
    *   title of the HTML page
    */
  def plotJson(
      path: String,
      figure: String,
      title: String = "plotly chart",
      useCdn: Boolean = true,
      openInBrowser: Boolean = true,
      addSuffixIfExists: Boolean = true
  ): File =
    writeHtml(
      path,
      title,
      jsSnippetFromJson(_, figure),
      useCdn = useCdn,
      openInBrowser = openInBrowser,
      addSuffixIfExists = addSuffixIfExists
    )

  private def htmlEscape(s: String): String =
    s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")

  private def writeHtml(
      path: String,
      title: String,
      jsSnippet: String => String,
      useCdn: Boolean,
      openInBrowser: Boolean,
      addSuffixIfExists: Boolean
  ): File = {

    val f0 = new File(path)

    val f =
      if (addSuffixIfExists) {
        lazy val name = f0.getName
        lazy val idx  = name.lastIndexOf('.')

        lazy val (prefix, suffixOpt) =
          if (idx < 0)
            (name, None)
          else
            (name.take(idx), Some(name.drop(idx + 1)))

        def nameWithIndex(idx: Int) =
          s"$prefix-$idx${suffixOpt.fold("")("." + _)}"

        @tailrec
        def nonExisting(counter: Option[Int]): File = {
          val f = counter.fold(f0) { n =>
            new File(f0.getParentFile, nameWithIndex(n))
          }

          if (f.exists())
            nonExisting(counter.fold(Some(1))(n => Some(n + 1)))
          else
            f
        }

        nonExisting(None)
      } else if (f0.exists())
        throw new Exception(s"$f0 already exists")
      else
        f0

    val plotlyHeader =
      if (useCdn)
        s"""<script src="https://cdn.plot.ly/plotly-$plotlyVersion.min.js"></script>"""
      else
        s"<script>$plotlyMinJs</script>"

    val divId = "chart"

    val html =
      s"""<!DOCTYPE html>
         |<html>
         |<head>
         |<meta charset="UTF-8">
         |<title>${htmlEscape(title)}</title>
         |$plotlyHeader
         |</head>
         |<body>
         |<div id="$divId"></div>
         |<script>
         |${jsSnippet(divId)}
         |</script>
         |</body>
         |</html>
       """.stripMargin

    Files.write(f.toPath, html.getBytes("UTF-8"))

    if (openInBrowser) {
      val cmdOpt = sys.props.get("os.name").map(_.toLowerCase) match {
        case Some("mac os x") =>
          Some(Seq("open", f.getAbsolutePath))
        case Some(win) if win.startsWith("windows") =>
          Some(Seq("cmd", s"start ${f.getAbsolutePath}"))
        case Some(lin) if lin.indexOf("linux") >= 0 =>
          Some(Seq("xdg-open", f.getAbsolutePath))
        case other =>
          None
      }

      cmdOpt match {
        case Some(cmd) =>
          sys.process.Process(cmd).!
        case None =>
          Console.err.println(s"Don't know how to open ${f.getAbsolutePath}")
      }
    }

    f
  }

  implicit class TraceOps(val trace: Trace) extends AnyVal {
    def plot(
        path: String,
        layout: Layout,
        useCdn: Boolean,
        openInBrowser: Boolean,
        addSuffixIfExists: Boolean
    ): Unit =
      Plotly.plot(
        path,
        Seq(trace),
        layout,
        useCdn = useCdn,
        openInBrowser = openInBrowser,
        addSuffixIfExists = addSuffixIfExists
      )

    def plot(
        path: String,
        layout: Layout
    ): Unit =
      plot(
        path,
        layout,
        useCdn = true,
        openInBrowser = true,
        addSuffixIfExists = true
      )
  }

  implicit class TraceSeqOps(val traces: Seq[Trace]) extends AnyVal {
    def plot(
        path: String,
        layout: Layout,
        useCdn: Boolean,
        openInBrowser: Boolean,
        addSuffixIfExists: Boolean
    ): Unit =
      Plotly.plot(
        path,
        traces,
        layout,
        useCdn = useCdn,
        openInBrowser = openInBrowser,
        addSuffixIfExists = addSuffixIfExists
      )

    def plot(
        path: String,
        layout: Layout
    ): Unit =
      plot(
        path,
        layout,
        useCdn = true,
        openInBrowser = true,
        addSuffixIfExists = true
      )
  }

}
