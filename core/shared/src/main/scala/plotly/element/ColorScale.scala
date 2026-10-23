package plotly.element
import dataclass.data

sealed abstract class ColorScale extends Product with Serializable

object ColorScale {
  @data(
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use copy instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class CustomScale(values: Seq[(Double, Color)]) extends ColorScale
  @data(
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use copy instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class NamedScale(name: String) extends ColorScale
}
