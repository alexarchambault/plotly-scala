package plotly
package internals

import java.math.BigInteger

import argonaut._
import argonaut.Argonaut._
import argonaut.ArgonautShapeless._
import argonaut.derive._
import shapeless._

import scala.util.Try
import plotly.element._
import plotly.layout._

object ArgonautCodecsInternals extends ArgonautCodecsExtra {

  sealed abstract class IsWrapper[W]

  implicit def isWrapperEncode[W, L <: HList, T]
   (implicit
     ev: IsWrapper[W],
     gen: Generic.Aux[W, L],
     isHCons: ops.hlist.IsHCons.Aux[L, T, HNil],
     underlying: EncodeJson[T]
   ): EncodeJson[W] =
    EncodeJson { w =>
      val t = isHCons.head(gen.to(w))
      t.asJson
    }

  implicit def isWrapperDecode[W, L <: HList, T]
   (implicit
     ev: IsWrapper[W],
     gen: Generic.Aux[W, L],
     isHCons: ops.hlist.IsHCons.Aux[L, T, HNil],
     underlying: DecodeJson[T]
   ): DecodeJson[W] =
    DecodeJson { c =>
      c.as[T].map(t =>
        gen.from((t :: HNil).asInstanceOf[L]) // FIXME
      )
    }

  implicit val boxMeanBoolIsWrapper: IsWrapper[BoxMean.Bool] = null
  implicit val boxPointsBoolIsWrapper: IsWrapper[BoxPoints.Bool] = null
  implicit val sequenceDoublesIsWrapper: IsWrapper[Sequence.Doubles] = null
  implicit val sequenceNestedDoublesIsWrapper: IsWrapper[Sequence.NestedDoubles] = null
  implicit val sequenceNestedIntsIsWrapper: IsWrapper[Sequence.NestedInts] = null
  implicit val sequenceStringsIsWrapper: IsWrapper[Sequence.Strings] = null
  implicit val sequenceDatetimesIsWrapper: IsWrapper[Sequence.DateTimes] = null
  implicit val rangeDoublesIsWrapper: IsWrapper[Range.Doubles] = null
  implicit val rangeDatetimesIsWrapper: IsWrapper[Range.DateTimes] = null
  implicit val doubleElementIsWrapper: IsWrapper[Element.DoubleElement] = null
  implicit val stringElementIsWrapper: IsWrapper[Element.StringElement] = null
  implicit def oneOrSeqOneIsWrapper[T]: IsWrapper[OneOrSeq.One[T]] = null
  implicit def oneOrSeqSequenceIsWrapper[T]: IsWrapper[OneOrSeq.Sequence[T]] = null

  def flagEncoder[T, F](flags: T => Set[F], label: F => String): EncodeJson[T] =
    EncodeJson { t =>
      val s = flags(t).toSeq match {
        case Seq() => "none"
        case nonEmpty => nonEmpty.map(label).mkString("+")
      }

      s.asJson
    }

  def flagDecoder[T, F](type0: String, map: Map[String, F], build: Set[F] => T): DecodeJson[T] =
    DecodeJson { c =>
      c.as[String].flatMap { s =>
        val flags =
          if (s == "none")
            DecodeResult.ok(Set.empty[F])
          else
            s.split('+').foldLeft[DecodeResult[Set[F]]](DecodeResult.ok(Set.empty[F])) {
              case (acc, f) =>
                for {
                  acc0 <- acc
                  f0 <- map.get(f).fold[DecodeResult[F]](DecodeResult.fail(s"Unrecognized $type0: $f", c.history))(DecodeResult.ok)
                } yield acc0 + f0
            }

        flags.map(build)
      }
    }

  sealed abstract class IsEnum[-T] {
    def label(t: T): String
  }

  object IsEnum {
    def apply[T](implicit isEnum: IsEnum[T]): IsEnum[T] = isEnum

    def instance[T](f: T => String): IsEnum[T] =
      new IsEnum[T] {
        def label(t: T) = f(t)
      }
  }

  implicit def isEnumEncoder[T: IsEnum]: EncodeJson[T] =
    EncodeJson.of[String].contramap(IsEnum[T].label)

