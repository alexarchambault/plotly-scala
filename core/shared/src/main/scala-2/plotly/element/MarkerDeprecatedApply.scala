package plotly
package element

import java.lang.{Double => JDouble}

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait MarkerDeprecatedApply {
  @deprecated("Use Marker() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      size: OneOrSeq[Int] = null,
      color: OneOrSeq[Color] = null,
      opacity: OneOrSeq[Double] = null,
      line: Line = null,
      symbol: OneOrSeq[Symbol] = null,
      outliercolor: Color = null,
      sizeref: JDouble = null,
      sizemode: SizeMode = null,
      width: OneOrSeq[Int] = null
  ): Marker =
    Marker(
      Option(size),
      Option(color),
      Option(opacity),
      Option(line),
      Option(symbol),
      Option(outliercolor),
      Option(sizeref).map(d => d: Double),
      Option(sizemode),
      Option(width)
    )
}
