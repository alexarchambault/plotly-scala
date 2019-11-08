package plotly
package element

import dataclass.data

sealed abstract class AxisAnchor(val label: String) extends Product with Serializable

object AxisAnchor {
  @data class Reference(axisReference: AxisReference) extends AxisAnchor(axisReference.label)
  case object Free extends AxisAnchor("free")
  case object Y extends AxisAnchor("y")
}
