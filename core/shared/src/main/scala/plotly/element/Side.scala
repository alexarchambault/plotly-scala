package plotly
package element

sealed abstract class Side(val label: String) extends Product with Serializable

object Side {
  case object   Left extends Side("left")
  case object  Right extends Side("right")
  case object    Top extends Side("top")
  case object Bottom extends Side("bottom")
}
