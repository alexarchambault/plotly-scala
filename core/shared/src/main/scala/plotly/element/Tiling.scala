package plotly
package element

import dataclass.data

/** The layout of the rectangles of treemap and icicle traces (`tiling` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Tiling(
    // treemap traces only
    packing: Option[TreemapPacking] = None,
    squarifyratio: Option[Double] = None,
    // icicle traces only
    orientation: Option[Orientation] = None,
    // the axes the layout is flipped along: "x", "y" or "x+y"
    flip: Option[String] = None,
    pad: Option[Double] = None
)
