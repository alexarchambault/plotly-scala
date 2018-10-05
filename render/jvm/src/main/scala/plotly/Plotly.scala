package plotly

import java.io.{ByteArrayOutputStream, File, InputStream}

import plotly.Codecs._
import plotly.element.Color
import plotly.layout._
import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}
import java.nio.file.Files

import argonaut.Argonaut._
import argonaut.PrettyParams

import scala.annotation.tailrec

object Plotly {

  private val printer = PrettyParams.nospace.copy(dropNullKeys = true)

  def jsSnippet(div: String, data: Seq[Trace], layout: Layout): String = {

    val b = new StringBuilder

    b ++= "(function () {\n"

    for ((d, idx) <- data.zipWithIndex) {
      b ++= s"  var data$idx = "
      b ++= printer.pretty(d.asJson)
      b ++= ";\n"
    }

    b ++= "\n  "
    b ++= data.indices.map(idx => s"data$idx").mkString("var data = [", ", ", "];")
    b ++= "\n"
    b ++= "  var layout = "
    b ++= printer.pretty(layout.asJson)
    b ++= ";\n\n  Plotly.plot('"
    b ++= div.replaceAll("'", "\\'")
    b ++= "', data, layout);\n"

    b ++= "})();"

    b.result()
  }

  private def readFully(is: InputStream): Array[Byte] = {
    val buffer = new ByteArrayOutputStream()
    val data = Array.ofDim[Byte](16384)

    var nRead = is.read(data, 0, data.length)
    while (nRead != -1) {
      buffer.write(data, 0, nRead)
      nRead = is.read(data, 0, data.length)
    }

    buffer.flush()
    buffer.toByteArray
  }

  val plotlyVersion = "1.12.0" // FIXME Get from build.sbt

  def plotlyMinJs: String = {
    var is: InputStream = null
    try {
      is = getClass.getClassLoader.getResourceAsStream(s"META-INF/resources/webjars/plotly.js/$plotlyVersion/dist/plotly.min.js")
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
    useCdn: Boolean = true,
    openInBrowser: Boolean = true,
    addSuffixIfExists: Boolean = true
  ): File = {

    val f0 = new File(path)

    val f =
      if (addSuffixIfExists) {
        lazy val name = f0.getName
        lazy val idx = name.lastIndexOf('.')

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
         |<title>${layout.title.getOrElse("plotly chart")}</title>
         |$plotlyHeader
         |</head>
         |<body>
         |<div id="$divId"></div>
         |<script>
         |${jsSnippet(divId, traces, layout)}
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
                   path: String          = "./plot.html",
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
                 useCdn: Boolean         = true,
          openInBrowser: Boolean         = true,
      addSuffixIfExists: Boolean         = true
    ): Unit =
      plot(
        path,
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
        useCdn,
        openInBrowser,
        addSuffixIfExists
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
                   path: String          = "./plot.html",
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
                 useCdn: Boolean         = true,
          openInBrowser: Boolean         = true,
      addSuffixIfExists: Boolean         = true
    ): Unit =
      plot(
        path,
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
        useCdn,
        openInBrowser,
        addSuffixIfExists
      )
  }

}