  implicit def isEnumDecoder[T]
   (implicit
     isEnum: IsEnum[T],
     enum: Enumerate[T],
     typeable: Typeable[T]
   ): DecodeJson[T] =
    DecodeJson {
      val underlying = DecodeJson.of[String]
      val map = enum().map(e => isEnum.label(e) -> e).toMap
      val name = typeable.describe // TODO split in words

      c =>
        underlying(c).flatMap { s =>
          map.get(s) match {
            case None => DecodeResult.fail(s"Unrecognized $name: '$s'", c.history)
            case Some(m) => DecodeResult.ok(m)
          }
        }
    }

  implicit val anchorIsEnum = IsEnum.instance[Anchor](_.label)
  implicit val refIsEnum = IsEnum.instance[Ref](_.label)
  implicit val axisAnchorIsEnum = IsEnum.instance[AxisAnchor](_.label)
  implicit val axisReferenceIsEnum = IsEnum.instance[AxisReference](_.label)
  implicit val axisTypeIsEnum = IsEnum.instance[AxisType](_.label)
  implicit val barModeIsEnum = IsEnum.instance[BarMode](_.label)
  implicit val boxModeIsEnum = IsEnum.instance[BoxMode](_.label)
  implicit val dashIsEnum = IsEnum.instance[Dash](_.label)
  implicit val fillIsEnum = IsEnum.instance[Fill](_.label)
  implicit val hoverModeIsEnum = IsEnum.instance[HoverMode](_.label)
  implicit val lineShapeIsEnum = IsEnum.instance[LineShape](_.label)
  implicit val orientationIsEnum = IsEnum.instance[Orientation](_.label)
  implicit val traceOrderIsEnum = IsEnum.instance[TraceOrder](_.label)
  implicit val boxMeanOtherIsEnum = IsEnum.instance[BoxMean.Labeled](_.label)
  implicit val boxPointsOtherIsEnum = IsEnum.instance[BoxPoints.Labeled](_.label)
  implicit val textPositionIsEnum = IsEnum.instance[TextPosition](_.label)
  implicit val barTextPositionIsEnum = IsEnum.instance[BarTextPosition](_.label)
  implicit val sideIsEnum = IsEnum.instance[Side](_.label)
  implicit val symbolIsEnum = IsEnum.instance[Symbol](_.label)
  implicit val ticksIsEnum = IsEnum.instance[Ticks](_.label)
  implicit val histNormIsEnum = IsEnum.instance[HistNorm](_.label)
  implicit val sizeModeIsEnum = IsEnum.instance[SizeMode](_.label)
  implicit val hoverOnIsEnum = IsEnum.instance[HoverOn](_.label)
  implicit val groupNormIsEnum = IsEnum.instance[GroupNorm](_.label)
  implicit val histFuncIsEnum = IsEnum.instance[HistFunc](_.label)
  implicit val tickModeIsEnum = IsEnum.instance[TickMode](_.mode)
  implicit val patternIsEnum = IsEnum.instance[Pattern](_.label)
  implicit val rowOrderIsEnum = IsEnum.instance[RowOrder](_.label)

  def jsonSumDirectCodecFor(name: String): JsonSumCodec = new JsonSumCodec {
    def encodeEmpty: Nothing =
      throw new IllegalArgumentException(s"empty $name")

    def encodeField(fieldOrObj: Either[Json, (String, Json)]): Json =
      fieldOrObj match {
        case Left(other) => other
        case Right((_, content)) => content
      }

    def decodeEmpty(cursor: HCursor): DecodeResult[Nothing] =
    // FIXME Sometimes reports the wrong error (in case of two nested sum types)
      DecodeResult.fail(s"unrecognized $name", cursor.history)

    def decodeField[A](name: String, cursor: HCursor, decode: DecodeJson[A]): DecodeResult[Either[ACursor, A]] =
      DecodeResult.ok {
        val o = decode(cursor)
        o.toOption
          .toRight(ACursor.ok(cursor))
      }
  }

