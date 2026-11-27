package plotly
package internals

import java.math.BigInteger

import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._
import plotly.element._
import plotly.layout._

import scala.reflect.ClassTag
import scala.util.Try

object JsoniterCodecsInternals {

  // The configs passed to JsonCodecMaker.make below need to be literals, so they're repeated
  // at each call site. They all:
  // - fail on unexpected fields, so that the documentation tests check that all fields are supported,
  // - write fields having their default value, and empty collections, so that nothing is lost,
  // - require collection fields without default values to be present.

  private def readSeq[T](in: JsonReader, underlying: JsonValueCodec[T]): Seq[T] =
    if (in.isNextToken('[')) {
      if (in.isNextToken(']')) Nil
      else {
        in.rollbackToken()
        val b = Vector.newBuilder[T]
        while ({
          b += underlying.decodeValue(in, underlying.nullValue)
          in.isNextToken(',')
        }) ()
        if (!in.isCurrentToken(']'))
          in.arrayEndOrCommaError()
        b.result()
      }
    } else
      in.readNullOrTokenError(null, '[')

  private def writeSeq[T](seq: Seq[T], out: JsonWriter, underlying: JsonValueCodec[T]): Unit = {
    out.writeArrayStart()
    for (t <- seq)
      underlying.encodeValue(t, out)
    out.writeArrayEnd()
  }

  // Returns the next token, without consuming it
  private def peekToken(in: JsonReader): Byte = {
    val t = in.nextToken()
    in.rollbackToken()
    t
  }

  // Returns the first token of the first element of the upcoming array (or ']' if it's empty), without consuming
  // anything.
  // Like all the setMark / rollbackToMark calls below, this doesn't decode anything between the two calls,
  // as these calls cannot be nested.
  private def peekFirstArrayElementToken(in: JsonReader): Byte = {
    in.setMark()
    val isArray = in.isNextToken('[')
    val t       = if (isArray) in.nextToken() else 0: Byte
    in.rollbackToMark()
    if (!isArray)
      in.decodeError("expected '['")
    t
  }

  // Returns the first token of the first element of the first element of the upcoming array of arrays
  // (or ']' if one of them is empty), without consuming anything
  private def peekFirstNestedArrayElementToken(in: JsonReader): Byte = {
    in.setMark()
    val isNestedArray = in.isNextToken('[') && in.isNextToken('[')
    val t             = if (isNestedArray) in.nextToken() else 0: Byte
    in.rollbackToMark()
    if (!isNestedArray)
      in.decodeError("expected '[['")
    t
  }

  private def stringCodec[T](encode: T => String)(decode: (String, JsonReader) => T): JsonValueCodec[T] =
    new JsonValueCodec[T] {
      def decodeValue(in: JsonReader, default: T): T = {
        val s = in.readString(null)
        if (s == null) in.decodeError("expected string")
        decode(s, in)
      }
      def encodeValue(x: T, out: JsonWriter): Unit =
        out.writeVal(encode(x))
      def nullValue: T = null.asInstanceOf[T]
    }

  private val stringValueCodec: JsonValueCodec[String] =
    new JsonValueCodec[String] {
      def decodeValue(in: JsonReader, default: String): String = in.readString(default)
      def encodeValue(x: String, out: JsonWriter): Unit        = out.writeVal(x)
      def nullValue: String                                    = null
    }

  private val intValueCodec: JsonValueCodec[Int] =
    new JsonValueCodec[Int] {
      def decodeValue(in: JsonReader, default: Int): Int = in.readInt()
      def encodeValue(x: Int, out: JsonWriter): Unit     = out.writeVal(x)
      def nullValue: Int                                 = 0
    }

  // JSON has no representation for NaN and infinite values: write them as null, and read null as NaN
  implicit val doubleCodec: JsonValueCodec[Double] =
    new JsonValueCodec[Double] {
      def decodeValue(in: JsonReader, default: Double): Double =
        if (in.isNextToken('n')) in.readNullOrError(Double.NaN, "expected number or null")
        else {
          in.rollbackToken()
          in.readDouble()
        }
      def encodeValue(x: Double, out: JsonWriter): Unit =
        if (x.isNaN || x.isInfinite) out.writeNull()
        else out.writeVal(x)
      def nullValue: Double = Double.NaN
    }

