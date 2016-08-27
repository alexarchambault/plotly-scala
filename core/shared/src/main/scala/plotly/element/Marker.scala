package plotly
package element

import java.lang.{ Double => JDouble }

final case class Marker(
          size: Option[OneOrSeq[Int]],
         color: Option[OneOrSeq[Color]],
       opacity: Option[OneOrSeq[Double]],
          line: Option[Line],
        symbol: Option[OneOrSeq[Symbol]],
  outliercolor: Option[Color],
       sizeref: Option[Double],
      sizemode: Option[SizeMode],
         width: Option[OneOrSeq[Int]]
)

object Marker {
  def apply(
            size: OneOrSeq[Int]    = null,
           color: OneOrSeq[Color]  = null,
         opacity: OneOrSeq[Double] = null,
            line: Line             = null,
          symbol: OneOrSeq[Symbol] = null,
    outliercolor: Color            = null,
         sizeref: JDouble          = null,
        sizemode: SizeMode         = null,
           width: OneOrSeq[Int]    = null
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
