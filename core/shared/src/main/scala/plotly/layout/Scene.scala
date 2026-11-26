package plotly
package layout

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Scene(
    xaxis: Option[Axis] = None,
    yaxis: Option[Axis] = None,
    zaxis: Option[Axis] = None
)
