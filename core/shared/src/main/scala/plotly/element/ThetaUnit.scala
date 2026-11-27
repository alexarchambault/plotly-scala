package plotly
package element

/** The unit of the angles of polar traces and axes */
sealed abstract class ThetaUnit(val label: String) extends Product with Serializable

object ThetaUnit {
  case object Radians extends ThetaUnit("radians")
  case object Degrees extends ThetaUnit("degrees")
}
