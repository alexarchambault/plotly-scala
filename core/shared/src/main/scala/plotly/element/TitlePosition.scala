package plotly
package element

/** The position of the title of pie-like traces (funnelarea traces only accept the top ones) */
sealed abstract class TitlePosition(val label: String) extends Product with Serializable

object TitlePosition {
  case object TopLeft      extends TitlePosition("top left")
  case object TopCenter    extends TitlePosition("top center")
  case object TopRight     extends TitlePosition("top right")
  case object MiddleCenter extends TitlePosition("middle center")
  case object BottomLeft   extends TitlePosition("bottom left")
  case object BottomCenter extends TitlePosition("bottom center")
  case object BottomRight  extends TitlePosition("bottom right")
}