  private def flagCodec[T, F](
      type0: String,
      flags: T => Set[F],
      label: F => String,
      map: Map[String, F],
      build: Set[F] => T
  ): JsonValueCodec[T] =
    stringCodec[T] { t =>
      flags(t).toSeq match {
        case Seq()    => "none"
        case nonEmpty => nonEmpty.map(label).mkString("+")
      }
    } { (s, in) =>
      if (s == "none") build(Set.empty)
      else
        build(
          s.split('+')
            .iterator
            .map(f => map.getOrElse(f, in.decodeError(s"Unrecognized $type0: $f")))
            .toSet
        )
    }

  private def enumCodec[T](label: T => String)(implicit enumerate: Enumerate[T], ct: ClassTag[T]): JsonValueCodec[T] = {
    lazy val map  = enumerate().map(e => label(e) -> e).toMap
    lazy val name = ct.runtimeClass.getName.split('.').last.split('$').filter(_.nonEmpty).mkString(".")
    stringCodec(label)((s, in) => map.getOrElse(s, in.decodeError(s"Unrecognized $name: '$s'")))
  }

  implicit val anchorCodec: JsonValueCodec[Anchor]                     = enumCodec[Anchor](_.label)
  implicit val refCodec: JsonValueCodec[Ref]                           = enumCodec[Ref](_.label)
  implicit val axisAnchorCodec: JsonValueCodec[AxisAnchor]             = enumCodec[AxisAnchor](_.label)
  implicit val axisReferenceCodec: JsonValueCodec[AxisReference]       = enumCodec[AxisReference](_.label)
  implicit val axisTypeCodec: JsonValueCodec[AxisType]                 = enumCodec[AxisType](_.label)
  implicit val barModeCodec: JsonValueCodec[BarMode]                   = enumCodec[BarMode](_.label)
  implicit val boxModeCodec: JsonValueCodec[BoxMode]                   = enumCodec[BoxMode](_.label)
  implicit val dashCodec: JsonValueCodec[Dash]                         = enumCodec[Dash](_.label)
  implicit val fillCodec: JsonValueCodec[Fill]                         = enumCodec[Fill](_.label)
  implicit val hoverModeCodec: JsonValueCodec[HoverMode]               = enumCodec[HoverMode](_.label)
  implicit val lineShapeCodec: JsonValueCodec[LineShape]               = enumCodec[LineShape](_.label)
  implicit val orientationCodec: JsonValueCodec[Orientation]           = enumCodec[Orientation](_.label)
  implicit val traceOrderCodec: JsonValueCodec[TraceOrder]             = enumCodec[TraceOrder](_.label)
  implicit val textPositionCodec: JsonValueCodec[TextPosition]         = enumCodec[TextPosition](_.label)
  implicit val barTextPositionCodec: JsonValueCodec[BarTextPosition]   = enumCodec[BarTextPosition](_.label)
  implicit val sideCodec: JsonValueCodec[Side]                         = enumCodec[Side](_.label)
  implicit val symbolCodec: JsonValueCodec[Symbol]                     = enumCodec[Symbol](_.label)
  implicit val ticksCodec: JsonValueCodec[Ticks]                       = enumCodec[Ticks](_.label)
  implicit val histNormCodec: JsonValueCodec[HistNorm]                 = enumCodec[HistNorm](_.label)
  implicit val sizeModeCodec: JsonValueCodec[SizeMode]                 = enumCodec[SizeMode](_.label)
  implicit val hoverOnCodec: JsonValueCodec[HoverOn]                   = enumCodec[HoverOn](_.label)
  implicit val groupNormCodec: JsonValueCodec[GroupNorm]               = enumCodec[GroupNorm](_.label)
  implicit val histFuncCodec: JsonValueCodec[HistFunc]                 = enumCodec[HistFunc](_.label)
  implicit val tickModeCodec: JsonValueCodec[TickMode]                 = enumCodec[TickMode](_.mode)
  implicit val patternCodec: JsonValueCodec[Pattern]                   = enumCodec[Pattern](_.label)
  implicit val rowOrderCodec: JsonValueCodec[RowOrder]                 = enumCodec[RowOrder](_.label)
  implicit val alignmentCodec: JsonValueCodec[Alignment]               = enumCodec[Alignment](_.label)
  implicit val colorModelCodec: JsonValueCodec[ColorModel]             = enumCodec[ColorModel](_.label)
  implicit val sizeUnitCodec: JsonValueCodec[SizeUnit]                 = enumCodec[SizeUnit](_.label)
  implicit val contourColoringCodec: JsonValueCodec[ContourColoring]   = enumCodec[ContourColoring](_.label)
  implicit val violinSideCodec: JsonValueCodec[ViolinSide]             = enumCodec[ViolinSide](_.label)
  implicit val spanModeCodec: JsonValueCodec[SpanMode]                 = enumCodec[SpanMode](_.label)
  implicit val violinScaleModeCodec: JsonValueCodec[ViolinScaleMode]   = enumCodec[ViolinScaleMode](_.label)
  implicit val connectorModeCodec: JsonValueCodec[ConnectorMode]       = enumCodec[ConnectorMode](_.label)
  implicit val waterfallMeasureCodec: JsonValueCodec[WaterfallMeasure] =
    enumCodec[WaterfallMeasure](_.label)
  implicit val titlePositionCodec: JsonValueCodec[TitlePosition]         = enumCodec[TitlePosition](_.label)
  implicit val categoryOrderCodec: JsonValueCodec[CategoryOrder]         = enumCodec[CategoryOrder](_.label)
  implicit val thetaUnitCodec: JsonValueCodec[ThetaUnit]                 = enumCodec[ThetaUnit](_.label)
  implicit val polarGridShapeCodec: JsonValueCodec[PolarGridShape]       = enumCodec[PolarGridShape](_.label)
  implicit val branchValuesCodec: JsonValueCodec[BranchValues]           = enumCodec[BranchValues](_.label)
  implicit val treemapPackingCodec: JsonValueCodec[TreemapPacking]       = enumCodec[TreemapPacking](_.label)
  implicit val rotationDirectionCodec: JsonValueCodec[RotationDirection] =
    enumCodec[RotationDirection](_.label)
  implicit val insideTextOrientationCodec: JsonValueCodec[InsideTextOrientation] =
    enumCodec[InsideTextOrientation](_.label)

