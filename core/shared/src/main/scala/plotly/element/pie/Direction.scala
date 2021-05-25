package plotly.element.pie

sealed abstract class Direction(val label: String) extends Product with Serializable

object Direction {
  case object Clockwise extends Direction("clockwise")
  case object CounterClockwise extends Direction("counterclockwise")
}
