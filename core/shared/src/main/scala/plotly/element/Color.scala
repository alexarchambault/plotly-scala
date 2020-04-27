package plotly
package element

import dataclass.data

sealed abstract class Color extends Product with Serializable

object Color {

  @data class RGBA(r: Int, g: Int, b: Int, alpha: Double) extends Color

  @data class StringColor(color: String) extends Color

  object StringColor {
    val colors = Set(
      "black",
      "grey",
      "white",
      "fuchsia",
      "red",
      "blue",
      "cls", // ???
      "pink",
      "green",
      "magenta"
    )
  }

  @data class RGB(r: Int, g: Int, b: Int) extends Color

  @data class HSL(h: Int, s: Int, l: Int) extends Color
}