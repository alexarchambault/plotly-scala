package plotly.layout

sealed abstract class Pattern(val label: String) extends Product with Serializable

object Pattern {
  case object Independent extends Pattern("independent")
  case object     Coupled extends Pattern("coupled")
}