  implicit val scatterLayoutModeCodec: JsonValueCodec[ScatterLayoutMode] =
    enumCodec[ScatterLayoutMode](_.label)

  private val boxMeanLabeledCodec = enumCodec[BoxMean.Labeled](_.label)
  // Also reads "Outliers", that BoxPoints.Outliers used to be written as (and that some examples
  // of the plotly.js documentation use, although plotly.js ignores it)
  private val boxPointsLabeledCodec = {
    stringCodec[BoxPoints.Labeled](_.label) { (s, in) =>
      Enumerate[BoxPoints.Labeled]
        .apply()
        .find(_.label == s)
        .orElse(Some(BoxPoints.Outliers).filter(_ => s == "Outliers"))
        .getOrElse(in.decodeError(s"Unrecognized BoxPoints.Labeled: '$s'"))
    }
  }

  implicit val boxMeanCodec: JsonValueCodec[BoxMean] =
    new JsonValueCodec[BoxMean] {
      def decodeValue(in: JsonReader, default: BoxMean): BoxMean =
        peekToken(in) match {
          case 't' | 'f' => BoxMean.Bool(in.readBoolean())
          case _         => boxMeanLabeledCodec.decodeValue(in, null)
        }
      def encodeValue(x: BoxMean, out: JsonWriter): Unit =
        x match {
          case b: BoxMean.Bool    => out.writeVal(b.value)
          case l: BoxMean.Labeled => boxMeanLabeledCodec.encodeValue(l, out)
        }
      def nullValue: BoxMean = null
    }