  case class JsonProductObjCodecNoEmpty(
    toJsonName: String => String = identity
  ) extends JsonProductCodec {

    private val underlying = JsonProductCodec.adapt(toJsonName)

    val encodeEmpty: Json = underlying.encodeEmpty

    def encodeField(field: (String, Json), obj: Json, default: => Option[Json]): Json =
      underlying.encodeField(field, obj, default)

    def decodeEmpty(cursor: HCursor): DecodeResult[Unit] =
      if (cursor.focus == Json.obj())
        DecodeResult.ok(())
      else
        DecodeResult.fail(
          s"Found extra fields: ${cursor.fields.toSeq.flatten.mkString(", ")}",
          cursor.history
        )

    def decodeField[A](name: String, cursor: HCursor, decode: DecodeJson[A], default: Option[A]): DecodeResult[(A, ACursor)] = {
      val c = cursor.downField(toJsonName(name))

      def result = c.as(decode).map((_, if (c.succeeded) c.delete else ACursor.ok(cursor)))

      default match {
        case None => result
        case Some(d) =>
          if (c.succeeded)
            result
          else
            DecodeResult.ok((d, ACursor.ok(cursor)))
      }
    }
  }

  object JsonProductObjCodecNoEmpty {
    val default = JsonProductObjCodecNoEmpty()
  }

  implicit val encodeHoverInfo: EncodeJson[HoverInfo] =
    EncodeJson.of[String].contramap(_.label)
  implicit val decodeHoverInfo: DecodeJson[HoverInfo] =
    DecodeJson { c =>
      DecodeJson.of[String].apply(c).flatMap {
        case "all" => DecodeResult.ok(HoverInfo.All)
        case "skip" => DecodeResult.ok(HoverInfo.Skip)
        case "none" => DecodeResult.ok(HoverInfo.None)
        case combination =>
          val results = combination.split('+').map {
            case "x" => Right(HoverInfo.X)
            case "y" => Right(HoverInfo.Y)
            case "z" => Right(HoverInfo.Z)
            case "text" => Right(HoverInfo.Text)
            case "name" => Right(HoverInfo.Name)
            case other => Left(s"Unrecognized hover info element: $other")
          }
          if (results.exists(_.isLeft))
            DecodeResult.fail(
              s"Unrecognized hover info elements: ${results.flatMap(_.left.toSeq).mkString(", ")}",
              c.history
            )
          else
            DecodeResult.ok(HoverInfo(results.flatMap(_.toSeq).toIndexedSeq: _*))
      }
    }


  implicit def defaultJsonProductCodecFor[T]: JsonProductCodecFor[T] =
    JsonProductCodecFor(JsonProductObjCodecNoEmpty.default)

  implicit val encodeRGBA: EncodeJson[Color.RGBA] =
    EncodeJson.of[String].contramap(c => s"rgba(${c.r}, ${c.g}, ${c.b}, ${c.alpha})")

  implicit val decodeRGBA: DecodeJson[Color.RGBA] =
    DecodeJson { c =>
      c.as[String].flatMap { s =>
        if (s.startsWith("rgba(") && s.endsWith(")"))
          s.stripPrefix("rgba(").stripSuffix(")").split(',').map(_.trim) match {
            case Array(rStr, gStr, bStr, alphaStr) =>
              val res = for {
                r <- Try(rStr.toInt).toOption
                g <- Try(gStr.toInt).toOption
                b <- Try(bStr.toInt).toOption
                alpha <- Try(alphaStr.toDouble).toOption
              } yield DecodeResult.ok(Color.RGBA(r, g, b, alpha))

              res.getOrElse {
                DecodeResult.fail(s"Unrecognized RGBA color: '$s'", c.history)
              }
            case _ =>
              DecodeResult.fail(s"Unrecognized RGBA color: '$s'", c.history)
          }
        else
          DecodeResult.fail(s"Unrecognized RGBA color: '$s'", c.history)
      }
    }

  implicit val encodeStringColor: EncodeJson[Color.StringColor] =
    EncodeJson.of[String].contramap(_.color)

  implicit val decodeStringColor: DecodeJson[Color.StringColor] =
    DecodeJson {
      val underlying = DecodeJson.of[String]
      val map = Color.StringColor.colors
        .toVector
        .map(c => c -> Color.StringColor(c))
        .toMap

      c =>
        underlying(c).flatMap { s =>
          map.get(s) match {
            case None => DecodeResult.fail(s"Unrecognized color: '$s'", c.history)
            case Some(m) => DecodeResult.ok(m)
          }
        }
    }

  private val HexaColor3 = "#([0-9a-fA-F]{3})".r
  private val HexaColor6 = "#([0-9a-fA-F]{6})".r

  implicit val encodeRGB: EncodeJson[Color.RGB] =
    EncodeJson.of[String].contramap(c => s"rgb(${c.r}, ${c.g}, ${c.b})")

