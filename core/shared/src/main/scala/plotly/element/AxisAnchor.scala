package plotly
package element

sealed abstract class AxisAnchor(val label: String) extends Product with Serializable

object AxisAnchor {
  case class Reference(axisReference: AxisReference) extends AxisAnchor(axisReference.label)
  case object Free extends AxisAnchor("free")
}
