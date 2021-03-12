package plotly
package doc

import java.io.File
import java.nio.file.Files

import argonaut.ArgonautShapeless._
import argonaut.{DecodeJson, DecodeResult, Json, Parse}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import plotly.element._
import shapeless.Witness

object SchemaTests {

  sealed abstract class Attribute extends Product with Serializable

  object Attribute {
    case class ConstantString(value: String) extends Attribute

    case class Flag(
      valType: Witness.`"flaglist"`.T,
      flags: List[String],
      description: String,
      role: String
    ) extends Attribute

    case class Enumerated(
      valType: Witness.`"enumerated"`.T,
      description: String,
      role: String,
      values: List[String]
    ) extends Attribute

    case class Other(json: Json) extends Attribute

    implicit val decode: DecodeJson[Attribute] =
      DecodeJson { c =>
        val constantString = c.as[String].map[Attribute](ConstantString(_))
        def flag = c.as[Flag].map[Attribute](x => x)
        def enumerated = c.as[Enumerated].map[Attribute](x => x)
        def other = DecodeResult.ok[Attribute](Other(c.focus))

        constantString.toOption.map(DecodeResult.ok)
          .orElse(flag.toOption.map(DecodeResult.ok))
          .orElse(enumerated.toOption.map(DecodeResult.ok))
          .getOrElse(other)
      }
  }

  case class Trace(
    description: Option[String],
    attributes: Map[String, Attribute]
  ) {
    def flagAttribute(name: String): Attribute.Flag =
      attributes.get(name) match {
        case None =>
          throw new NoSuchElementException(s"attribute $name")
        case Some(f: Attribute.Flag) =>
          f
        case Some(nonFlag) =>
          throw new Exception(s"Attribute $name not a flag ($nonFlag)")
      }

    def enumeratedAttribute(name: String): Attribute.Enumerated =
      attributes.get(name) match {
        case None =>
          throw new NoSuchElementException(s"attribute $name")
        case Some(f: Attribute.Enumerated) =>
          f
        case Some(nonFlag) =>
          throw new Exception(s"Attribute $name not a flag ($nonFlag)")
      }
  }

  case class Schema(
    traces: Map[String, Trace]
  )

  val schemaFile = new File("plotly-documentation/_data/plotschema.json")

  lazy val schema: Schema = {

    val schemaContent = new String(Files.readAllBytes(schemaFile.toPath), "UTF-8")

    val schemaJson = Parse.parse(schemaContent) match {
      case Left(error) =>
        throw new Exception(s"Cannot parse schema: $error")
      case Right(json) => json
    }

    schemaJson.as[Schema].toEither match {
      case Left(error) =>
        println(schemaJson.obj.map(_.fields).getOrElse(Nil).mkString("\n"))
        throw new Exception(s"Cannot decode schema: $error")
      case Right(schema) =>
        schema
    }
  }

}

class SchemaTests extends AnyFlatSpec with Matchers {

  private def compareValues(fromSchema: Set[String], fromLib: Set[String]): Unit = {
    val onlySchema = (fromSchema -- fromLib).toVector.sorted
    val onlyLib = (fromLib -- fromSchema).toVector.sorted

    assert(onlySchema.isEmpty, s"Only in schema: ${onlySchema.mkString(", ")}")
    assert(onlyLib.isEmpty, s"Only in lib: ${onlyLib.mkString(", ")}")
  }

  "Scatter mode flags" should "be exhaustive" in {
    val fromSchema = SchemaTests.schema
      .traces("scatter")
      .flagAttribute("mode")
      .flags
      .toSet

    val fromLib = ScatterMode.flags
      .map(_.label)
      .toSet

    compareValues(fromSchema, fromLib)
  }

  "Text position" should "be exhausitve" in {
    val fromSchema = SchemaTests.schema
      .traces("scatter")
      .enumeratedAttribute("textposition")
      .values
      .toSet

    val fromLib = Enumerate[TextPosition].apply()
      .map(_.label)
      .toSet

    compareValues(fromSchema, fromLib)
  }

}

