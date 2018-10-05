package plotly.element

sealed abstract class HistFunc(val label: String) extends Product with Serializable

object HistFunc {

  case object Count extends HistFunc("count")
  case object Sum extends HistFunc("sum")
  case object Average extends HistFunc("avg")
  case object Min extends HistFunc("min")
  case object Max extends HistFunc("max")

}
