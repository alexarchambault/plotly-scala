package plotly
package element

import dataclass.data

sealed abstract class Color extends Product with Serializable

object Color {

  @data(
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use copy instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class RGBA(r: Int, g: Int, b: Int, alpha: Double) extends Color

  @data(
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use copy instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class StringColor(color: String) extends Color

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

  @data(
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use copy instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class RGB(r: Int, g: Int, b: Int) extends Color

  @data(
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use copy instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class HSL(h: Int, s: Int, l: Int) extends Color
}