  private val zSmoothLabeledCodec = enumCodec[ZSmooth.Labeled](_.label)

  implicit val zSmoothCodec: JsonValueCodec[ZSmooth] =
    new JsonValueCodec[ZSmooth] {
      def decodeValue(in: JsonReader, default: ZSmooth): ZSmooth =
        if (peekToken(in) == 'f') {
          in.readBoolean()
          ZSmooth.False
        } else
          zSmoothLabeledCodec.decodeValue(in, null)
      def encodeValue(x: ZSmooth, out: JsonWriter): Unit =
        x match {
          case ZSmooth.False      => out.writeVal(false)
          case l: ZSmooth.Labeled => zSmoothLabeledCodec.encodeValue(l, out)
        }
      def nullValue: ZSmooth = null
    }

  implicit val boxPointsCodec: JsonValueCodec[BoxPoints] =
    new JsonValueCodec[BoxPoints] {
      def decodeValue(in: JsonReader, default: BoxPoints): BoxPoints =
        peekToken(in) match {
          case 't' | 'f' => BoxPoints.Bool(in.readBoolean())
          case _         => boxPointsLabeledCodec.decodeValue(in, null)
        }
      def encodeValue(x: BoxPoints, out: JsonWriter): Unit =
        x match {
          case b: BoxPoints.Bool    => out.writeVal(b.value)
          case l: BoxPoints.Labeled => boxPointsLabeledCodec.encodeValue(l, out)
        }
      def nullValue: BoxPoints = null
    }

  implicit val hoverInfoCodec: JsonValueCodec[HoverInfo] =
    stringCodec[HoverInfo](_.label) {
      case ("all", _)        => HoverInfo.All
      case ("skip", _)       => HoverInfo.Skip
      case ("none", _)       => HoverInfo.None
      case (combination, in) =>
        val results = combination.split('+').map { elem =>
          HoverInfo.elementMap.get(elem).toRight(elem)
        }
        if (results.exists(_.isLeft))
          in.decodeError(s"Unrecognized hover info elements: ${results.flatMap(_.left.toSeq).mkString(", ")}")
        else
          HoverInfo(results.flatMap(_.toSeq).toIndexedSeq: _*)
    }

  implicit val scatterModeCodec: JsonValueCodec[ScatterMode] =
    flagCodec[ScatterMode, ScatterMode.Flag]("scatter mode", _.flags, _.label, ScatterMode.flagMap, ScatterMode(_))

  implicit val textInfoCodec: JsonValueCodec[TextInfo] =
    flagCodec[TextInfo, TextInfo.Flag]("text info", _.flags, _.label, TextInfo.flagMap, TextInfo(_))

  implicit val localDateTimeCodec: JsonValueCodec[LocalDateTime] =
    stringCodec[LocalDateTime](_.toString) { (s, in) =>
      LocalDateTime.parse(s).getOrElse(in.decodeError(s"Malformed date-time: '$s'"))
    }

  private def parseRGBA(s: String): Option[Color.RGBA] =
    if (s.startsWith("rgba(") && s.endsWith(")"))
      s.stripPrefix("rgba(").stripSuffix(")").split(',').map(_.trim) match {
        case Array(rStr, gStr, bStr, alphaStr) =>
          for {
            r     <- Try(rStr.toInt).toOption
            g     <- Try(gStr.toInt).toOption
            b     <- Try(bStr.toInt).toOption
            alpha <- Try(alphaStr.toDouble).toOption
          } yield Color.RGBA(r, g, b, alpha)
        case _ => None
      }
    else
      None

  // like in CSS, color names are case-insensitive
  private def parseStringColor(s: String): Option[Color.StringColor] =
    if (Color.StringColor.colors(s.toLowerCase(java.util.Locale.ROOT))) Some(Color.StringColor(s))
    else None

  private val HexaColor3 = "#([0-9a-fA-F]{3})".r
  private val HexaColor6 = "#([0-9a-fA-F]{6})".r

