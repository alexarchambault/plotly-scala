package plotly
package element

/** How the nodes of sankey traces can be moved by users */
sealed abstract class SankeyArrangement(val label: String) extends Product with Serializable

object SankeyArrangement {
  case object Snap          extends SankeyArrangement("snap")
  case object Perpendicular extends SankeyArrangement("perpendicular")
  case object Freeform      extends SankeyArrangement("freeform")
  case object Fixed         extends SankeyArrangement("fixed")
}
