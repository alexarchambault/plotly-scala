package plotly

import java.time._
import plotly.element.{LocalDateTime => PlotlyLocalDateTime}

object JavaTime {

  implicit def fromJavaLocalDateTime(javaLocalDateTime: LocalDateTime): PlotlyLocalDateTime =
    PlotlyLocalDateTime(
      javaLocalDateTime.getYear,
      javaLocalDateTime.getMonthValue,
      javaLocalDateTime.getDayOfMonth,
      javaLocalDateTime.getHour,
      javaLocalDateTime.getMinute,
      javaLocalDateTime.getSecond,
    )

  implicit def fromJavaInstant(javaInstant: Instant): PlotlyLocalDateTime =
    fromJavaOffsetDateTime(javaInstant.atOffset(ZoneOffset.UTC))

  implicit def fromJavaOffsetDateTime(javaOffsetDateTime: OffsetDateTime): PlotlyLocalDateTime =
    fromJavaLocalDateTime(javaOffsetDateTime.toLocalDateTime)

  implicit def fromJavaZonedDateTime(javaZonedDateTime: ZonedDateTime): PlotlyLocalDateTime =
    fromJavaLocalDateTime(javaZonedDateTime.toLocalDateTime)

  implicit def fromJavaLocalDate(javaLocalDate: LocalDate): PlotlyLocalDateTime =
    fromJavaLocalDateTime(javaLocalDate.atStartOfDay)

}
