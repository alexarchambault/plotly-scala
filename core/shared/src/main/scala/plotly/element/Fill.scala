package plotly
package element

sealed abstract class Fill(val label: String) extends Product with Serializable

object Fill {
  case object None    extends Fill("none")
  case object ToZeroX extends Fill("tozerox")
  case object ToZeroY extends Fill("tozeroy")
  case object ToNextX extends Fill("tonextx")
  case object ToNextY extends Fill("tonexty")
  case object ToSelf  extends Fill("toself")
  case object ToNext  extends Fill("tonext")
}
