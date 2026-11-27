package plotly
package element

import dataclass.data

/** The style of the leaves of sunburst and icicle traces (`leaf` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Leaf(
    opacity: Option[Double] = None
)
