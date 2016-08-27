package plotly
package layout

sealed abstract class TraceOrder(val label: String) extends Product with Serializable

object TraceOrder {
  case object Reversed extends TraceOrder("reversed")
}