  private def parseRGB(s: String): Option[Color.RGB] = {
    def parseHex(s: String, from: Int, until: Int) =
      new BigInteger(s.substring(from, until), 16).intValue()
    s match {
      case _ if s.startsWith("rgb(") && s.endsWith(")") =>
        s.stripPrefix("rgb(").stripSuffix(")").split(',').map(_.trim).map(s => Try(s.toInt).toOption) match {
          case Array(Some(r), Some(g), Some(b)) => Some(Color.RGB(r, g, b))
          case _                                => None
        }
      case HexaColor3(hex) =>
        Some(Color.RGB(parseHex(hex, 0, 1), parseHex(hex, 1, 2), parseHex(hex, 2, 3)))
      case HexaColor6(hex) =>
        Some(Color.RGB(parseHex(hex, 0, 2), parseHex(hex, 2, 4), parseHex(hex, 4, 6)))
      case _ =>
        None
    }
  }

  // Accepts numbers, like 40, and percentages, like 40%, returning 40 for both
  private def decodeNum(s: String) =
    Try(s.stripSuffix("%").trim.toDouble).toOption.map(_.toInt)

  // Accepts the saturation and lightness as percentages (hsl(120, 50%, 40%), the CSS syntax) or as
  // numbers (hsl(120, 50, 40), that plotly.js accepted before plotly.js 4)
  private def parseHSL(s: String): Option[Color.HSL] =
    if (s.startsWith("hsl(") && s.endsWith(")"))
      s.stripPrefix("hsl(").stripSuffix(")").split(',').map(_.trim).map(decodeNum) match {
        case Array(Some(h), Some(s), Some(l)) => Some(Color.HSL(h, s, l))
        case _                                => None
      }
    else
      None

  implicit val colorCodec: JsonValueCodec[Color] =
    new JsonValueCodec[Color] {
      def decodeValue(in: JsonReader, default: Color): Color =
        if (peekToken(in) == '"') {
          val s = in.readString(null)
          parseHSL(s)
            .orElse(parseRGB(s))
            .orElse(parseRGBA(s))
            .orElse(parseStringColor(s))
            .getOrElse(in.decodeError(s"Unrecognized color: '$s'"))
        } else
          // numbers are mapped to colors by color scales
          Color.Number(in.readDouble())
      // plotly.js >= 4 rejects the colors that aren't valid CSS colors, like HSL ones without
      // percentages
      def encodeValue(x: Color, out: JsonWriter): Unit =
        x match {
          case c: Color.RGBA        => out.writeVal(s"rgba(${c.r}, ${c.g}, ${c.b}, ${c.alpha})")
          case c: Color.StringColor => out.writeVal(c.color)
          case c: Color.RGB         => out.writeVal(s"rgb(${c.r}, ${c.g}, ${c.b})")
          case c: Color.HSL         => out.writeVal(s"hsl(${c.h}, ${c.s}%, ${c.l}%)")
          case c: Color.Number      => doubleCodec.encodeValue(c.value, out)
        }
      def nullValue: Color = null
    }

  // The levels of the color scales are numbers, that plotly.js also accepts as strings, like "0.5"
  private val colorScaleLevelCodec: JsonValueCodec[Double] =
    new JsonValueCodec[Double] {
      def decodeValue(in: JsonReader, default: Double): Double =
        if (peekToken(in) == '"') {
          val s = in.readString(null)
          Try(s.trim.toDouble).getOrElse(in.decodeError(s"Invalid color scale level: '$s'"))
        } else
          in.readDouble()
      def encodeValue(x: Double, out: JsonWriter): Unit = out.writeVal(x)
      def nullValue: Double                             = 0.0
    }

  private val colorScaleEntryCodec: JsonValueCodec[(Double, Color)] =
    new JsonValueCodec[(Double, Color)] {
      def decodeValue(in: JsonReader, default: (Double, Color)): (Double, Color) =
        if (in.isNextToken('[')) {
          val level = colorScaleLevelCodec.decodeValue(in, 0.0)
          if (!in.isNextToken(',')) in.commaError()
          val color = colorCodec.decodeValue(in, null)
          if (!in.isNextToken(']')) in.arrayEndError()
          (level, color)
        } else
          in.decodeError("expected '['")
      def encodeValue(x: (Double, Color), out: JsonWriter): Unit = {
        out.writeArrayStart()
        colorScaleLevelCodec.encodeValue(x._1, out)
        colorCodec.encodeValue(x._2, out)
        out.writeArrayEnd()
      }
      def nullValue: (Double, Color) = null
    }

