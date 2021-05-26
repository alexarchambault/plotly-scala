package plotly.element.pie

sealed abstract class PieTitlePosition(val label: String) extends Product with Serializable

object PieTitlePosition {
  case object TopLeft      extends PieTitlePosition("top left")
  case object TopCenter    extends PieTitlePosition("top center")
  case object TopRight     extends PieTitlePosition("top right")
  case object MiddleCenter extends PieTitlePosition("middle center")
  case object BottomLeft   extends PieTitlePosition("bottom left")
  case object BottomCenter extends PieTitlePosition("bottom center")
  case object BottomRight  extends PieTitlePosition("bottom right")
}
