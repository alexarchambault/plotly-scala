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
    roundTrip(Heatmap().withValues(z = Seq(Seq(1.0, 2.0), Seq(3.0, 4.0))))
  }

  test("Trace codec should round-trip enums, flags, colors and unions") {
    roundTrip(
      Scatter(Seq(1.0, 2.0)).withValues(
        mode = ScatterMode(ScatterMode.Markers, ScatterMode.Lines),
        textposition = TextPosition.BottomRight,
        hoverinfo = HoverInfo(HoverInfo.X, HoverInfo.Y),
        text = Seq("a", "b"),
        fillcolor = Color.RGBA(1, 2, 3, 0.5),
        error_y = Error.Data(Seq(1.0, 2.0)).withValues(visible = true),
        marker = Marker().withValues(
          symbol = Seq[Symbol](Symbol.Circle(true, false), Symbol.Square(false, true)),
          color = Seq[Color](Color.RGB(1, 2, 3), Color.HSL(1, 2, 3), Color.StringColor("red")),
          size = 3
        )
      )
    )
    roundTrip(
      Box(Seq(1.0, 2.0)).withValues(
        boxpoints = BoxPoints.SuspectedOutliers,
        boxmean = BoxMean.True
      )
    )
  }

  test("Trace codec should read and write multi-word hover info elements") {
    val bar  = Bar(Seq(1.0), Seq(2.0)).withValues(hoverinfo = HoverInfo(HoverInfo.PercentRoot, HoverInfo.Label))
    val json = writeToString[Trace](bar)
    assertEquals(ujson.read(json).obj.get("hoverinfo"), Some(ujson.Str("percent root+label")))
    assertEquals(readFromString[Trace](json), bar)
  }

  test("Trace codec should round-trip pie traces") {
    val pie = Pie(Seq(19.0, 26.0, 55.0), Seq("Residential", "Non-Residential", "Utility")).withValues(
      hole = 0.4,
      textinfo = TextInfo(TextInfo.Label, TextInfo.Percent),
      hoverinfo = HoverInfo(HoverInfo.Label, HoverInfo.Percent, HoverInfo.Name),
      insidetextorientation = InsideTextOrientation.Radial,
      direction = RotationDirection.CounterClockwise,
      domain = Domain().withValues(x = (0.0, 0.5), row = 1),
      marker = Marker().withValues(colors = Seq[Color](Color.RGB(1, 2, 3), Color.StringColor("red")))
    )
    roundTrip(pie)
    assertEquals(ujson.read(writeToString[Trace](pie)).obj.get("type"), Some(ujson.Str("pie")))
  }

  test("Trace codec should read and write empty text infos as none") {
    val pie  = Pie(Seq(1.0), Seq("a")).withValues(textinfo = TextInfo())
    val json = writeToString[Trace](pie)
    assertEquals(ujson.read(json).obj.get("textinfo"), Some(ujson.Str("none")))
    assertEquals(readFromString[Trace](json), pie)
  }

  test("Trace codec should round-trip contour and heatmap traces") {
    roundTrip(
      Contour(Seq(Seq(1.0, 2.0), Seq(3.0, 4.0))).withValues(
        contours = Contours().withValues(coloring = ContourColoring.Heatmap, showlabels = true, start = 0.0),
        colorbar = ColorBar().withValues(
          title = Title.from(text = "Level", side = Side.Right),
          thicknessmode = SizeUnit.Pixels,
          thickness = 20.0
        ),
        line = Line().withValues(smoothing = 0.85)
      )
    )
    roundTrip(Heatmap(Seq(Seq(1.0, 2.0))).withValues(zsmooth = ZSmooth.False))
    roundTrip(Heatmap(Seq(Seq(1.0, 2.0))).withValues(zsmooth = ZSmooth.Best))
    assertEquals(
      ujson.read(writeToString[Trace](Heatmap(Seq(Seq(1.0))).withValues(zsmooth = ZSmooth.False))).obj.get("zsmooth"),
      Some(ujson.False)
    )
  }

  test("Trace codec should round-trip 2D histograms") {
    val hist = Histogram2d(Seq(1.0, 2.0), Seq(3.0, 4.0)).withValues(
      histnorm = HistNorm.Probability,
      xbins = Bins(0.0, 5.0, 0.5),
      nbinsy = 10,
      zsmooth = ZSmooth.Fast
    )
    roundTrip(hist)
    assertEquals(ujson.read(writeToString[Trace](hist)).obj.get("type"), Some(ujson.Str("histogram2d")))
    val contour = Histogram2dContour(Seq(1.0, 2.0), Seq(3.0, 4.0)).withValues(
      contours = Contours().withValues(showlabels = true),
      ncontours = 20
    )
    roundTrip(contour)
    assertEquals(ujson.read(writeToString[Trace](contour)).obj.get("type"), Some(ujson.Str("histogram2dcontour")))
  }

  test("Trace codec should write outliers box points in lower case") {
    val box = Box(Seq(1.0, 2.0)).withValues(boxpoints = BoxPoints.Outliers)
    assertEquals(ujson.read(writeToString[Trace](box)).obj.get("boxpoints"), Some(ujson.Str("outliers")))
    roundTrip(box)
    assertEquals(readFromString[Trace]("""{"type": "box", "y": [1, 2], "boxpoints": "Outliers"}"""), box)
  }

  test("Trace codec should round-trip violin traces") {
    val violin = Violin(Seq(1.0, 2.0, 3.0)).withValues(
      side = ViolinSide.Positive,
      points = BoxPoints.False,
      box = ViolinBox().withValues(visible = true, width = 0.2),
      meanline = MeanLine().withValues(visible = true),
      spanmode = SpanMode.Hard,
      scalemode = ViolinScaleMode.Count
    )
    roundTrip(violin)
    assertEquals(ujson.read(writeToString[Trace](violin)).obj.get("type"), Some(ujson.Str("violin")))
  }

  test("Trace codec should round-trip multi-category sequences") {
    val bar  = Bar(Seq(Seq("2024", "2024", "2025"), Seq("Q1", "Q2", "Q1")), Seq(1.0, 2.0, 3.0))
    val json = writeToString[Trace](bar)
    assertEquals(ujson.read(json)("x"), ujson.Arr(ujson.Arr("2024", "2024", "2025"), ujson.Arr("Q1", "Q2", "Q1")))
    assertEquals(readFromString[Trace](json), bar)
    // still reads nested doubles, including empty ones
    roundTrip(Heatmap(Seq(Seq(1.0, 2.0), Seq(3.0, 4.0))))
    roundTrip(Heatmap(Seq(Seq.empty[Double], Seq(3.0, 4.0))))
  }

  test("Trace codec should round-trip waterfall traces") {
    val waterfall = Waterfall(
      Seq("a", "b", "c"),
      Seq(10.0, -2.0, 0.0),
      Seq(WaterfallMeasure.Absolute, WaterfallMeasure.Relative, WaterfallMeasure.Total)
    ).withValues(
      connector = Connector().withValues(mode = ConnectorMode.Between, line = Line().withValues(dash = Dash.Dot)),
      increasing = BarStyle().withValues(marker = Marker().withValues(color = Color.StringColor("teal"))),
      textinfo = TextInfo(TextInfo.Delta)
    )
    roundTrip(waterfall)
    assertEquals(
      ujson.read(writeToString[Trace](waterfall)).obj.get("measure"),
      Some(ujson.Arr("absolute", "relative", "total"))
    )
  }

  test("Trace codec should round-trip funnel traces") {
    val funnel = Funnel(Seq(100.0, 60.0, 20.0), Seq("visits", "carts", "orders")).withValues(
      orientation = Orientation.Horizontal,
      textinfo = TextInfo(TextInfo.Value, TextInfo.PercentInitial),
      connector = Connector().withValues(fillcolor = Color.StringColor("lightgray"))
    )
    roundTrip(funnel)
    assertEquals(ujson.read(writeToString[Trace](funnel)).obj.get("type"), Some(ujson.Str("funnel")))
    val funnelArea = FunnelArea(Seq(5.0, 4.0, 3.0), Seq("a", "b", "c")).withValues(
      title = Title.from(text = "Sales", position = TitlePosition.TopLeft)
    )
    roundTrip(funnelArea)
    assertEquals(ujson.read(writeToString[Trace](funnelArea)).obj.get("type"), Some(ujson.Str("funnelarea")))
  }

  test("Trace codec should reject unknown enum values") {
    assert(Try(readFromString[Trace]("""{"y": [1], "textposition": "nowhere"}""")).isFailure)
  }

  test("Layout codec should accept wrapped fonts") {
    assertEquals(readFromString[Layout]("""{"font": {"font": {"size": 12}}}"""), Layout().withValues(font = Font(12)))
    assertEquals(readFromString[Layout]("""{"font": {"size": 12}}"""), Layout().withValues(font = Font(12)))
  }

  test("Layout codec should round-trip ranges") {
    val layout = Layout().withValues(
      xaxis = Axis().withValues(range = (1.0, 2.0)),
      yaxis = Axis().withValues(range = (LocalDateTime(2020, 1, 2, 3, 4, 5), LocalDateTime(2021, 6, 7, 8, 9, 10)))
    )
    assertEquals(readFromString[Layout](writeToString(layout)), layout)
  }

  test("Plotly.jsonSnippet should be valid JSON") {
    val json = ujson.read(Plotly.jsonSnippet(Seq(Scatter(Seq(1.0))), Layout().withValues(title = "foo"), Config()))
    assertEquals(json.obj.keySet, Set("data", "layout", "config"))
    assertEquals(json("layout")("title")("text").str, "foo")
  }

  test("Title codec should write titles as objects") {
    val layout = Layout().withValues(
      title = "foo",
      xaxis = Axis().withValues(title = Title.from(text = "bar", font = Font(12), standoff = 5.0))
    )
    assertEquals(
      ujson.read(writeToString(layout)),
      ujson.Obj(
        "title" -> ujson.Obj("text" -> "foo"),
        "xaxis" -> ujson.Obj(
          "title" -> ujson.Obj("text" -> "bar", "font" -> ujson.Obj("size" -> 12), "standoff" -> 5.0)
        )
      )
    )
    assertEquals(readFromString[Layout](writeToString(layout)), layout)
  }

  test("Title codec should read string titles") {
    assertEquals(
      readFromString[Layout]("""{"title": "foo", "yaxis": {"title": "bar"}}"""),
      Layout().withValues(title = "foo", yaxis = Axis().withValues(title = "bar"))
    )
  }

  test("Color codec should write HSL colors with percentages") {
    def scatter(color: Color): Trace = Scatter(Seq(1.0)).withValues(marker = Marker().withValues(color = color))

    val json = writeToString(scatter(Color.HSL(120, 50, 40)))
    assertEquals(ujson.read(json)("marker")("color").str, "hsl(120, 50%, 40%)")
    assertEquals(readFromString[Trace](json), scatter(Color.HSL(120, 50, 40)))
    // plotly.js < 4 accepted HSL colors without percentages
    assertEquals(
      readFromString[Trace]("""{"y": [1], "marker": {"color": "hsl(120,50,40)"}}"""),
      scatter(Color.HSL(120, 50, 40))
    )
  }

  test("Color codec should read CSS named colors") {
    for (name <- Seq("cyan", "rebeccapurple", "lightgoldenrodyellow", "transparent", "Maroon", "DarkBlue"))
      assertEquals(
        readFromString[Trace](s"""{"y": [1], "marker": {"color": "$name"}}"""),
        Scatter(Seq(1.0)).withValues(marker = Marker().withValues(color = Color.StringColor(name)))
      )
    assert(Try(readFromString[Trace]("""{"y": [1], "marker": {"color": "notacolor"}}""")).isFailure)
  }

  test("Color scale codec should read levels written as strings") {
    val expected = Heatmap(Seq(Seq(1.0))).withValues(
      colorscale = ColorScale.CustomScale(Seq(0.0 -> Color.RGB(12, 51, 131), 0.5 -> Color.StringColor("red")))
    )
    assertEquals(
      readFromString[Trace](
        """{"type": "heatmap", "z": [[1]], "colorscale": [["0", "rgb(12,51,131)"], [0.5, "red"]]}"""
      ),
      expected
    )
    roundTrip(expected)
    assertEquals(
      ujson.read(writeToString[Trace](expected))("colorscale"),
      ujson.Arr(ujson.Arr(0.0, "rgb(12, 51, 131)"), ujson.Arr(0.5, "red"))
    )
  }
}
