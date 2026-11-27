package plotly
package element

import dataclass.data

/** The part of the plot area of a trace (pie, sunburst, …) or of a subplot (polar, …), as
  * fractions of the plot area (`x` and `y`), or as a cell of the layout grid (`row` and
  * `column`)
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Domain(
    x: Option[Range] = None,
    y: Option[Range] = None,
    row: Option[Int] = None,
    column: Option[Int] = None
)
