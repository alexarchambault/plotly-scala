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
    roundTrip(Heatmap().updated(z = Seq(Seq(1.0, 2.0), Seq(3.0, 4.0))))
  }

  test("Trace codec should round-trip enums, flags, colors and unions") {
    roundTrip(
      Scatter(Seq(1.0, 2.0)).updated(
        mode = ScatterMode(ScatterMode.Markers, ScatterMode.Lines),
        textposition = TextPosition.BottomRight,
        hoverinfo = HoverInfo(HoverInfo.X, HoverInfo.Y),
        text = Seq("a", "b"),
        fillcolor = Color.RGBA(1, 2, 3, 0.5),
        error_y = Error.Data(Seq(1.0, 2.0)).updated(visible = true),
        marker = Marker().updated(
          symbol = Seq[Symbol](Symbol.Circle(true, false), Symbol.Square(false, true)),
          color = Seq[Color](Color.RGB(1, 2, 3), Color.HSL(1, 2, 3), Color.StringColor("red")),
          size = 3
        )
      )
    )
    roundTrip(
      Box(Seq(1.0, 2.0)).updated(
        boxpoints = BoxPoints.SuspectedOutliers,
        boxmean = BoxMean.True
      )
    )
  }

  test("Trace codec should read and write multi-word hover info elements") {
    val bar  = Bar(Seq(1.0), Seq(2.0)).updated(hoverinfo = HoverInfo(HoverInfo.PercentRoot, HoverInfo.Label))
    val json = writeToString[Trace](bar)
    assertEquals(ujson.read(json).obj.get("hoverinfo"), Some(ujson.Str("percent root+label")))
    assertEquals(readFromString[Trace](json), bar)
  }

  test("Trace codec should round-trip pie traces") {
    val pie = Pie(Seq(19.0, 26.0, 55.0), Seq("Residential", "Non-Residential", "Utility")).updated(
      hole = 0.4,
      textinfo = TextInfo(TextInfo.Label, TextInfo.Percent),
      hoverinfo = HoverInfo(HoverInfo.Label, HoverInfo.Percent, HoverInfo.Name),
      insidetextorientation = InsideTextOrientation.Radial,
      direction = RotationDirection.CounterClockwise,
      domain = Domain().updated(x = (0.0, 0.5), row = 1),
      marker = Marker().updated(colors = Seq[Color](Color.RGB(1, 2, 3), Color.StringColor("red")))
    )
    roundTrip(pie)
    assertEquals(ujson.read(writeToString[Trace](pie)).obj.get("type"), Some(ujson.Str("pie")))
  }

  test("Trace codec should read and write empty text infos as none") {
    val pie  = Pie(Seq(1.0), Seq("a")).updated(textinfo = TextInfo())
    val json = writeToString[Trace](pie)
    assertEquals(ujson.read(json).obj.get("textinfo"), Some(ujson.Str("none")))
    assertEquals(readFromString[Trace](json), pie)
  }

  test("Trace codec should round-trip contour and heatmap traces") {
    roundTrip(
      Contour(Seq(Seq(1.0, 2.0), Seq(3.0, 4.0))).updated(
        contours = Contours().updated(coloring = ContourColoring.Heatmap, showlabels = true, start = 0.0),
        colorbar = ColorBar().updated(
          title = Title.from(text = "Level", side = Side.Right),
          thicknessmode = SizeUnit.Pixels,
          thickness = 20.0
        ),
        line = Line().updated(smoothing = 0.85)
      )
    )
    roundTrip(Heatmap(Seq(Seq(1.0, 2.0))).updated(zsmooth = ZSmooth.False))
    roundTrip(Heatmap(Seq(Seq(1.0, 2.0))).updated(zsmooth = ZSmooth.Best))
    assertEquals(
      ujson.read(writeToString[Trace](Heatmap(Seq(Seq(1.0))).updated(zsmooth = ZSmooth.False))).obj.get("zsmooth"),
      Some(ujson.False)
    )
  }

  test("Trace codec should round-trip 2D histograms") {
    val hist = Histogram2d(Seq(1.0, 2.0), Seq(3.0, 4.0)).updated(
      histnorm = HistNorm.Probability,
      xbins = Bins(0.0, 5.0, 0.5),
      nbinsy = 10,
      zsmooth = ZSmooth.Fast
    )
    roundTrip(hist)
    assertEquals(ujson.read(writeToString[Trace](hist)).obj.get("type"), Some(ujson.Str("histogram2d")))
    val contour = Histogram2dContour(Seq(1.0, 2.0), Seq(3.0, 4.0)).updated(
      contours = Contours().updated(showlabels = true),
      ncontours = 20
    )
    roundTrip(contour)
    assertEquals(ujson.read(writeToString[Trace](contour)).obj.get("type"), Some(ujson.Str("histogram2dcontour")))
  }

  test("Trace codec should write outliers box points in lower case") {
    val box = Box(Seq(1.0, 2.0)).updated(boxpoints = BoxPoints.Outliers)
    assertEquals(ujson.read(writeToString[Trace](box)).obj.get("boxpoints"), Some(ujson.Str("outliers")))
    roundTrip(box)
    assertEquals(readFromString[Trace]("""{"type": "box", "y": [1, 2], "boxpoints": "Outliers"}"""), box)
  }

  test("Trace codec should round-trip violin traces") {
    val violin = Violin(Seq(1.0, 2.0, 3.0)).updated(
      side = ViolinSide.Positive,
      points = BoxPoints.False,
      box = ViolinBox().updated(visible = true, width = 0.2),
      meanline = MeanLine().updated(visible = true),
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
    ).updated(
      connector = Connector().updated(mode = ConnectorMode.Between, line = Line().updated(dash = Dash.Dot)),
      increasing = BarStyle().updated(marker = Marker().updated(color = Color.StringColor("teal"))),
      textinfo = TextInfo(TextInfo.Delta)
    )
    roundTrip(waterfall)
    assertEquals(
      ujson.read(writeToString[Trace](waterfall)).obj.get("measure"),
      Some(ujson.Arr("absolute", "relative", "total"))
    )
  }

  test("Trace codec should round-trip funnel traces") {
    val funnel = Funnel(Seq(100.0, 60.0, 20.0), Seq("visits", "carts", "orders")).updated(
      orientation = Orientation.Horizontal,
      textinfo = TextInfo(TextInfo.Value, TextInfo.PercentInitial),
      connector = Connector().updated(fillcolor = Color.StringColor("lightgray"))
    )
    roundTrip(funnel)
    assertEquals(ujson.read(writeToString[Trace](funnel)).obj.get("type"), Some(ujson.Str("funnel")))
    val funnelArea = FunnelArea(Seq(5.0, 4.0, 3.0), Seq("a", "b", "c")).updated(
      title = Title.from(text = "Sales", position = TitlePosition.TopLeft)
    )
    roundTrip(funnelArea)
    assertEquals(ujson.read(writeToString[Trace](funnelArea)).obj.get("type"), Some(ujson.Str("funnelarea")))
  }

  test("Trace codec should round-trip ohlc traces") {
    val ohlc =
      Ohlc(Seq("2025-01-02", "2025-01-03"), Seq(1.0, 2.0), Seq(3.0, 4.0), Seq(0.5, 1.5), Seq(2.0, 1.8)).updated(
        increasing = DirectionStyle().updated(line = Line().updated(color = Color.StringColor("green"))),
        tickwidth = 0.2
      )
    roundTrip(ohlc)
    assertEquals(ujson.read(writeToString[Trace](ohlc)).obj.get("type"), Some(ujson.Str("ohlc")))
  }

  test("Trace codec should round-trip scatter3d traces") {
    val scatter3d = Scatter3d(Seq(1.0, 2.0), Seq(3.0, 4.0), Seq(5.0, 6.0)).updated(
      mode = ScatterMode(ScatterMode.Lines, ScatterMode.Markers),
      marker = Marker().updated(
        color = Seq[Color](Color.Number(5.0), Color.Number(6.0)),
        colorscale = ColorScale.NamedScale("Viridis"),
        cmin = 0.0,
        colorbar = ColorBar().updated(title = "z")
      ),
      line = Line().updated(colorscale = ColorScale.NamedScale("Greens"), width = 4.0),
      scene = "scene2"
    )
    roundTrip(scatter3d)
    assertEquals(ujson.read(writeToString[Trace](scatter3d)).obj.get("type"), Some(ujson.Str("scatter3d")))
  }

  test("Trace codec should round-trip polar traces") {
    val scatter = ScatterPolar(Seq(1.0, 2.0, 3.0), Seq("a", "b", "c")).updated(
      fill = Fill.ToSelf,
      thetaunit = ThetaUnit.Degrees,
      subplot = "polar2"
    )
    roundTrip(scatter)
    assertEquals(ujson.read(writeToString[Trace](scatter)).obj.get("type"), Some(ujson.Str("scatterpolar")))
    val bar = BarPolar(Seq(1.0, 2.0), Seq(0.0, 90.0)).updated(width = 30.0)
    roundTrip(bar)
    assertEquals(ujson.read(writeToString[Trace](bar)).obj.get("type"), Some(ujson.Str("barpolar")))
    roundTrip(ScatterPolarGl(Seq(1.0), Seq(2.0)))
  }

  test("Layout codec should round-trip polar subplots") {
    val layout = Layout().updated(
      polar = Polar().updated(
        sector = Seq(0.0, 180.0),
        gridshape = PolarGridShape.Linear,
        barmode = BarMode.Overlay,
        radialaxis = RadialAxis().updated(range = (0.0, 5.0), angle = 45.0, side = RotationDirection.Clockwise),
        angularaxis = AngularAxis().updated(
          direction = RotationDirection.Clockwise,
          rotation = 90.0,
          categoryorder = CategoryOrder.CategoryAscending
        )
      )
    )
    assertEquals(readFromString[Layout](writeToString(layout)), layout)
  }

  test("Trace codec should round-trip hierarchical traces") {
    val labels   = Seq("root", "a", "b", "a1")
    val parents  = Seq("", "root", "root", "a")
    val values   = Seq(10.0, 6.0, 4.0, 6.0)
    val sunburst = Sunburst(labels, parents, values).updated(
      branchvalues = BranchValues.Total,
      leaf = Leaf().updated(opacity = 0.6),
      textinfo = TextInfo(TextInfo.Label, TextInfo.PercentParent)
    )
    roundTrip(sunburst)
    assertEquals(ujson.read(writeToString[Trace](sunburst)).obj.get("type"), Some(ujson.Str("sunburst")))
    val treemap = Treemap(labels, parents, values).updated(
      tiling = Tiling().updated(packing = TreemapPacking.SliceDice),
      pathbar = PathBar().updated(visible = false),
      root = Root().updated(color = Color.StringColor("lightgray"))
    )
    roundTrip(treemap)
    assertEquals(ujson.read(writeToString[Trace](treemap)).obj.get("type"), Some(ujson.Str("treemap")))
    val icicle = Icicle(labels, parents).updated(tiling = Tiling().updated(orientation = Orientation.Horizontal))
    roundTrip(icicle)
    assertEquals(ujson.read(writeToString[Trace](icicle)).obj.get("type"), Some(ujson.Str("icicle")))
  }

  test("Trace codec should round-trip sankey traces") {
    val sankey = Sankey(
      SankeyNode().updated(label = Seq("a", "b", "c"), pad = 15.0, align = SankeyNodeAlign.Left),
      SankeyLink().updated(source = Seq(0, 0), target = Seq(1, 2), value = Seq(3.0, 4.0), sort = SankeySort.Input)
    ).updated(arrangement = SankeyArrangement.Snap, valuesuffix = " TWh")
    roundTrip(sankey)
    assertEquals(ujson.read(writeToString[Trace](sankey)).obj.get("type"), Some(ujson.Str("sankey")))
  }

  test("Trace codec should read and write the common trace attributes") {
    def scatter(visible: Visible) = Scatter(Seq(1.0, 2.0)).updated(
      visible = visible,
      legendgroup = "group",
      legendgrouptitle = LegendGroupTitle().updated(text = "Group"),
      legendrank = 2.0,
      ids = Seq("a", "b"),
      customdata = Seq(3.0, 4.0),
      meta = "some meta",
      uid = "id",
      zorder = 1
    )
    for (visible <- Seq[Visible](Visible.True, Visible.False, Visible.LegendOnly))
      roundTrip(scatter(visible))
    assertEquals(ujson.read(writeToString[Trace](scatter(Visible.LegendOnly)))("visible"), ujson.Str("legendonly"))
    assertEquals(ujson.read(writeToString[Trace](scatter(false)))("visible"), ujson.False)
    assert(Try(readFromString[Trace]("""{"y": [1], "visible": "maybe"}""")).isFailure)
  }

  test("Trace codec should round-trip positioning attributes") {
    roundTrip(
      Bar(Seq("2025-01-01", "2025-02-01"), Seq(1.0, 2.0)).updated(
        xperiod = "M1",
        xperiodalignment = PeriodAlignment.Middle,
        xcalendar = Calendar.Gregorian,
        xhoverformat = "%b %Y",
        offsetgroup = "a",
        cliponaxis = false
      )
    )
    roundTrip(Scatter(Seq(1.0, 2.0)).updated(x0 = 10.0, dx = 2.0, orientation = Orientation.Horizontal))
  }

  test("Codecs should round-trip font attributes") {
    val font = Font(14).updated(
      weight = FontWeight.Bold,
      style = FontStyle.Italic,
      variant = FontVariant.SmallCaps,
      textcase = TextCase.Upper,
      lineposition = "under",
      shadow = "auto"
    )
    val layout = Layout().updated(font = font)
    assertEquals(readFromString[Layout](writeToString(layout)), layout)
    val layout600 = Layout().updated(font = Font(14).updated(weight = 600))
    assertEquals(ujson.read(writeToString(layout600))("font")("weight"), ujson.Num(600))
    assertEquals(readFromString[Layout](writeToString(layout600)), layout600)
    roundTrip(
      Scatter(Seq(1.0)).updated(
        textfont = TextFont("Arial").updated(color = Color.StringColor("red"), size = 12.0, weight = FontWeight.Bold)
      )
    )
  }

  test("Trace codec should round-trip selection styles") {
    roundTrip(
      Scatter(Seq(1.0, 2.0)).updated(
        selectedpoints = Seq(1),
        selected =
          Selection().updated(marker = SelectionMarker().updated(color = Color.StringColor("red"), size = 12.0)),
        unselected = Selection().updated(
          marker = SelectionMarker().updated(opacity = 0.3),
          textfont = SelectionTextFont().updated(color = Color.StringColor("gray"))
        )
      )
    )
  }

  test("Trace codec should round-trip fill patterns and gradients") {
    roundTrip(
      Scatter(Seq(1.0, 2.0)).updated(
        fill = Fill.ToZeroY,
        fillpattern = FillPattern().updated(shape = PatternShape.DiagonalDown, solidity = 0.4),
        stackgaps = StackGaps.Interpolate,
        marker = Marker().updated(
          angle = Seq(10.0, 20.0),
          angleref = MarkerAngleRef.Previous,
          gradient = MarkerGradient().updated(`type` = GradientType.Radial, color = Color.StringColor("white"))
        ),
        line = Line().updated(backoff = 4.0, simplify = false)
      )
    )
    roundTrip(
      Scatter(Seq(1.0, 2.0)).updated(
        fillgradient =
          FillGradient().updated(`type` = GradientType.Vertical, colorscale = ColorScale.NamedScale("Blues"))
      )
    )
    val bar = Bar(Seq(1.0, 2.0), Seq(3.0, 4.0)).updated(
      marker = Marker().updated(pattern =
        FillPattern().updated(shape = Seq[PatternShape](PatternShape.Cross, PatternShape.Dots))
      )
    )
    roundTrip(bar)
    assertEquals(ujson.read(writeToString[Trace](bar))("marker")("pattern")("shape"), ujson.Arr("x", "."))
  }

  test("Trace codec should round-trip error bars") {
    roundTrip(
      Scatter(Seq(1.0, 2.0)).updated(
        error_y = Error.Data(Seq(0.1, 0.2)).updated(color = Color.StringColor("gray"), thickness = 1.5, width = 4.0),
        error_x = Error.Sqrt().updated(visible = true)
      )
    )
    roundTrip(Scatter(Seq(1.0)).updated(error_y = Error.Percent(10.0).updated(valueminus = 5.0, traceref = 0)))
    roundTrip(
      Scatter(Seq(1.0)).updated(error_y = Error.Constant(1.0).updated(symmetric = false, valueminus = 0.5))
    )
    val json = writeToString[Trace](Scatter(Seq(1.0)).updated(error_y = Error.Sqrt()))
    assertEquals(ujson.read(json)("error_y")("type"), ujson.Str("sqrt"))
  }

  test("Trace codec should round-trip bar and histogram attributes") {
    roundTrip(
      Bar(Seq("a", "b"), Seq(3.0, 4.0)).updated(
        textangle = 0.0,
        constraintext = ConstrainText.Both,
        insidetextanchor = InsideTextAnchor.Start,
        insidetextfont = Font(10),
        offset = -0.2,
        error_x = Error.Percent(5.0)
      )
    )
    roundTrip(
      Histogram(Seq(1.0, 2.0, 2.5)).updated(
        nbinsx = 20,
        bingroup = "g",
        textposition = BarTextPosition.Outside,
        cumulative = Cumulative(true).updated(direction = CumulativeDirection.Decreasing, currentbin = CurrentBin.Half)
      )
    )
  }

  test("Trace codec should round-trip box statistics") {
    roundTrip(
      Box().updated(
        x = Seq("a", "b"),
        q1 = Seq(1.0, 2.0),
        median = Seq(2.0, 3.0),
        q3 = Seq(3.0, 4.0),
        lowerfence = Seq(0.0, 1.0),
        upperfence = Seq(5.0, 6.0),
        notched = true,
        quartilemethod = QuartileMethod.Inclusive,
        sizemode = BoxSizeMode.StandardDeviation,
        hoveron = BoxHoverOn.BoxesPoints
      )
    )
  }

  test("Trace codec should round-trip heatmap, image and candlestick attributes") {
    roundTrip(
      Heatmap(Seq(Seq(1.0, 2.0), Seq(3.0, 4.0))).updated(
        texttemplate = "%{z}",
        textfont = Font(10),
        transpose = true,
        xtype = CoordinateType.Scaled,
        x0 = 1.0,
        dx = 2.0,
        zauto = false
      )
    )
    roundTrip(Image(Seq(Seq(Seq(1.0, 2.0, 3.0)))).updated(zsmooth = ZSmooth.Fast))
    roundTrip(Candlestick().updated(whiskerwidth = 0.2, name = "candles"))
  }

  test("Trace codec should round-trip surface attributes") {
    val surface = Surface().updated(
      z = Seq(Seq(1.0, 2.0), Seq(3.0, 4.0)),
      colorscale = ColorScale.NamedScale("Viridis"),
      contours = SurfaceContours().updated(
        z = SurfaceContour().updated(show = true, usecolormap = true, project = Projection().updated(z = true))
      ),
      lighting = Lighting().updated(specular = 0.5),
      lightposition = LightPosition().updated(x = 100.0, y = 100.0, z = 1000.0),
      opacityscale = OpacityScale.CustomScale(Seq(0.0 -> 1.0, 1.0 -> 0.5))
    )
    roundTrip(surface)
    assertEquals(
      ujson.read(writeToString[Trace](surface))("opacityscale"),
      ujson.Arr(ujson.Arr(0.0, 1.0), ujson.Arr(1.0, 0.5))
    )
    roundTrip(Surface().updated(opacityscale = OpacityScale.NamedScale("extremes")))
  }

  test("Codecs should round-trip color axes") {
    roundTrip(Heatmap(Seq(Seq(1.0, 2.0))).updated(coloraxis = "coloraxis"))
    val layout = Layout().updated(
      coloraxis = ColorAxis().updated(colorscale = ColorScale.NamedScale("Viridis"), cmin = 0.0, cmax = 10.0),
      coloraxis2 = ColorAxis().updated(showscale = false)
    )
    assertEquals(readFromString[Layout](writeToString(layout)), layout)
  }

  test("Trace codec should round-trip hover labels") {
    roundTrip(Scatter(Seq(1.0)).updated(hoverlabel = HoverLabel().updated(showarrow = false, namelength = -1)))
  }

  test("Trace codec should reject unknown enum values") {
    assert(Try(readFromString[Trace]("""{"y": [1], "textposition": "nowhere"}""")).isFailure)
  }

  test("Layout codec should accept wrapped fonts") {
    assertEquals(readFromString[Layout]("""{"font": {"font": {"size": 12}}}"""), Layout().updated(font = Font(12)))
    assertEquals(readFromString[Layout]("""{"font": {"size": 12}}"""), Layout().updated(font = Font(12)))
  }

  test("Layout codec should round-trip ranges") {
    val layout = Layout().updated(
      xaxis = Axis().updated(range = (1.0, 2.0)),
      yaxis = Axis().updated(range = (LocalDateTime(2020, 1, 2, 3, 4, 5), LocalDateTime(2021, 6, 7, 8, 9, 10)))
    )
    assertEquals(readFromString[Layout](writeToString(layout)), layout)
  }

  test("Plotly.jsonSnippet should be valid JSON") {
    val json = ujson.read(Plotly.jsonSnippet(Seq(Scatter(Seq(1.0))), Layout().updated(title = "foo"), Config()))
    assertEquals(json.obj.keySet, Set("data", "layout", "config"))
    assertEquals(json("layout")("title")("text").str, "foo")
  }

  test("Title codec should write titles as objects") {
    val layout = Layout().updated(
      title = "foo",
      xaxis = Axis().updated(title = Title.from(text = "bar", font = Font(12), standoff = 5.0))
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
      Layout().updated(title = "foo", yaxis = Axis().updated(title = "bar"))
    )
  }

  test("Color codec should write HSL colors with percentages") {
    def scatter(color: Color): Trace = Scatter(Seq(1.0)).updated(marker = Marker().updated(color = color))

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
        Scatter(Seq(1.0)).updated(marker = Marker().updated(color = Color.StringColor(name)))
      )
    assert(Try(readFromString[Trace]("""{"y": [1], "marker": {"color": "notacolor"}}""")).isFailure)
  }

  test("Color scale codec should read levels written as strings") {
    val expected = Heatmap(Seq(Seq(1.0))).updated(
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

  test("Color codec should read and write numeric colors") {
    val scatter = Scatter(Seq(1.0, 2.0, 3.0)).updated(
      marker = Marker().updated(
        color = Seq[Color](Color.Number(0.5), Color.Number(10), Color.Number(300)),
        colorscale = ColorScale.NamedScale("Viridis"),
        cmin = 0.0,
        cmax = 300.0,
        showscale = true
      ),
      line = Line().updated(color = Color.Number(2), colorscale = ColorScale.NamedScale("Greens"))
    )
    val json = writeToString[Trace](scatter)
    assertEquals(ujson.read(json)("marker")("color"), ujson.Arr(0.5, 10.0, 300.0))
    assertEquals(readFromString[Trace](json), scatter)
  }
}
