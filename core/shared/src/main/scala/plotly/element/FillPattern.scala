package plotly
package element

import dataclass.data

/** A pattern filling areas (`fillpattern` of scatter traces) or bars (`pattern` of markers) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class FillPattern(
    shape: Option[OneOrSeq[PatternShape]] = None,
    // an SVG path, rather than one of the shapes
    path: Option[OneOrSeq[String]] = None,
    fillmode: Option[PatternFillMode] = None,
    bgcolor: Option[OneOrSeq[Color]] = None,
    fgcolor: Option[OneOrSeq[Color]] = None,
    fgopacity: Option[Double] = None,
    // the size of the pattern tiles, in pixels
    size: Option[OneOrSeq[Double]] = None,
    // the fraction of the area the pattern covers
    solidity: Option[OneOrSeq[Double]] = None
)
