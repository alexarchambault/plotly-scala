package plotly
package doc

import java.io.{ByteArrayOutputStream, File, InputStream}
import java.lang.{Double => JDouble}
import java.nio.file.Files

import argonaut.Argonaut._
import argonaut.{Json, Parse}
import plotly.layout.Layout
import org.mozilla.javascript._
import org.scalatest.{FlatSpec, Matchers}

import scala.util.matching.Regex

object DocumentationTests {

  import plotly.Codecs._

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


  def load(path: String): String = {

    val cl = getClass.getClassLoader // resources should be in the same JAR as this, so same loader
    val resPath = s"plotly/doc/$path"
    val is = cl.getResourceAsStream(resPath)

    if (is == null)
      throw new NoSuchElementException(s"Resource $resPath")

    val res = readFully(is)

    new String(res, "UTF-8")
  }

  def resourceTrace(res: String): Trace = {
    val dataStr = load(res)
    val result = dataStr.decodeEither[Trace]
    result.right.getOrElse {
      throw new Exception(s"$res: $result")
    }
  }

  def resourceLayout(res: String): Layout = {
    val dataStr = load(res)
    val result = dataStr.decodeEither[Layout]
    result.right.getOrElse {
      throw new Exception(s"$res: $result")
    }
  }

  private class Plotly {

    var dataOpt = Option.empty[Object]
    var layoutOpt = Option.empty[Object]

    def newPlot(div: String, data: Object, layout: Object): Unit = {
      dataOpt = Option(data)
      layoutOpt = Option(layout)
    }

    def newPlot(div: String, data: Object): Unit = {
      dataOpt = Option(data)
    }

    def plot(div: String, data: Object, layout: Object): Unit =
      newPlot(div, data, layout)
    def plot(div: String, data: Object): Unit =
      newPlot(div, data)

    def result(cx: Context, scope: ScriptableObject): (Seq[Json], Option[Json]) = {
      def stringify(obj: Object) =
        NativeJSON.stringify(cx, scope, obj, null, null).toString

      def jsonRepr(obj: Object): Json = {
        val jsonStr = stringify(obj)
        Parse.parse(jsonStr).left.map { err =>
          throw new Exception(s"Cannot parse JSON: $err\n$jsonStr")
        }.merge
      }

      val data = dataOpt.map(jsonRepr) match {
        case None =>
          throw new NoSuchElementException("data not set")
        case Some(json) =>
          json.array.getOrElse {
            throw new Exception(s"data is not a JSON array\n${json.spaces2}")
          }
      }

      (data, layoutOpt.map(jsonRepr))
    }
  }

  private object Document {
    // stub...
    def getElementById(id: String): String = id
  }

  private object Numeric {
    def linspace(from: Int, to: Int, count: Int) = {
      val step = (to - from).toDouble / (count - 1)
      new NativeArrayWithDefault((0 until count).map(n => from + n * step: JDouble).toArray[AnyRef], 0.0: JDouble)
    }
  }

  def linspaceImpl(cx: Context, thisObj: Scriptable, args: Array[Object], funObj: Function): AnyRef =
    args.toSeq.map(x => x: Any) match {
      case Seq(from: Int, to: Int, step: Int) =>
        Numeric.linspace(from, to, step)
      case other => throw new NoSuchElementException(s"linspace${other.mkString("(", ", ", ")")}")
    }

  def requireImpl(cx: Context, thisObj: Scriptable, args: Array[Object], funObj: Function): AnyRef =
    args match {
      case Array("linspace") => linspace(thisObj)
      case other => throw new NoSuchElementException(s"require${other.mkString("(", ", ", ")")}")
    }

  private def linspace(scope: Scriptable) = new FunctionObject(
    "linspace",
    classOf[DocumentationTests].getMethods.find(_.getName == "linspaceImpl").get,
    scope
  )

  private def require(scope: Scriptable) = new FunctionObject(
    "require",
    classOf[DocumentationTests].getMethods.find(_.getName == "requireImpl").get,
    scope
  )

