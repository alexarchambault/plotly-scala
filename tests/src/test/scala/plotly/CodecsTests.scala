package plotly

import com.github.plokhotnyuk.jsoniter_scala.core.{readFromString, writeToString}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import plotly.Codecs._
import plotly.element._
import plotly.layout._

import scala.util.Try

class CodecsTests extends AnyFlatSpec with Matchers {

  private def roundTrip(trace: Trace): Unit = {
    val json = writeToString(trace)
    readFromString[Trace](json) shouldBe trace
  }

  "Trace codec" should "write the trace type" in {
    ujson.read(writeToString[Trace](Bar(Seq(1, 2), Seq(3, 4)))).obj.get("type") shouldBe Some(ujson.Str("bar"))
  }

  it should "assume scatter traces if no type is specified" in {
    readFromString[Trace]("""{"x": [1, 2], "y": [3, 4]}""") shouldBe Scatter(Seq(1, 2), Seq(3, 4))
  }

  it should "use the type field, wherever it is" in {
    readFromString[Trace]("""{"x": [1, 2], "type": "bar", "y": [3, 4]}""") shouldBe Bar(Seq(1, 2), Seq(3, 4))
  }

  it should "reject unknown fields" in {
    Try(readFromString[Trace]("""{"x": [1, 2], "foo": 2}""")).isFailure shouldBe true
    Try(readFromString[Trace]("""{"type": "bar", "x": [1, 2], "y": [1, 2], "foo": 2}""")).isFailure shouldBe true
    Try(readFromString[Layout]("""{"xaxis": {"foo": 2}}""")).isFailure shouldBe true
  }

  it should "not write None fields" in {
    writeToString[Trace](Scatter(Seq(1, 2))) shouldBe """{"type":"scatter","y":[1.0,2.0]}"""
  }

  it should "write NaN as null, and read null as NaN" in {
    val json = writeToString[Trace](Scatter(Seq(1.0, Double.NaN, 3.0)))
    json shouldBe """{"type":"scatter","y":[1.0,null,3.0]}"""
    readFromString[Trace](json) match {
      case s: Scatter =>
        s.y.collect { case Sequence.Doubles(seq) => seq.map(_.isNaN) } shouldBe Some(Seq(false, true, false))
      case other => fail(s"Unexpected trace: $other")
    }
  }

  it should "round-trip the various kinds of sequences" in {
    roundTrip(Scatter(Seq(1.0, 2.0), Seq("a", "b")))
    roundTrip(Scatter(Seq(LocalDateTime(2020, 1, 2, 3, 4, 5), LocalDateTime(2021, 6, 7, 8, 9, 10)), Seq(1.0, 2.0)))
    roundTrip(Heatmap().withZ(Seq(Seq(1.0, 2.0), Seq(3.0, 4.0))))
  }

  it should "round-trip enums, flags, colors and unions" in {
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

  it should "reject unknown enum values" in {
    Try(readFromString[Trace]("""{"y": [1], "textposition": "nowhere"}""")).isFailure shouldBe true
  }

  "Layout codec" should "accept wrapped fonts" in {
    readFromString[Layout]("""{"font": {"font": {"size": 12}}}""") shouldBe Layout().withFont(Font(12))
    readFromString[Layout]("""{"font": {"size": 12}}""") shouldBe Layout().withFont(Font(12))
  }

  it should "round-trip ranges" in {
    val layout = Layout()
      .withXaxis(Axis().withRange((1.0, 2.0)))
      .withYaxis(Axis().withRange((LocalDateTime(2020, 1, 2, 3, 4, 5), LocalDateTime(2021, 6, 7, 8, 9, 10))))
    readFromString[Layout](writeToString(layout)) shouldBe layout
  }

  "Plotly.jsonSnippet" should "be valid JSON" in {
    val json = ujson.read(Plotly.jsonSnippet(Seq(Scatter(Seq(1.0))), Layout().withTitle("foo"), Config()))
    json.obj.keySet shouldBe Set("data", "layout", "config")
    json("layout")("title").str shouldBe "foo"
  }
}
