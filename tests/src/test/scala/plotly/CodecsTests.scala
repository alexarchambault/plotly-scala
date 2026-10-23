package plotly

import com.github.plokhotnyuk.jsoniter_scala.core.{readFromString, writeToString}
import plotly.Codecs._
import plotly.element._
import plotly.layout._

import scala.util.Try

class CodecsTests extends munit.FunSuite {

  private def roundTrip(trace: Trace)(implicit loc: munit.Location): Unit = {
    val json = writeToString(trace)
    assertEquals(readFromString[Trace](json), trace)
  }

  test("Trace codec should write the trace type") {
    assertEquals(ujson.read(writeToString[Trace](Bar(Seq(1, 2), Seq(3, 4)))).obj.get("type"), Some(ujson.Str("bar")))
  }

  test("Trace codec should assume scatter traces if no type is specified") {
    assertEquals(readFromString[Trace]("""{"x": [1, 2], "y": [3, 4]}"""), Scatter(Seq(1, 2), Seq(3, 4)))
  }

  test("Trace codec should use the type field, wherever it is") {
    assertEquals(readFromString[Trace]("""{"x": [1, 2], "type": "bar", "y": [3, 4]}"""), Bar(Seq(1, 2), Seq(3, 4)))
  }

  test("Trace codec should reject unknown fields") {
    assert(Try(readFromString[Trace]("""{"x": [1, 2], "foo": 2}""")).isFailure)
    assert(Try(readFromString[Trace]("""{"type": "bar", "x": [1, 2], "y": [1, 2], "foo": 2}""")).isFailure)
    assert(Try(readFromString[Layout]("""{"xaxis": {"foo": 2}}""")).isFailure)
  }

  test("Trace codec should not write None fields") {
    assertEquals(writeToString[Trace](Scatter(Seq(1, 2))), """{"type":"scatter","y":[1.0,2.0]}""")
  }

  test("Trace codec should write NaN as null, and read null as NaN") {
    val json = writeToString[Trace](Scatter(Seq(1.0, Double.NaN, 3.0)))
    assertEquals(json, """{"type":"scatter","y":[1.0,null,3.0]}""")
    readFromString[Trace](json) match {
      case s: Scatter =>
        assertEquals(s.y.collect { case Sequence.Doubles(seq) => seq.map(_.isNaN) }, Some(Seq(false, true, false)))
      case other => fail(s"Unexpected trace: $other")
    }
  }

  test("Trace codec should round-trip the various kinds of sequences") {
    roundTrip(Scatter(Seq(1.0, 2.0), Seq("a", "b")))
    roundTrip(Scatter(Seq(LocalDateTime(2020, 1, 2, 3, 4, 5), LocalDateTime(2021, 6, 7, 8, 9, 10)), Seq(1.0, 2.0)))
    roundTrip(Heatmap().withZ(Seq(Seq(1.0, 2.0), Seq(3.0, 4.0))))
  }

  test("Trace codec should round-trip enums, flags, colors and unions") {
    roundTrip(
      Scatter(Seq(1.0, 2.0))
        .withMode(ScatterMode(ScatterMode.Markers, ScatterMode.Lines))
        .withTextposition(TextPosition.BottomRight)
        .withHoverinfo(HoverInfo(HoverInfo.X, HoverInfo.Y))
        .withText(Seq("a", "b"))
        .withFillcolor(Color.RGBA(1, 2, 3, 0.5))
        .withError_y(Error.Data(Seq(1.0, 2.0)).withVisible(true))
        .withMarker(
          Marker()
            .withSymbol(Seq[Symbol](Symbol.Circle(true, false), Symbol.Square(false, true)))
            .withColor(Seq[Color](Color.RGB(1, 2, 3), Color.HSL(1, 2, 3), Color.StringColor("red")))
            .withSize(3)
        )
    )
    roundTrip(
      Box(Seq(1.0, 2.0))
        .withBoxpoints(BoxPoints.SuspectedOutliers)
        .withBoxmean(BoxMean.True)
    )
  }

  test("Trace codec should reject unknown enum values") {
    assert(Try(readFromString[Trace]("""{"y": [1], "textposition": "nowhere"}""")).isFailure)
  }

  test("Layout codec should accept wrapped fonts") {
    assertEquals(readFromString[Layout]("""{"font": {"font": {"size": 12}}}"""), Layout().withFont(Font(12)))
    assertEquals(readFromString[Layout]("""{"font": {"size": 12}}"""), Layout().withFont(Font(12)))
  }

  test("Layout codec should round-trip ranges") {
    val layout = Layout()
      .withXaxis(Axis().withRange((1.0, 2.0)))
      .withYaxis(Axis().withRange((LocalDateTime(2020, 1, 2, 3, 4, 5), LocalDateTime(2021, 6, 7, 8, 9, 10))))
    assertEquals(readFromString[Layout](writeToString(layout)), layout)
  }

  test("Plotly.jsonSnippet should be valid JSON") {
    val json = ujson.read(Plotly.jsonSnippet(Seq(Scatter(Seq(1.0))), Layout().withTitle("foo"), Config()))
    assertEquals(json.obj.keySet, Set("data", "layout", "config"))
    assertEquals(json("layout")("title").str, "foo")
  }
}
