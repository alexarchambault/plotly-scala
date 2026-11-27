package plotly
package element

/** How far violins extend beyond the minimum and maximum of their values */
sealed abstract class SpanMode(val label: String) extends Product with Serializable

object SpanMode {
  case object Soft   extends SpanMode("soft")
  case object Hard   extends SpanMode("hard")
  case object Manual extends SpanMode("manual")
}
