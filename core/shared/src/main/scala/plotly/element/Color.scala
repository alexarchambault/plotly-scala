package plotly
package element

import dataclass.data

sealed abstract class Color extends Product with Serializable

object Color {

  @data final case class RGBA(r: Int, g: Int, b: Int, alpha: Double) extends Color with ColorRGBASetters

  @data final case class StringColor(color: String) extends Color with ColorStringColorSetters

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

  @data final case class RGB(r: Int, g: Int, b: Int) extends Color with ColorRGBSetters

  @data final case class HSL(h: Int, s: Int, l: Int) extends Color with ColorHSLSetters
}
