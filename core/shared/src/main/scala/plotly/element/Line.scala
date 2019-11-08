package plotly
package element

import dataclass.data

import java.lang.{ Double => JDouble }

@data class Line(
         shape: Option[LineShape],
         color: Option[OneOrSeq[Color]],
         width: Option[OneOrSeq[Double]],
          dash: Option[Dash],
  outliercolor: Option[Color],
  outlierwidth: Option[Double]
)

object Line {
  def apply(
           shape: LineShape        = null,
           color: OneOrSeq[Color]  = null,
           width: OneOrSeq[Double] = null,
            dash: Dash             = null,
    outliercolor: Color            = null,
    outlierwidth: JDouble          = null
  ): Line =
    Line(
      Option(shape),
      Option(color),
      Option(width),
      Option(dash),
      Option(outliercolor),
      Option(outlierwidth) .map(x => x: Double)
    )
}
