package plotly.element
import dataclass.data

sealed abstract class ColorScale extends Product with Serializable

object ColorScale {
  @data class CustomScale(values: Seq[(Double, Color)]) extends ColorScale
  @data class NamedScale(name: String)                  extends ColorScale
}
