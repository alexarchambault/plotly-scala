package plotly
package element

import dataclass.data

/** The style of the root of hierarchical traces (`root` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Root(
    color: Option[Color] = None
)