  private val customColorScaleValuesCodec: JsonValueCodec[Seq[(Double, Color)]] =
    new JsonValueCodec[Seq[(Double, Color)]] {
      def decodeValue(in: JsonReader, default: Seq[(Double, Color)]): Seq[(Double, Color)] =
        readSeq(in, colorScaleEntryCodec)
      def encodeValue(x: Seq[(Double, Color)], out: JsonWriter): Unit =
        writeSeq(x, out, colorScaleEntryCodec)
      def nullValue: Seq[(Double, Color)] = null
    }

  implicit val colorScaleCodec: JsonValueCodec[ColorScale] =
    new JsonValueCodec[ColorScale] {
      def decodeValue(in: JsonReader, default: ColorScale): ColorScale =
        if (peekToken(in) == '"')
          // TODO: Add colorscale name enum?
          ColorScale.NamedScale(in.readString(null))
        else
          ColorScale.CustomScale(customColorScaleValuesCodec.decodeValue(in, null))
      def encodeValue(x: ColorScale, out: JsonWriter): Unit =
        x match {
          case s: ColorScale.NamedScale  => out.writeVal(s.name)
          case s: ColorScale.CustomScale => customColorScaleValuesCodec.encodeValue(s.values, out)
        }
      def nullValue: ColorScale = null
    }

  implicit val elementCodec: JsonValueCodec[Element] =
    new JsonValueCodec[Element] {
      def decodeValue(in: JsonReader, default: Element): Element =
        if (peekToken(in) == '"') Element.StringElement(in.readString(null))
        else Element.DoubleElement(doubleCodec.decodeValue(in, Double.NaN))
      def encodeValue(x: Element, out: JsonWriter): Unit =
        x match {
          case Element.DoubleElement(d) => doubleCodec.encodeValue(d, out)
          case Element.StringElement(s) => out.writeVal(s)
        }
      def nullValue: Element = null
    }

  private val nestedDoublesCodec: JsonValueCodec[Seq[Seq[Double]]] = JsonCodecMaker.make
  private val nestedIntsCodec: JsonValueCodec[Seq[Seq[Int]]]       = JsonCodecMaker.make
  private val nestedStringsCodec: JsonValueCodec[Seq[Seq[String]]] = JsonCodecMaker.make

  implicit val sequenceCodec: JsonValueCodec[Sequence] =
    new JsonValueCodec[Sequence] {
      def decodeValue(in: JsonReader, default: Sequence): Sequence =
        peekFirstArrayElementToken(in) match {
          case '"' =>
            val strings = readSeq(in, stringValueCodec)
            if (strings.contains(null))
              in.decodeError("Unexpected null value in string sequence")
            val dateTimes = strings.map(LocalDateTime.parse)
            if (dateTimes.forall(_.nonEmpty)) Sequence.DateTimes(dateTimes.flatten)
            else Sequence.Strings(strings)
          case '[' =>
            if (peekFirstNestedArrayElementToken(in) == '"')
              Sequence.NestedStrings(nestedStringsCodec.decodeValue(in, null))
            else
              Sequence.NestedDoubles(nestedDoublesCodec.decodeValue(in, null))
          case _ =>
            Sequence.Doubles(readSeq(in, doubleCodec))
        }
      def encodeValue(x: Sequence, out: JsonWriter): Unit =
        x match {
          case Sequence.Doubles(seq)       => writeSeq(seq, out, doubleCodec)
          case Sequence.NestedDoubles(seq) => nestedDoublesCodec.encodeValue(seq, out)
          case Sequence.NestedInts(seq)    => nestedIntsCodec.encodeValue(seq, out)
          case Sequence.Strings(seq)       => writeSeq(seq, out, stringValueCodec)
          case Sequence.DateTimes(seq)     => writeSeq(seq, out, localDateTimeCodec)
          case Sequence.NestedStrings(seq) => nestedStringsCodec.encodeValue(seq, out)
        }
      def nullValue: Sequence = null
    }

