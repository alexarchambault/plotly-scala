package plotly
package element

sealed abstract class Anchor(val label: String) extends Product with Serializable

object Anchor {
  case object   Left extends Anchor("left")
  case object Center extends Anchor("center")
  case object  Right extends Anchor("right")
  case object    Top extends Anchor("top")
  case object Middle extends Anchor("middle")
  case object Bottom extends Anchor("bottom")
}
