package plotly
package element

import scala.language.implicitConversions

/** The weight of a font: normal, bold, or a value between 1 and 1000 (400 is normal, 700 is bold) */
sealed abstract class FontWeight extends Product with Serializable

object FontWeight {
  case object Normal                  extends FontWeight
  case object Bold                    extends FontWeight
  final case class Value(weight: Int) extends FontWeight

  implicit def fromInt(weight: Int): FontWeight =
    Value(weight)
}
