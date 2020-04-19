package plotly.element

import java.time._
import plotly.element.{LocalDateTime => PlotlyLocalDateTime}

trait PlotlyJavaTimeConversions {

  implicit def fromJavaLocalDateTime(javaLocalDateTime: java.time.LocalDateTime): PlotlyLocalDateTime =
    PlotlyLocalDateTime(
      javaLocalDateTime.getYear,
      javaLocalDateTime.getMonthValue,
      javaLocalDateTime.getDayOfMonth,
      javaLocalDateTime.getHour,
      javaLocalDateTime.getMinute,
      javaLocalDateTime.getSecond,
    )

  implicit def fromJavaInstant(javaInstant: Instant): PlotlyLocalDateTime =
    fromJavaLocalDateTime(javaInstant.atOffset(ZoneOffset.UTC).toLocalDateTime)

  implicit def fromJavaLocalDate(javaLocalDate: LocalDate): PlotlyLocalDateTime =
    fromJavaLocalDateTime(javaLocalDate.atStartOfDay)

  /**
    * Implicit conversions in this object convert to `plotly.element.LocalDateTime` by simply dropping timezone/offset
    * information. This can lead to unexpected behaviour, particularly for datasets with varying offsets and timezones.
    * It will generally be safer to convert your data to `java.time.LocalDateTime` in the appropriate timezone/offset,
    * and then use the `PlotlyJavaTimeConversions.fromJavaLocalDateTime` implicit conversion.
    */
  object UnsafeImplicitConversions {

    implicit def fromJavaOffsetDateTime(javaOffsetDateTime: OffsetDateTime): PlotlyLocalDateTime =
      fromJavaLocalDateTime(javaOffsetDateTime.toLocalDateTime)

    implicit def fromJavaZonedDateTime(javaZonedDateTime: ZonedDateTime): PlotlyLocalDateTime =
      fromJavaLocalDateTime(javaZonedDateTime.toLocalDateTime)

  }

}
