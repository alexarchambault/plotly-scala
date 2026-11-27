package plotly
package element

/** The direction sectors (pie traces) or angles (polar angular axes) go along */
sealed abstract class RotationDirection(val label: String) extends Product with Serializable

object RotationDirection {
  case object Clockwise        extends RotationDirection("clockwise")
  case object CounterClockwise extends RotationDirection("counterclockwise")
}
