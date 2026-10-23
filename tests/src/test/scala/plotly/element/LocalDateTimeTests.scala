package plotly.element

import plotly.element.LocalDateTime.UnsafeImplicitConversions._
import plotly.element.{LocalDateTime => PlotlyLocalDateTime}

class LocalDateTimeTests extends munit.FunSuite {

  test("JavaTime conversions should convert java.time.LocalDateTime to Plotly LocalDateTime") {
    val javaLocalDateTime   = java.time.LocalDateTime.parse("2020-04-18T14:52:52")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 14, 52, 52)

    assertEquals(javaLocalDateTime: PlotlyLocalDateTime, plotlyLocalDateTime)
  }

  test("JavaTime conversions should convert java.time.Instant to Plotly LocalDateTime using UTC") {
    val javaInstant         = java.time.Instant.parse("2020-04-18T14:52:52Z")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 14, 52, 52)

    assertEquals(javaInstant: PlotlyLocalDateTime, plotlyLocalDateTime)
  }

  test("JavaTime conversions should convert java.time.OffsetDateTime to Plotly LocalDateTime") {
    val javaOffsetDateTime  = java.time.OffsetDateTime.parse("2020-04-18T14:52:52+10:00")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 14, 52, 52)

    assertEquals(javaOffsetDateTime: PlotlyLocalDateTime, plotlyLocalDateTime)
  }

  test("JavaTime conversions should convert java.time.ZonedDateTime to Plotly LocalDateTime") {
    val javaOffsetDateTime  = java.time.ZonedDateTime.parse("2020-04-18T14:52:52+10:00[Australia/Melbourne]")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 14, 52, 52)

    assertEquals(javaOffsetDateTime: PlotlyLocalDateTime, plotlyLocalDateTime)
  }

  test("JavaTime conversions should convert java.time.LocalDate to Plotly LocalDateTime") {
    val javaLocalDate       = java.time.LocalDate.parse("2020-04-18")
    val plotlyLocalDateTime = PlotlyLocalDateTime(2020, 4, 18, 0, 0, 0)

    assertEquals(javaLocalDate: PlotlyLocalDateTime, plotlyLocalDateTime)
  }

}
