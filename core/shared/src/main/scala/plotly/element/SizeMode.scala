package plotly.element

sealed abstract class SizeMode(val label: String) extends Product with Serializable

object SizeMode {
  case object Diameter extends SizeMode("diameter")
  case object Area     extends SizeMode("area")
}
