package plotly.element

import dataclass.data

import scala.util.Try

@data class LocalDateTime(
    year: Int,
    month: Int,
    dayOfMonth: Int,
    hour: Int,
    minute: Int,
    second: Int
) {
  override def toString: String =
    f"$year-$month%02d-$dayOfMonth%02d $hour%02d:$minute%02d:$second%02d"
}

object LocalDateTime extends PlotlyJavaTimeConversions {

  private object IntStr {
    def unapply(s: String): Option[Int] =
      Try(s.toInt).toOption
  }

  def parse(s: String): Option[LocalDateTime] =
    s.split(' ') match {
      case Array(d, t) =>
        (d.split('-'), t.split(':')) match {
          case (Array(IntStr(y), IntStr(m), IntStr(d)), Array(IntStr(h), IntStr(min), IntStr(s))) =>
            Some(LocalDateTime(y, m, d, h, min, s))
          case (Array(IntStr(y), IntStr(m), IntStr(d)), Array(IntStr(h), IntStr(min))) =>
            Some(LocalDateTime(y, m, d, h, min, 0))
          case _ => None
        }
      case _ => None
    }
}
