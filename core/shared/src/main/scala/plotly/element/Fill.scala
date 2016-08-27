package plotly
package element

sealed abstract class Fill(val label: String) extends Product with Serializable

object Fill {
  case object ToZeroY extends Fill("tozeroy")
  case object ToNextY extends Fill("tonexty")
}
