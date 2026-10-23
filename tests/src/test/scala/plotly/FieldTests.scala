package plotly

import com.github.plokhotnyuk.jsoniter_scala.core.writeToString
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FieldTests extends AnyFlatSpec with Matchers {

  def traceHasShowlegendField(trace: Trace): Unit = {

    val expectedField = ujson.Bool(true)

    val field = ujson
      .read(writeToString(trace)(Codecs.traceCodec))
      .obj
      .get("showlegend")
      .toList

    assert(field === List(expectedField))
  }

  "Bar" should "have a showlegend field" in {

    val bar = Bar(1 to 10, 1 to 10)
      .withShowlegend(true)

    traceHasShowlegendField(bar)
  }

  "Box" should "have a showlegend field" in {

    val box = Box(1 to 10)
      .withShowlegend(true)

    traceHasShowlegendField(box)
  }

  "Histogram" should "have a showlegend field" in {

    val histogram = Histogram(1 to 10)
      .withShowlegend(true)

    traceHasShowlegendField(histogram)
  }

  "Scatter" should "have a showlegend field" in {

    val scatter = Scatter(1 to 10)
      .withShowlegend(true)

    traceHasShowlegendField(scatter)
  }

}