  implicit val decodeRGB: DecodeJson[Color.RGB] =
    DecodeJson { c =>
      val asString: DecodeResult[Color.RGB] = c.as[String].flatMap { s =>
        if (s.startsWith("rgb(") && s.endsWith(")"))
          s.stripPrefix("rgb(").stripSuffix(")").split(',').map(_.trim).map(s => Try(s.toInt).toOption) match {
            case Array(Some(r), Some(g), Some(b)) =>
              DecodeResult.ok(Color.RGB(r, g, b))
            case _ =>
              DecodeResult.fail(s"Unrecognized RGB color: '$s'", c.history)
          }
        else
          DecodeResult.fail(s"Unrecognized RGB color: '$s'", c.history)
      }
      def asInt: DecodeResult[Color.RGB] = c.as[Int].flatMap {
        case r if r >= 0 && r < 256 =>
          DecodeResult.ok(Color.RGB(r, 0, 0))
        case _ =>
          DecodeResult.fail(s"Unrecognized RGB color: ${c.focus}", c.history)
      }

      def parseHex(s: String, from: Int, until: Int) =
        new BigInteger(s.substring(from, until), 16).intValue()
      def asHexa: DecodeResult[Color.RGB] = c.as[String].flatMap {
        case HexaColor3(hex) =>
          val r = parseHex(hex, 0, 1)
          val g = parseHex(hex, 1, 2)
          val b = parseHex(hex, 2, 3)

          DecodeResult.ok(Color.RGB(r, g, b))

        case HexaColor6(hex) =>
          val r = parseHex(hex, 0, 2)
          val g = parseHex(hex, 2, 4)
          val b = parseHex(hex, 4, 6)

          DecodeResult.ok(Color.RGB(r, g, b))

        case other =>
          DecodeResult.fail(s"Unrecognized RGB color: $other", c.history)
      }

      asString
        .toOption
        .orElse(asInt.toOption)
        .fold(asHexa)(DecodeResult.ok)
    }

  private def decodeNum(s: String) = {

    val intOpt = Try(s.toInt)
      .toOption

    val fromDouble = Try(s.toDouble)
      .toOption
      .map(_.toInt)

    def fromPct =
      if (s.endsWith("%"))
        Try(s.stripSuffix("%").trim.toDouble)
          .toOption
          .map(v => (256 * v).toInt)
      else
        None

    intOpt
      .orElse(fromDouble)
      .orElse(fromPct)
  }

  implicit val encodeHSL: EncodeJson[Color.HSL] =
    EncodeJson.of[String].contramap(c => s"hsl(${c.h}, ${c.s}, ${c.l})")

  implicit val decodeHSL: DecodeJson[Color.HSL] =
    DecodeJson { c =>
      c.as[String].flatMap { s =>
        if (s.startsWith("hsl(") && s.endsWith(")"))
          s.stripPrefix("hsl(").stripSuffix(")").split(',').map(_.trim).map(decodeNum) match {
            case Array(Some(h), Some(s), Some(l)) =>
              DecodeResult.ok(Color.HSL(h, s, l))
            case _ =>
              DecodeResult.fail(s"Unrecognized HSL color: '$s'", c.history)
          }
        else
          DecodeResult.fail(s"Unrecognized HSL color: '$s'", c.history)
      }
    }

  implicit val encodeNamedColorScale: EncodeJson[ColorScale.NamedScale] =
    EncodeJson.of[String].contramap(_.name)

  implicit val decodeNamedColorScale: DecodeJson[ColorScale.NamedScale] =
    DecodeJson { c =>
      c.as[String].flatMap { s =>
        // TODO: Add colorscale name enum?
        DecodeResult.ok(ColorScale.NamedScale(s))
      }
    }

  implicit val encodeCustomColorScale: EncodeJson[ColorScale.CustomScale] =
    EncodeJson.of[Json].contramap(_.values.toList.asJson)

  implicit val decodeCustomColorScale: DecodeJson[ColorScale.CustomScale] =
    DecodeJson { c =>
      c.as[Seq[(Double, Color)]].flatMap { s =>
        DecodeResult.ok(ColorScale.CustomScale(s))
      }
    }

  implicit val colorscaleJsonCodec: JsonSumCodecFor[ColorScale] =
    JsonSumCodecFor(jsonSumDirectCodecFor("colorscale"))

