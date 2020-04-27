package plotly
package element

import dataclass.data

import java.lang.{ Double => JDouble }

@data(optionSetters = true) class Line(
         shape: Option[LineShape] = None,
         color: Option[OneOrSeq[Color]] = None,
         width: Option[OneOrSeq[Double]] = None,
          dash: Option[Dash] = None,
  outliercolor: Option[Color] = None,
  outlierwidth: Option[Double] = None
)

object Line {
  @deprecated("Use Line() and chain-call .with* methods on it instead", "0.8.0")
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
