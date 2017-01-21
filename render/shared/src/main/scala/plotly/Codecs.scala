package plotly

import java.math.BigInteger
import java.time.LocalDateTime

import io.circe.{Error => _, _}
import io.circe.simplegeneric._
import io.circe.simplegeneric.derive._
import io.circe.syntax._
import shapeless._

import scala.util.Try
import plotly.element._
import plotly.layout._
import DateTimeFormats.`yyyy-MM-dd HH:mm:ss`
import java.time.format.DateTimeParseException

object Codecs {

  object Internals {

    sealed abstract class IsWrapper[W]

    implicit def isWrapperEncode[W, L <: HList, T]
     (implicit
       ev: IsWrapper[W],
       gen: Generic.Aux[W, L],
       isHCons: ops.hlist.IsHCons.Aux[L, T, HNil],
       underlying: Encoder[T]
     ): Encoder[W] =
      Encoder.instance { w =>
        val t = isHCons.head(gen.to(w))
        t.asJson
      }

    implicit def isWrapperDecode[W, L <: HList, T]
     (implicit
       ev: IsWrapper[W],
       gen: Generic.Aux[W, L],
       isHCons: ops.hlist.IsHCons.Aux[L, T, HNil],
       underlying: Decoder[T]
     ): Decoder[W] =
      Decoder.instance { c =>
        c.as[T].right.map(t =>
          gen.from((t :: HNil).asInstanceOf[L]) // FIXME
        )
      }

    implicit val boxMeanBoolIsWrapper: IsWrapper[BoxMean.Bool] = null
    implicit val boxPointsBoolIsWrapper: IsWrapper[BoxPoints.Bool] = null
    implicit val sequenceDoublesIsWrapper: IsWrapper[Sequence.Doubles] = null
    implicit val sequenceStringsIsWrapper: IsWrapper[Sequence.Strings] = null
    implicit val sequenceDatetimesIsWrapper: IsWrapper[Sequence.DateTimes] = null
    implicit val doubleElementIsWrapper: IsWrapper[Element.DoubleElement] = null
    implicit val stringElementIsWrapper: IsWrapper[Element.StringElement] = null
    implicit def oneOrSeqOneIsWrapper[T]: IsWrapper[OneOrSeq.One[T]] = null
    implicit def oneOrSeqSequenceIsWrapper[T]: IsWrapper[OneOrSeq.Sequence[T]] = null

    def flagEncoder[T, F](flags: T => Set[F], label: F => String): Encoder[T] =
      Encoder.instance { t =>
        val s = flags(t).toSeq match {
          case Seq() => "none"
          case nonEmpty => nonEmpty.map(label).mkString("+")
        }

        s.asJson
      }

