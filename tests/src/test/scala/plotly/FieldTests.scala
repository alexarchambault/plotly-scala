package plotly

import com.github.plokhotnyuk.jsoniter_scala.core.writeToString

class FieldTests extends munit.FunSuite {

  def traceHasShowlegendField(trace: Trace)(implicit loc: munit.Location): Unit = {

    val expectedField = ujson.Bool(true)

    val field = ujson
      .read(writeToString(trace)(Codecs.traceCodec))
      .obj
      .get("showlegend")
      .toList

    assertEquals(field, List(expectedField))
  }

  test("Bar should have a showlegend field") {

    val bar = Bar(1 to 10, 1 to 10)
      .withShowlegend(true)

    traceHasShowlegendField(bar)
  }

  test("Box should have a showlegend field") {

    val box = Box(1 to 10)
      .withShowlegend(true)

    traceHasShowlegendField(box)
  }

  test("Histogram should have a showlegend field") {

    val histogram = Histogram(1 to 10)
      .withShowlegend(true)

    traceHasShowlegendField(histogram)
  }

  test("Scatter should have a showlegend field") {

    val scatter = Scatter(1 to 10)
      .withShowlegend(true)

    traceHasShowlegendField(scatter)
  }

}
