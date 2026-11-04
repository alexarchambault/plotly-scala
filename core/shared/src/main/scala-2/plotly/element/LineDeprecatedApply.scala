package plotly
package element

import java.lang.{Double => JDouble}

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait LineDeprecatedApply {
  @deprecated("Use Line() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      shape: LineShape = null,
      color: OneOrSeq[Color] = null,
      width: OneOrSeq[Double] = null,
      dash: Dash = null,
      outliercolor: Color = null,
      outlierwidth: JDouble = null
  ): Line =
    Line(
      Option(shape),
      Option(color),
      Option(width),
      Option(dash),
      Option(outliercolor),
      Option(outlierwidth).map(x => x: Double)
    )
}
