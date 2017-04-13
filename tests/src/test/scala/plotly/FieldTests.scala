package plotly

import io.circe.Json
import org.scalatest.{FlatSpec, Matchers}

class FieldTests extends FlatSpec with Matchers {

  def traceHasShowlegendField(trace: Trace): Unit = {

    val expectedField = Json.fromBoolean(true)

    val json = Codecs.encodeTrace(trace)
    val field = json.findAllByKey("showlegend")

    assert(field === List(expectedField))
  }

  "Bar" should "have a showlegend field" in {

    val bar = Bar(
      1 to 10,
      1 to 10,
      showlegend = true
    )

    traceHasShowlegendField(bar)
  }

  "Box" should "have a showlegend field" in {

    val box = Box(
      1 to 10,
      showlegend = true
    )

    traceHasShowlegendField(box)
  }

  "Histogram" should "have a showlegend field" in {

    val histogram = Histogram(
      1 to 10,
      showlegend = true
    )

    traceHasShowlegendField(histogram)
  }

  "Scatter" should "have a showlegend field" in {

    val scatter = Scatter(
      1 to 10,
      showlegend = true
    )

    traceHasShowlegendField(scatter)
  }

}
