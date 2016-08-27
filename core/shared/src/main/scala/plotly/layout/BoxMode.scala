package plotly
package layout

sealed abstract class BoxMode(val label: String) extends Product with Serializable

object BoxMode {
  case object Group extends BoxMode("group")
}
