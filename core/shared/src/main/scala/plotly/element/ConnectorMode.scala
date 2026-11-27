package plotly
package element

/** Whether the connectors of waterfall traces span the gaps between bars (spanning), or only the
  * space between the bars (between)
  */
sealed abstract class ConnectorMode(val label: String) extends Product with Serializable

object ConnectorMode {
  case object Spanning extends ConnectorMode("spanning")
  case object Between  extends ConnectorMode("between")
}
