package plotly

import org.joda.time._
import plotly.Sequence.DateTimes

object Joda {

  implicit def fromJodaLocalDates(seq: Seq[LocalDate]): Sequence =
    fromJodaLocalDateTimes {
      seq.map(_.toLocalDateTime(LocalTime.MIDNIGHT))
    }

  implicit def fromJodaDateTimes(seq: Seq[DateTime]): Sequence =
    fromJodaLocalDateTimes {
      seq.map(_.toLocalDateTime)
    }

  implicit def fromJodaLocalDateTimes(seq: Seq[LocalDateTime]): Sequence =
    DateTimes {
      seq.map { d =>
        plotly.element.LocalDateTime(
          d.getYear,
          d.getMonthOfYear,
          d.getDayOfMonth,
          d.getHourOfDay,
          d.getMinuteOfHour,
          d.getSecondOfMinute
        )
      }
    }

}
