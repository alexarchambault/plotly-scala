package plotly
package doc

import java.io.File
import java.nio.file.Files

import plotly.element._

object SchemaTests {

  val schemaFile = new File("plotly-documentation/_data/plotschema.json")

  lazy val schema: ujson.Value =
    ujson.read(Files.readAllBytes(schemaFile.toPath))

  private def attribute(trace: String, name: String, valType: String): ujson.Value = {
    val attr = schema("traces")(trace)("attributes").obj
      .getOrElse(name, throw new NoSuchElementException(s"attribute $name"))
    val attrValType = attr.objOpt.flatMap(_.get("valType")).flatMap(_.strOpt)
    if (!attrValType.contains(valType))
      throw new Exception(s"Attribute $name not a $valType ($attr)")
    attr
  }

  def flagAttributeFlags(trace: String, name: String): Seq[String] =
    attribute(trace, name, "flaglist")("flags").arr.map(_.str).toSeq

  def traceAttributes(trace: String): Map[String, ujson.Value] =
    schema("traces")(trace)("attributes").obj.toMap

  def enumeratedAttributeValues0(trace: String, name: String): Seq[ujson.Value] =
    attribute(trace, name, "enumerated")("values").arr.toSeq

  def enumeratedAttributeValues(trace: String, name: String): Seq[String] =
    attribute(trace, name, "enumerated")("values").arr.map(_.str).toSeq

}

class SchemaTests extends munit.FunSuite {

  private def compareValues(fromSchema: Set[String], fromLib: Set[String])(implicit loc: munit.Location): Unit = {
    val onlySchema = (fromSchema -- fromLib).toVector.sorted
    val onlyLib    = (fromLib -- fromSchema).toVector.sorted

    assert(onlySchema.isEmpty, s"Only in schema: ${onlySchema.mkString(", ")}")
    assert(onlyLib.isEmpty, s"Only in lib: ${onlyLib.mkString(", ")}")
  }

  test("Scatter mode flags should be exhaustive") {
    val fromSchema = SchemaTests
      .flagAttributeFlags("scatter", "mode")
      .toSet

    val fromLib = ScatterMode.flags
      .map(_.label)
      .toSet

    compareValues(fromSchema, fromLib)
  }

  // the trace types having a case class extending Trace
  private val traceTypes = Seq(
    "bar",
    "box",
    "candlestick",
    "contour",
    "funnel",
    "funnelarea",
    "heatmap",
    "histogram",
    "histogram2d",
    "histogram2dcontour",
    "image",
    "ohlc",
    "pie",
    "scatter",
    "scatter3d",
    "surface",
    "violin",
    "waterfall"
  )

  test("Hover info elements should include the hoverinfo flags of all trace types") {
    val fromSchema = traceTypes
      .flatMap(SchemaTests.traceAttributes(_).get("hoverinfo"))
      .flatMap(_("flags").arr.map(_.str))
      .toSet
    val fromLib = HoverInfo.elements.map(_.label).toSet
    val missing = (fromSchema -- fromLib).toVector.sorted
    assert(missing.isEmpty, s"Missing in lib: ${missing.mkString(", ")}")
  }

  test("Text info flags should include the textinfo flags of all trace types") {
    val fromSchema = traceTypes
      .flatMap(SchemaTests.traceAttributes(_).get("textinfo"))
      .flatMap(_("flags").arr.map(_.str))
      .toSet
    val fromLib = TextInfo.flags.map(_.label).toSet
    val missing = (fromSchema -- fromLib).toVector.sorted
    assert(missing.isEmpty, s"Missing in lib: ${missing.mkString(", ")}")
  }

  test("Inside text orientation should be exhaustive") {
    compareValues(
      SchemaTests.enumeratedAttributeValues("pie", "insidetextorientation").toSet,
      Enumerate[InsideTextOrientation].apply().map(_.label).toSet
    )
  }

  test("Rotation direction should be exhaustive") {
    compareValues(
      SchemaTests.enumeratedAttributeValues("pie", "direction").toSet,
      Enumerate[RotationDirection].apply().map(_.label).toSet
    )
  }

  test("Size units should be exhaustive") {
    val colorBar = SchemaTests.traceAttributes("contour")("colorbar")
    for (attr <- Seq("lenmode", "thicknessmode"))
      compareValues(
        colorBar(attr)("values").arr.map(_.str).toSet,
        Enumerate[SizeUnit].apply().map(_.label).toSet
      )
  }

