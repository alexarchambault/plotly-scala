package plotly
package element

sealed abstract class Element extends Product with Serializable

object Element {
  final case class DoubleElement(value: Double) extends Element
  final case class StringElement(value: String) extends Element

  implicit def fromDouble(d: Double): Element =
    DoubleElement(d)
  implicit def fromFloat(f: Float): Element =
    DoubleElement(f.toDouble)
  implicit def fromLong(l: Long): Element =
    DoubleElement(l.toDouble)
  implicit def fromInt(n: Int): Element =
    DoubleElement(n.toDouble)
  implicit def fromString(s: String): Element =
    StringElement(s)
}
