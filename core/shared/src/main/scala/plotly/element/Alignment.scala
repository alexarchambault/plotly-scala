package plotly.element

sealed abstract class Alignment(val label: String) extends Product with Serializable

object Alignment {
  case object Left extends Alignment("left")
  case object Right extends Alignment("right")
  case object Auto extends Alignment("auto")
}