  private val doublesRangeCodec: JsonValueCodec[(Double, Double)]                 = JsonCodecMaker.make
  private val dateTimesRangeCodec: JsonValueCodec[(LocalDateTime, LocalDateTime)] = JsonCodecMaker.make

  implicit val rangeCodec: JsonValueCodec[Range] =
    new JsonValueCodec[Range] {
      def decodeValue(in: JsonReader, default: Range): Range =
        if (peekFirstArrayElementToken(in) == '"') Range.DateTimes(dateTimesRangeCodec.decodeValue(in, null))
        else Range.Doubles(doublesRangeCodec.decodeValue(in, null))
      def encodeValue(x: Range, out: JsonWriter): Unit =
        x match {
          case Range.Doubles(range)   => doublesRangeCodec.encodeValue(range, out)
          case Range.DateTimes(range) => dateTimesRangeCodec.encodeValue(range, out)
        }
      def nullValue: Range = null
    }

  private def oneOrSeqCodec[T](underlying: JsonValueCodec[T]): JsonValueCodec[OneOrSeq[T]] =
    new JsonValueCodec[OneOrSeq[T]] {
      def decodeValue(in: JsonReader, default: OneOrSeq[T]): OneOrSeq[T] =
        if (peekToken(in) == '[') OneOrSeq.Sequence(readSeq(in, underlying))
        else OneOrSeq.One(underlying.decodeValue(in, underlying.nullValue))
      def encodeValue(x: OneOrSeq[T], out: JsonWriter): Unit =
        x match {
          case OneOrSeq.One(t)        => underlying.encodeValue(t, out)
          case OneOrSeq.Sequence(seq) => writeSeq(seq, out, underlying)
        }
      def nullValue: OneOrSeq[T] = null
    }

  implicit val oneOrSeqAlignmentCodec: JsonValueCodec[OneOrSeq[Alignment]] = oneOrSeqCodec(alignmentCodec)
  implicit val oneOrSeqColorCodec: JsonValueCodec[OneOrSeq[Color]]         = oneOrSeqCodec(colorCodec)
  implicit val oneOrSeqDoubleCodec: JsonValueCodec[OneOrSeq[Double]]       = oneOrSeqCodec(doubleCodec)
  implicit val oneOrSeqIntCodec: JsonValueCodec[OneOrSeq[Int]]             = oneOrSeqCodec(intValueCodec)
  implicit val oneOrSeqStringCodec: JsonValueCodec[OneOrSeq[String]]       = oneOrSeqCodec(stringValueCodec)
  implicit val oneOrSeqSymbolCodec: JsonValueCodec[OneOrSeq[Symbol]]       = oneOrSeqCodec(symbolCodec)

  implicit val errorCodec: JsonValueCodec[Error] =
    JsonCodecMaker.make(
      CodecMakerConfig
        .withDiscriminatorFieldName(Some("type"))
        .withAdtLeafClassNameMapper(x => JsonCodecMaker.simpleClassName(x).toLowerCase)
        .withRequireDiscriminatorFirst(false)
        .withSkipUnexpectedFields(false)
        .withTransientDefault(false)
        .withTransientEmpty(false)
        .withRequireCollectionFields(true)
    )

  private final case class WrappedFont(font: Font)

  private val unwrappedFontCodec: JsonValueCodec[Font] =
    JsonCodecMaker.make(
      CodecMakerConfig
        .withSkipUnexpectedFields(false)
        .withTransientDefault(false)
        .withTransientEmpty(false)
        .withRequireCollectionFields(true)
    )
  private lazy val wrappedFontCodec: JsonValueCodec[WrappedFont] =
    JsonCodecMaker.make(
      CodecMakerConfig
        .withSkipUnexpectedFields(false)
        .withTransientDefault(false)
        .withTransientEmpty(false)
        .withRequireCollectionFields(true)
    )