  test("Contour coloring should be exhaustive") {
    compareValues(
      SchemaTests.traceAttributes("contour")("contours")("coloring")("values").arr.map(_.str).toSet,
      Enumerate[ContourColoring].apply().map(_.label).toSet
    )
  }

  test("Z smoothing should be exhaustive") {
    val fromSchema = SchemaTests.traceAttributes("heatmap")("zsmooth")("values").arr.map(_.render()).toSet
    // the labels, as JSON, and false
    val fromLib = Enumerate[ZSmooth.Labeled].apply().map(l => ujson.Str(l.label).render()).toSet + "false"
    compareValues(fromSchema, fromLib)
  }

  test("Box points should be exhaustive") {
    val fromSchema = SchemaTests.enumeratedAttributeValues0("box", "boxpoints").map(_.render()).toSet
    // the labels, as JSON, and false (true isn't accepted by plotly.js)
    val fromLib = Enumerate[BoxPoints.Labeled].apply().map(l => ujson.Str(l.label).render()).toSet + "false"
    compareValues(fromSchema, fromLib)
  }

  test("Box mode should be exhaustive") {
    compareValues(
      SchemaTests.schema("traces")("box")("layoutAttributes")("boxmode")("values").arr.map(_.str).toSet,
      Enumerate[plotly.layout.BoxMode].apply().map(_.label).toSet
    )
  }

  test("Violin enums should be exhaustive") {
    compareValues(
      SchemaTests.enumeratedAttributeValues("violin", "side").toSet,
      Enumerate[ViolinSide].apply().map(_.label).toSet
    )
    compareValues(
      SchemaTests.enumeratedAttributeValues("violin", "spanmode").toSet,
      Enumerate[SpanMode].apply().map(_.label).toSet
    )
    compareValues(
      SchemaTests.enumeratedAttributeValues("violin", "scalemode").toSet,
      Enumerate[ViolinScaleMode].apply().map(_.label).toSet
    )
    compareValues(
      SchemaTests.schema("traces")("violin")("layoutAttributes")("violinmode")("values").arr.map(_.str).toSet,
      Enumerate[plotly.layout.BoxMode].apply().map(_.label).toSet
    )
  }

  test("Dash styles should include the named ones") {
    // plotly.js also accepts dash lengths, like "5px,10px,2px,2px", as dash styles
    compareValues(
      SchemaTests.schema("traces")("scatter")("attributes")("line")("dash")("values").arr.map(_.str).toSet,
      Enumerate[Dash].apply().map(_.label).toSet
    )
  }

  test("Axis types should be exhaustive") {
    compareValues(
      SchemaTests.schema("layout")("layoutAttributes")("xaxis")("type")("values").arr.map(_.str).toSet,
      Enumerate[AxisType].apply().map(_.label).toSet
    )
  }

  test("Waterfall enums should be exhaustive") {
    compareValues(
      SchemaTests.traceAttributes("waterfall")("connector")("mode")("values").arr.map(_.str).toSet,
      Enumerate[ConnectorMode].apply().map(_.label).toSet
    )
    compareValues(
      SchemaTests.schema("traces")("waterfall")("layoutAttributes")("waterfallmode")("values").arr.map(_.str).toSet,
      Enumerate[plotly.layout.BoxMode].apply().map(_.label).toSet
    )
  }

  test("Title positions should be exhaustive") {
    compareValues(
      SchemaTests.traceAttributes("pie")("title")("position")("values").arr.map(_.str).toSet,
      Enumerate[TitlePosition].apply().map(_.label).toSet
    )
  }

  test("Funnel modes should be bar modes") {
    val fromSchema = SchemaTests.schema("traces")("funnel")("layoutAttributes")("funnelmode")("values").arr.map(_.str)
    val fromLib    = Enumerate[plotly.layout.BarMode].apply().map(_.label).toSet
    val missing    = fromSchema.filterNot(fromLib).toVector.sorted
    assert(missing.isEmpty, s"Missing in lib: ${missing.mkString(", ")}")
  }

  test("Text position should be exhaustive") {
    val fromSchema = SchemaTests
      .enumeratedAttributeValues("scatter", "textposition")
      .toSet

    val fromLib = Enumerate[TextPosition]
      .apply()
      .map(_.label)
      .toSet

    compareValues(fromSchema, fromLib)
  }

}
