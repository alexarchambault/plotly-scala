package plotly
package element

import java.lang.{Double => JDouble}

import dataclass.data

@data(optionSetters = true) class Marker(
    size: Option[OneOrSeq[Int]] = None,
    color: Option[OneOrSeq[Color]] = None,
    opacity: Option[OneOrSeq[Double]] = None,
    line: Option[Line] = None,
    symbol: Option[OneOrSeq[Symbol]] = None,
    outliercolor: Option[Color] = None,
    sizeref: Option[Double] = None,
    sizemode: Option[SizeMode] = None,
    width: Option[OneOrSeq[Int]] = None
)

object Marker {
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