  // Accepts both {"size": …, "family": …, "color": …} and {"font": {"size": …, "family": …, "color": …}}
  implicit lazy val fontCodec: JsonValueCodec[Font] =
    new JsonValueCodec[Font] {
      def decodeValue(in: JsonReader, default: Font): Font = {
        in.setMark()
        val isWrapped = in.isNextToken('{') && !in.isNextToken('}') && {
          in.rollbackToken()
          in.readKeyAsString() == "font"
        }
        in.rollbackToMark()
        if (isWrapped) wrappedFontCodec.decodeValue(in, null).font
        else unwrappedFontCodec.decodeValue(in, default)
      }
      def encodeValue(x: Font, out: JsonWriter): Unit =
        unwrappedFontCodec.encodeValue(x, out)
      def nullValue: Font = null
    }

  private val unwrappedTitleCodec: JsonValueCodec[Title] =
    JsonCodecMaker.make(
      CodecMakerConfig
        .withSkipUnexpectedFields(false)
        .withTransientDefault(false)
        .withTransientEmpty(false)
        .withRequireCollectionFields(true)
    )

  // Writes titles as objects, like {"text": …, "font": …}, as plotly.js >= 3 ignores string titles,
  // but also reads string titles, like "My plot" (plotly.js < 3 accepted those)
  implicit lazy val titleCodec: JsonValueCodec[Title] =
    new JsonValueCodec[Title] {
      def decodeValue(in: JsonReader, default: Title): Title =
        if (peekToken(in) == '"') Title.from(text = in.readString(null))
        else unwrappedTitleCodec.decodeValue(in, default)
      def encodeValue(x: Title, out: JsonWriter): Unit =
        unwrappedTitleCodec.encodeValue(x, out)
      def nullValue: Title = null
    }

  private val typedTraceCodec: JsonValueCodec[Trace] =
    JsonCodecMaker.make(
      CodecMakerConfig
        .withDiscriminatorFieldName(Some("type"))
        .withAdtLeafClassNameMapper(x => JsonCodecMaker.simpleClassName(x).toLowerCase)
        .withRequireDiscriminatorFirst(false)
        .withSkipUnexpectedFields(false)
        .withTransientDefault(false)
        .withTransientEmpty(false)
        .withRequireCollectionFields(true)
    )
  private val scatterCodec: JsonValueCodec[Scatter] =
    JsonCodecMaker.make(
      CodecMakerConfig
        .withSkipUnexpectedFields(false)
        .withTransientDefault(false)
        .withTransientEmpty(false)
        .withRequireCollectionFields(true)
    )

  // Traces without a "type" field are assumed to be scatter traces
  implicit val traceCodec: JsonValueCodec[Trace] =
    new JsonValueCodec[Trace] {
      def decodeValue(in: JsonReader, default: Trace): Trace = {
        in.setMark()
        val hasType = in.isNextToken('{') && !in.isNextToken('}') && {
          in.rollbackToken()
          in.skipToKey("type")
        }
        in.rollbackToMark()
        if (hasType) typedTraceCodec.decodeValue(in, default)
        else scatterCodec.decodeValue(in, null)
      }
      def encodeValue(x: Trace, out: JsonWriter): Unit =
        typedTraceCodec.encodeValue(x, out)
      def nullValue: Trace = null
    }

  implicit val tracesCodec: JsonValueCodec[Seq[Trace]] =
    JsonCodecMaker.make

  implicit val layoutCodec: JsonValueCodec[Layout] =
    JsonCodecMaker.make(
      CodecMakerConfig
        .withSkipUnexpectedFields(false)
        .withTransientDefault(false)
        .withTransientEmpty(false)
        .withRequireCollectionFields(true)
    )

  implicit val configCodec: JsonValueCodec[Config] =
    JsonCodecMaker.make(
      CodecMakerConfig
        .withSkipUnexpectedFields(false)
        .withTransientDefault(false)
        .withTransientEmpty(false)
        .withRequireCollectionFields(true)
    )
}
