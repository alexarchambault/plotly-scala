package plotly
package element

sealed abstract class AxisReference(val label: String) extends Product with Serializable

object AxisReference {
  case object X  extends AxisReference("x")
  case object X1 extends AxisReference("x1")
  case object X2 extends AxisReference("x2")
  case object X3 extends AxisReference("x3")
  case object X4 extends AxisReference("x4")
  case object Y  extends AxisReference("y")
  case object Y1 extends AxisReference("y1")
  case object Y2 extends AxisReference("y2")
  case object Y3 extends AxisReference("y3")
  case object Y4 extends AxisReference("y4")
}