  implicit val elementJsonCodec: JsonSumCodecFor[Element] =
    JsonSumCodecFor(jsonSumDirectCodecFor("element"))

  implicit val sequenceJsonCodec: JsonSumCodecFor[Sequence] =
    JsonSumCodecFor(jsonSumDirectCodecFor("sequence"))

  implicit val rangeJsonCodec: JsonSumCodecFor[Range] =
    JsonSumCodecFor(jsonSumDirectCodecFor("range"))

  implicit val boxPointsJsonCodec: JsonSumCodecFor[BoxPoints] =
    JsonSumCodecFor(jsonSumDirectCodecFor("box points"))

  implicit val boxMeanJsonCodec: JsonSumCodecFor[BoxMean] =
    JsonSumCodecFor(jsonSumDirectCodecFor("box mean"))

  implicit def oneOrSeqJsonCodec[T]: JsonSumCodecFor[OneOrSeq[T]] =
    JsonSumCodecFor(jsonSumDirectCodecFor("one or sequence"))

  implicit val encodeScatterMode: EncodeJson[ScatterMode] =
    flagEncoder[ScatterMode, ScatterMode.Flag](_.flags, _.label)

  implicit val decodeScatterMode: DecodeJson[ScatterMode] =
    flagDecoder[ScatterMode, ScatterMode.Flag]("scatter mode", ScatterMode.flagMap, ScatterMode(_))

  implicit val encodeLocalDateTime: EncodeJson[LocalDateTime] =
    EncodeJson { dt =>
      dt.toString.asJson
    }

  implicit val decodeLocalDateTime: DecodeJson[LocalDateTime] =
    DecodeJson { c =>
      c.as[String].flatMap { s =>
        LocalDateTime.parse(s) match {
          case Some(dt) =>
            DecodeResult.ok(dt)
          case None =>
            DecodeResult.fail(
              s"Malformed date-time: '$s'",
              c.history
            )
        }
      }
    }

  implicit val encodeError: EncodeJson[Error] =
    EncodeJson { error =>
      val json = error match {
        case data: Error.Data => data.asJson
        case pct: Error.Percent => pct.asJson
        case cst: Error.Constant => cst.asJson
      }

        json.obj.fold(json)(o => Json.jObject(("type" -> error.`type`.asJson) +: o))
    }

  implicit val decodeError: DecodeJson[Error] =
    DecodeJson { c =>
      c.downField("type").success match {
        case None =>
          DecodeResult.fail("No type found", c.history)
        case Some(c1) =>
          val c0 = c1.delete
          c1.focus.as[String].flatMap {
            case "data" =>
              c0.as[Error.Data].map(e => e: Error)
            case "percent" =>
              c0.as[Error.Percent].map(e => e: Error)
            case "constant" =>
              c0.as[Error.Constant].map(e => e: Error)
            case unrecognized =>
              DecodeResult.fail(s"Unrecognized type: $unrecognized", c.history)
          }
      }
    }

  implicit val jsonSumCodecForColor: JsonSumCodecFor[Color] =
    JsonSumCodecFor(jsonSumDirectCodecFor("color"))


  case class WrappedFont(font: Font)
  val derivedFontDecoder = MkDecodeJson[Font].decodeJson
  lazy val wrappedFontDecoder = DecodeJson.of[WrappedFont].map(_.font)

  implicit lazy val decodeFont: DecodeJson[Font] =
    DecodeJson {
      c =>
        wrappedFontDecoder(c).toOption.fold(derivedFontDecoder(c))(DecodeResult.ok)
    }

  implicit val jsonCodecForTrace = JsonSumCodecFor[Trace](
    new JsonSumTypeFieldCodec {
      override def toTypeValue(name: String) = name.toLowerCase

      override def decodeField[A](name: String, cursor: HCursor, decode: DecodeJson[A]) = {
        val c = cursor.downField(typeField)

        c.as[String].toEither match {
          case Right(name0) if toTypeValue(name) == name0 =>
            c.delete.as(decode).map(Right(_))
          case Left(_) if name == "Scatter" => // assume scatter if no type found
            cursor.as(decode).map(Right(_))
          case _ =>
            DecodeResult.ok(Left(ACursor.ok(cursor)))
        }
      }
    }
  )
}
