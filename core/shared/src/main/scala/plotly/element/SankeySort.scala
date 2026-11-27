package plotly
package element

/** Whether the nodes and links of sankey traces are reordered to reduce crossings (auto), or kept in their input order (input) */
sealed abstract class SankeySort(val label: String) extends Product with Serializable

object SankeySort {
  case object Auto  extends SankeySort("auto")
  case object Input extends SankeySort("input")
}
