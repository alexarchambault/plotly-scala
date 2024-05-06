package plotly.element

sealed abstract class TickMode(val mode: String) extends Product with Serializable

object TickMode {
  case object Auto   extends TickMode("auto")
  case object Linear extends TickMode("linear")
  case object Array  extends TickMode("array")
}
