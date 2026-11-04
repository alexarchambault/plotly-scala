package plotly
package element

import dataclass.data

@data(optionSetters = true) final case class Marker(
    size: Option[OneOrSeq[Int]] = None,
    color: Option[OneOrSeq[Color]] = None,
    opacity: Option[OneOrSeq[Double]] = None,
    line: Option[Line] = None,
    symbol: Option[OneOrSeq[Symbol]] = None,
    outliercolor: Option[Color] = None,
    sizeref: Option[Double] = None,
    sizemode: Option[SizeMode] = None,
    width: Option[OneOrSeq[Int]] = None
) extends MarkerSetters

object Marker extends MarkerDeprecatedApply
