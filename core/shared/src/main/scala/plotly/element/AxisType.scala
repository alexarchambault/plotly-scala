package plotly
package element

sealed abstract class AxisType(val label: String) extends Product with Serializable

object AxisType {

  /** Lets plotly guess from data */
  case object Default  extends AxisType("-")
  case object Linear   extends AxisType("linear")
  case object Log      extends AxisType("log")
  case object Date     extends AxisType("date")
  case object Category extends AxisType("category")
}
