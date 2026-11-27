package plotly
package element

/** How the nodes of sankey traces are aligned horizontally */
sealed abstract class SankeyNodeAlign(val label: String) extends Product with Serializable

object SankeyNodeAlign {
  case object Justify extends SankeyNodeAlign("justify")
  case object Left    extends SankeyNodeAlign("left")
  case object Right   extends SankeyNodeAlign("right")
  case object Center  extends SankeyNodeAlign("center")
}