  def plotlyDemoElements(demo: String): (Seq[Trace], Option[Layout]) = {

    val plotly = new Plotly

    val cx = Context.enter()

    val (rawDataElems, rawLayoutOpt) = try {
      val scope = cx.initStandardObjects()
      ScriptableObject.putProperty(scope, "Plotly", plotly)
      ScriptableObject.putProperty(scope, "document", Document)
      ScriptableObject.putProperty(scope, "numeric", Numeric)
      ScriptableObject.putProperty(scope, "require", require(scope))
      cx.evaluateString(scope, demo, "<cmd>", 1, null)
      plotly.result(cx, scope)
    } catch {
      case e: org.mozilla.javascript.EvaluatorException =>
        println(s"Was running\n$demo\n\n")
        throw new Exception(s"Evaluation error at line ${e.lineNumber()} column ${e.columnNumber()}", e)
    } finally {
      Context.exit()
    }

    val decodeData0 = rawDataElems.map(json => json -> json.as[Trace].toEither)

    val dataErrors = decodeData0.collect {
      case (json, Left((err, h))) =>
        (json, err, h)
    }

    if (dataErrors.nonEmpty) {
      for ((json, err, h) <- dataErrors)
        Console.err.println(s"Decoding data: $err ($h)\n${json.spaces2}\n")

      throw new Exception("Error decoding data (see above messages)")
    }

    val data = decodeData0.collect {
      case (_, Right(data)) => data
    }

    val decodeLayoutOpt = rawLayoutOpt.map(json => json -> json.as[Layout].toEither)

    val layoutOpt = decodeLayoutOpt.map {
      case (json, Left((err, h))) =>
        Console.err.println(s"Decoding layout: $err ($h)\n${json.spaces2}\n")
        throw new Exception("Error decoding layout (see above messages)")

      case (_, Right(layout)) => layout
    }

    (data, layoutOpt)
  }

  def stripFrontMatter(content: String): String = {
    val lines = content.linesIterator.toVector
    lines match {
      case Seq("---", remaining0 @ _*) =>
        val idx = remaining0.indexOf("---")
        if (idx >= 0)
          remaining0
            .drop(idx + 1)
            .mkString("\n")
            .replaceAll(Regex.quote("{%") + ".*" + Regex.quote("%}"), "")
            .replaceAll(Regex.quote("&lt;"), "<")
            .replaceAll(Regex.quote("&gt;"), ">")
        else
          throw new Exception(s"Unrecognized format:\n$content")
      case _ =>
        content
    }
  }

}

class DocumentationTests extends FlatSpec with Matchers {

  import DocumentationTests._

  val dir = new File("plotly-documentation/_posts/plotly_js")
  val subDirNames = Seq(
    "basic/line_and_scatter",
    "basic/line-plots",
    "basic/bar",
    "basic/horizontal-bar",
    // TODO? Pie charts
    "financial/time-series",
    "basic/bubble",
    "basic/area",
    "layout/sizing",
    // TODO? Gauge charts
    // TODO Multiple chart types (needs contour)
    // TODO Shapes (need mock of d3)
    "subplot/subplots",
    "subplot/multiple-axes",
    "subplot/insets",
    // TODO Responsive demo (only a demo, no new chart type / attributes)
    "statistical/error-bar",
    // TODO Continuous error bars
    "statistical/box",
    // TODO 2D Density plots
    "statistical/histogram",
    "scientific/heatmap",
    // TODO 2D Histograms
    // TODO Wind rose charts
    // TODO Contour plots
    // TODO Heatmaps
    // TODO Heatmap and contour colorscales
    // TODO Polar charts
    "scientific/log",
    // TODO Financial charts
    // TODO Maps
    "3d/3d-surface"
  )

  val subDirs = subDirNames.map(new File(dir, _))

  for {
    subDir <- subDirs
    post <- subDir.listFiles().sorted
    if !post.getName.startsWith(".")
  } {
    s"$subDir" should s"$post" in {
      val rawContent = new String(Files.readAllBytes(post.toPath), "UTF-8")
      val content = stripFrontMatter(rawContent)
        .replace("<br>", "\\n")
        .replace("</br>", "\\n")
        .replace("(...size)", "(size[0])") // rhino doesn't seem to support the spead (...) operator
        .replace("desired_maximum_marker_size**2", "desired_maximum_marker_size*desired_maximum_marker_size")

      if (content.contains("Plotly.d3.csv"))
        println(s"Ignoring $post (Plotly.d3.csv not implemented)")
      else {

        val lines = content
          .linesIterator
          .toVector
          .map(_.trim)
          .filter(_.nonEmpty)

        if (lines.nonEmpty)
          plotlyDemoElements(content)
      }
    }
  }

}
