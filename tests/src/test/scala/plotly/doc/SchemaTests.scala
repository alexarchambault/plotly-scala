package plotly
package doc

import java.io.File
import java.nio.file.Files

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
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

  def enumeratedAttributeValues(trace: String, name: String): Seq[String] =
    attribute(trace, name, "enumerated")("values").arr.map(_.str).toSeq

}

class SchemaTests extends AnyFlatSpec with Matchers {

  private def compareValues(fromSchema: Set[String], fromLib: Set[String]): Unit = {
    val onlySchema = (fromSchema -- fromLib).toVector.sorted
    val onlyLib    = (fromLib -- fromSchema).toVector.sorted

    assert(onlySchema.isEmpty, s"Only in schema: ${onlySchema.mkString(", ")}")
    assert(onlyLib.isEmpty, s"Only in lib: ${onlyLib.mkString(", ")}")
  }

  "Scatter mode flags" should "be exhaustive" in {
    val fromSchema = SchemaTests
      .flagAttributeFlags("scatter", "mode")
      .toSet

    val fromLib = ScatterMode.flags
      .map(_.label)
      .toSet

    compareValues(fromSchema, fromLib)
  }

  "Text position" should "be exhausitve" in {
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
