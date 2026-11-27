package plotly
package element

/** The direction of the flows of sankey traces */
sealed abstract class SankeyDirection(val label: String) extends Product with Serializable

object SankeyDirection {
  case object Forward  extends SankeyDirection("forward")
  case object Reversed extends SankeyDirection("reversed")
}
