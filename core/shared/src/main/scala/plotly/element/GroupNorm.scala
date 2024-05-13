package plotly.element

sealed abstract class GroupNorm(val label: String) extends Product with Serializable

object GroupNorm {
  case object Fraction extends GroupNorm("fraction")
  case object Percent  extends GroupNorm("percent")
}
