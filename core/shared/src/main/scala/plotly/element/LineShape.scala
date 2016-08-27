package plotly
package element

sealed abstract class LineShape(val label: String) extends Product with Serializable

object LineShape {
  case object Linear extends LineShape("linear")
  case object Spline extends LineShape("spline")
  case object    VHV extends LineShape("vhv")
  case object    HVH extends LineShape("hvh")
  case object     VH extends LineShape("vh")
  case object     HV extends LineShape("hv")
}
