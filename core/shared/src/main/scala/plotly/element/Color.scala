package plotly
package element

sealed abstract class Color extends Product with Serializable

object Color {

  final case class RGBA(r: Int, g: Int, b: Int, alpha: Double) extends Color

  final case class StringColor(color: String) extends Color

  object StringColor {
    val colors = Set(
      "black",
      "grey",
      "white",
      "fuchsia",
      "red"
    )
  }

  final case class RGB(r: Int, g: Int, b: Int) extends Color

  final case class HSL(h: Int, s: Int, l: Int) extends Color
}