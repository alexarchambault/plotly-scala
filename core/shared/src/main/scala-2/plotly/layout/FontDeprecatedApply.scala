package plotly
package layout

import java.lang.{Integer => JInt}
import plotly.element._

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait FontDeprecatedApply {
  @deprecated("Use Font() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      size: JInt = null,
      family: String = null,
      color: Color = null
  ): Font =
    Font(
      Option(size).map(x => x: Int),
      Option(family),
      Option(color)
    )
}