    def flagDecoder[T, F](type0: String, map: Map[String, F], build: Set[F] => T): Decoder[T] =
      Decoder.instance { c =>
        c.as[String].right.flatMap { s =>
          val flags =
            if (s == "none")
              Right(Set.empty[F])
            else
              s.split('+').foldLeft[Decoder.Result[Set[F]]](Right(Set.empty[F])) {
                case (acc, f) =>
                  for {
                    acc0 <- acc.right
                    f0 <- map.get(f).fold[Decoder.Result[F]](Left(DecodingFailure(s"Unrecognized $type0: $f", c.history)))(Right(_)).right
                  } yield acc0 + f0
              }

          flags.right.map(build)
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

    implicit def isEnumEncoder[T: IsEnum]: Encoder[T] =
      Encoder[String].contramap(IsEnum[T].label)

    implicit def isEnumDecoder[T]
     (implicit
       isEnum: IsEnum[T],
       enum: Enumerate[T],
       typeable: Typeable[T]
     ): Decoder[T] =
      Decoder.instance {
        val underlying = Decoder[String]
        val map = enum().map(e => isEnum.label(e) -> e).toMap
        val name = typeable.describe // TODO split in words

        c =>
          underlying(c).right.flatMap { s =>
            map.get(s) match {
              case None => Left(DecodingFailure(s"Unrecognized $name: '$s'", c.history))
              case Some(m) => Right(m)
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
    implicit val sideIsEnum = IsEnum.instance[Side](_.label)
    implicit val symbolIsEnum = IsEnum.instance[Symbol](_.label)
    implicit val ticksIsEnum = IsEnum.instance[Ticks](_.label)
    implicit val histNormIsEnum = IsEnum.instance[HistNorm](_.label)
    implicit val sizeModeIsEnum = IsEnum.instance[SizeMode](_.label)

    def jsonSumDirectCodecFor(name: String): JsonSumCodec = new JsonSumCodec {
      def encodeEmpty: Nothing =
        throw new IllegalArgumentException(s"empty $name")

      def encodeField(fieldOrObj: Either[Json, (String, Json)]): Json =
        fieldOrObj match {
          case Left(other) => other
          case Right((_, content)) => content
        }

      def decodeEmpty(cursor: HCursor): Decoder.Result[Nothing] =
      // FIXME Sometimes reports the wrong error (in case of two nested sum types)
        Left(DecodingFailure(s"unrecognized $name", cursor.history))

      def decodeField[A](name: String, cursor: HCursor, decode: Decoder[A]): Decoder.Result[Either[ACursor, A]] =
        Right {
          val o = decode(cursor)
          o.right.toOption
            .toRight(cursor)
        }
    }

    case class JsonProductObjCodecNoEmpty(
      toJsonName: String => String = identity
    ) extends JsonProductCodec {

      private val underlying = JsonProductCodec.adapt(toJsonName)

      val encodeEmpty: Json = underlying.encodeEmpty

      def encodeField(field: (String, Json), obj: Json, default: => Option[Json]): Json =
        underlying.encodeField(field, obj, default)

      def decodeEmpty(cursor: HCursor): Decoder.Result[Unit] =
        if (cursor.focus == Json.obj())
          Right(())
        else
          Left(DecodingFailure(
            s"Found extra fields: ${cursor.fields.toSeq.flatten.mkString(", ")}",
            cursor.history
          ))

      def decodeField[A](name: String, cursor: HCursor, decode: Decoder[A], default: Option[A]): Decoder.Result[(A, ACursor)] = {
        val c = cursor.downField(toJsonName(name))

        def result = c.as(decode).right.map((_, if (c.succeeded) c.delete else cursor))

        default match {
          case None => result
          case Some(d) =>
            if (c.succeeded)
              result
            else
              Right((d, cursor))
        }
      }
    }

    object JsonProductObjCodecNoEmpty {
      val default = JsonProductObjCodecNoEmpty()
    }


    implicit def defaultJsonProductCodecFor[T]: JsonProductCodecFor[T] =
      JsonProductCodecFor(JsonProductObjCodecNoEmpty.default)

    implicit val encodeRGBA: Encoder[Color.RGBA] =
      Encoder[String].contramap(c => s"rgba(${c.r}, ${c.g}, ${c.b}, ${c.alpha})")

    implicit val decodeRGBA: Decoder[Color.RGBA] =
      Decoder.instance { c =>
        c.as[String].right.flatMap { s =>
          if (s.startsWith("rgba(") && s.endsWith(")"))
            s.stripPrefix("rgba(").stripSuffix(")").split(',').map(_.trim) match {
              case Array(rStr, gStr, bStr, alphaStr) =>
                val res = for {
                  r <- Try(rStr.toInt).toOption
                  g <- Try(gStr.toInt).toOption
                  b <- Try(bStr.toInt).toOption
                  alpha <- Try(alphaStr.toDouble).toOption
                } yield Right(Color.RGBA(r, g, b, alpha))

                res.getOrElse {
                  Left(DecodingFailure(s"Unrecognized RGBA color: '$s'", c.history))
                }
              case _ =>
                Left(DecodingFailure(s"Unrecognized RGBA color: '$s'", c.history))
            }
          else
            Left(DecodingFailure(s"Unrecognized RGBA color: '$s'", c.history))
        }
      }

    implicit val encodeStringColor: Encoder[Color.StringColor] =
      Encoder[String].contramap(_.color)

    implicit val decodeStringColor: Decoder[Color.StringColor] =
      Decoder.instance {
        val underlying = Decoder[String]
        val map = Color.StringColor.colors
          .toVector
          .map(c => c -> Color.StringColor(c))
          .toMap

        c =>
          underlying(c).right.flatMap { s =>
            map.get(s) match {
              case None => Left(DecodingFailure(s"Unrecognized color: '$s'", c.history))
              case Some(m) => Right(m)
            }
          }
      }

    private val HexaColor3 = "#([0-9a-fA-F]{3})".r
    private val HexaColor6 = "#([0-9a-fA-F]{6})".r

    implicit val encodeRGB: Encoder[Color.RGB] =
      Encoder[String].contramap(c => s"rgb(${c.r}, ${c.g}, ${c.b})")

    implicit val decodeRGB: Decoder[Color.RGB] =
      Decoder.instance { c =>
        val asString: Decoder.Result[Color.RGB] = c.as[String].right.flatMap { s =>
          if (s.startsWith("rgb(") && s.endsWith(")"))
            s.stripPrefix("rgb(").stripSuffix(")").split(',').map(_.trim).map(s => Try(s.toInt).toOption) match {
              case Array(Some(r), Some(g), Some(b)) =>
                Right(Color.RGB(r, g, b))
              case _ =>
                Left(DecodingFailure(s"Unrecognized RGB color: '$s'", c.history))
            }
          else
            Left(DecodingFailure(s"Unrecognized RGB color: '$s'", c.history))
        }
        def asInt: Decoder.Result[Color.RGB] = c.as[Int].right.flatMap {
          case r if r >= 0 && r < 256 =>
            Right(Color.RGB(r, 0, 0))
          case _ =>
            Left(DecodingFailure(s"Unrecognized RGB color: ${c.focus}", c.history))
        }

        def parseHex(s: String, from: Int, until: Int) =
          new BigInteger(s.substring(from, until), 16).intValue()
        def asHexa: Decoder.Result[Color.RGB] = c.as[String].right.flatMap {
          case HexaColor3(hex) =>
            val r = parseHex(hex, 0, 1)
            val g = parseHex(hex, 1, 2)
            val b = parseHex(hex, 2, 3)

            Right(Color.RGB(r, g, b))

          case HexaColor6(hex) =>
            val r = parseHex(hex, 0, 2)
            val g = parseHex(hex, 2, 4)
            val b = parseHex(hex, 4, 6)

            Right(Color.RGB(r, g, b))

          case other =>
            Left(DecodingFailure(s"Unrecognized RGB color: $other", c.history))
        }

        asString
          .right
          .toOption
          .orElse(asInt.right.toOption)
          .map(Right(_))
          .getOrElse(asHexa)
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

    implicit val encodeHSL: Encoder[Color.HSL] =
      Encoder[String].contramap(c => s"hsl(${c.h}, ${c.s}, ${c.l})")

    implicit val decodeHSL: Decoder[Color.HSL] =
      Decoder.instance { c =>
        c.as[String].right.flatMap { s =>
          if (s.startsWith("hsl(") && s.endsWith(")"))
            s.stripPrefix("hsl(").stripSuffix(")").split(',').map(_.trim).map(decodeNum) match {
              case Array(Some(h), Some(s), Some(l)) =>
                Right(Color.HSL(h, s, l))
              case _ =>
                Left(DecodingFailure(s"Unrecognized HSL color: '$s'", c.history))
            }
          else
            Left(DecodingFailure(s"Unrecognized HSL color: '$s'", c.history))
        }
      }

    implicit val elementJsonCodec: JsonSumCodecFor[Element] =
      JsonSumCodecFor(jsonSumDirectCodecFor("element"))

    implicit val sequenceJsonCodec: JsonSumCodecFor[Sequence] =
      JsonSumCodecFor(jsonSumDirectCodecFor("sequence"))

    implicit val boxPointsJsonCodec: JsonSumCodecFor[BoxPoints] =
      JsonSumCodecFor(jsonSumDirectCodecFor("box points"))

    implicit val boxMeanJsonCodec: JsonSumCodecFor[BoxMean] =
      JsonSumCodecFor(jsonSumDirectCodecFor("box mean"))

    implicit def oneOrSeqJsonCodec[T]: JsonSumCodecFor[OneOrSeq[T]] =
      JsonSumCodecFor(jsonSumDirectCodecFor("one or sequence"))

    implicit val encodeScatterMode: Encoder[ScatterMode] =
      flagEncoder[ScatterMode, ScatterMode.Flag](_.flags, _.label)

    implicit val decodeScatterMode: Decoder[ScatterMode] =
      flagDecoder[ScatterMode, ScatterMode.Flag]("scatter mode", ScatterMode.flagMap, ScatterMode(_))

    implicit val encodeLocalDateTime: Encoder[LocalDateTime] =
      Encoder.instance { dt =>
        dt.format(`yyyy-MM-dd HH:mm:ss`).asJson
      }

    implicit val decodeLocalDateTime: Decoder[LocalDateTime] =
      Decoder.instance { c =>
        c.as[String].right.flatMap { s =>
          try {
            Right(LocalDateTime.parse(s, `yyyy-MM-dd HH:mm:ss`))
          } catch {
            case e: DateTimeParseException => Left(DecodingFailure(
              s"$e: $s",
              c.history
            ))
          }
        }
      }

    implicit val encodeError: Encoder[Error] =
      Encoder.instance { error =>
        val json = error match {
          case data: Error.Data => data.asJson
          case pct: Error.Percent => pct.asJson
          case cst: Error.Constant => cst.asJson
        }

        json.mapObject(("type" -> error.`type`.asJson) +: _)
      }

    //TODO FIXME
    implicit val decodeError: Decoder[Error] = ???
    /*
      Decoder.instance { c =>
        c.downField("type").either match {
          case Left(c0) =>
            Left(DecodingFailure("No type found", c0.history))
          case Right(c1) =>
            val c0 = c1.delete
            c1.focus.as[String].right.flatMap {
              case "data" =>
                c0.as[Error.Data].right.map(e => e: Error)
              case "percent" =>
                c0.as[Error.Percent].right.map(e => e: Error)
              case "constant" =>
                c0.as[Error.Constant].right.map(e => e: Error)
              case unrecognized =>
                Left(DecodingFailure(s"Unrecognized type: $unrecognized", c.history))
            }
        }
      }
    */

    implicit val jsonSumCodecForColor: JsonSumCodecFor[Color] =
      JsonSumCodecFor(jsonSumDirectCodecFor("color"))


    case class WrappedFont(font: Font)
    val derivedFontDecoder = MkDecoder[Font].decoder
    lazy val wrappedFontDecoder = Decoder[WrappedFont].map(_.font)

    implicit lazy val decodeFont: Decoder[Font] =
      Decoder.instance {
        c =>
          wrappedFontDecoder(c).right.toOption.fold(derivedFontDecoder(c))(Right(_))
      }

    implicit val jsonCodecForTrace = JsonSumCodecFor[Trace](
      new JsonSumTypeFieldCodec {
        override def toTypeValue(name: String) = name.toLowerCase

        override def decodeField[A](name: String, cursor: HCursor, decode: Decoder[A]) = {
          val c = cursor.downField(typeField)

          c.as[String] match {
            case Right(name0) if toTypeValue(name) == name0 =>
              c.delete.as(decode).right.map(Right(_))
            case Left(_) if name == "Scatter" => // assume scatter if no type found
              cursor.as(decode).right.map(Right(_))
            case _ =>
              Right(Left(cursor))
          }
        }
      }
    )
  }

  import Internals._

  implicit val encodeTrace = Encoder[Trace]
  implicit val decodeTrace = Decoder[Trace]

  implicit val encodeLayout = Encoder[Layout]
  implicit val decodeLayout = Decoder[Layout]

}
