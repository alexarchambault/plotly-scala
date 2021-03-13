package plotly.layout

sealed abstract class RowOrder(val label: String) extends Product with Serializable

object RowOrder {
  case object TopToBottom extends RowOrder("top to bottom")
  case object BottomToTop extends RowOrder("bottom to top")
}
