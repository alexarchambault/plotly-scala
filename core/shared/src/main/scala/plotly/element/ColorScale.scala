package plotly.element
import dataclass.data

sealed abstract class ColorScale extends Product with Serializable

object ColorScale {
  @data final case class CustomScale(values: Seq[(Double, Color)]) extends ColorScale with ColorScaleCustomScaleSetters
  @data final case class NamedScale(name: String)                  extends ColorScale with ColorScaleNamedScaleSetters
}
