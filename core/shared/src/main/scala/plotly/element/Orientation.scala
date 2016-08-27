package plotly
package element

sealed abstract class Orientation(val label: String) extends Product with Serializable

object Orientation {
  case object Horizontal extends Orientation("h")
  case object Vertical   extends Orientation("v")
}
