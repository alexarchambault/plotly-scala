package plotly.element

import org.scalatest.flatspec.AnyFlatSpec
import plotly.element.LocalDateTime.UnsafeImplicitConversions._
import plotly.element.{LocalDateTime => PlotlyLocalDateTime}

class LocalDateTimeTests extends AnyFlatSpec {

  "JavaTime conversions" should "convert java.time.LocalDateTime to Plotly LocalDateTime" in {
    val javaLocalDateTime = java.time.LocalDateTime.parse("2020-04-18T14:52:52")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 14, 52, 52)

    assert((javaLocalDateTime: PlotlyLocalDateTime) === plotlyLocalDateTime)
  }

  it should "convert java.time.Instant to Plotly LocalDateTime using UTC" in {
    val javaInstant = java.time.Instant.parse("2020-04-18T14:52:52Z")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 14, 52, 52)

    assert((javaInstant: PlotlyLocalDateTime) === plotlyLocalDateTime)
  }

  it should "convert java.time.OffsetDateTime to Plotly LocalDateTime" in {
    val javaOffsetDateTime = java.time.OffsetDateTime.parse("2020-04-18T14:52:52+10:00")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 14, 52, 52)

    assert((javaOffsetDateTime: PlotlyLocalDateTime) === plotlyLocalDateTime)
  }

  it should "convert java.time.ZonedDateTime to Plotly LocalDateTime" in {
    val javaOffsetDateTime = java.time.ZonedDateTime.parse("2020-04-18T14:52:52+10:00[Australia/Melbourne]")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 14, 52, 52)

    assert((javaOffsetDateTime: PlotlyLocalDateTime) === plotlyLocalDateTime)
  }

  it should "convert java.time.LocalDate to Plotly LocalDateTime" in {
    val javaLocalDate = java.time.LocalDate.parse("2020-04-18")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 0, 0, 0)

    assert((javaLocalDate: PlotlyLocalDateTime) === plotlyLocalDateTime)
  }

}
