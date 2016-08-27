package plotly
package element

sealed abstract class TextPosition(val label: String) extends Product with Serializable

object TextPosition {
  case object TopLeft      extends TextPosition("top left")
  case object TopCenter    extends TextPosition("top center")
  case object TopRight     extends TextPosition("top right")
  case object MiddleLeft   extends TextPosition("middle left")
  case object MiddleCenter extends TextPosition("middle center")
  case object MiddleRight  extends TextPosition("middle right")
  case object BottomLeft   extends TextPosition("bottom left")
  case object BottomCenter extends TextPosition("bottom center")
  case object BottomRight  extends TextPosition("bottom right")
}
