package plotly
package layout

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Scene(
    xaxis: Option[Axis] = None,
    yaxis: Option[Axis] = None,
    zaxis: Option[Axis] = None
)
