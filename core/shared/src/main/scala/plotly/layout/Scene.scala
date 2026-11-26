package plotly
package layout

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues"
) class Scene(
    xaxis: Option[Axis] = None,
    yaxis: Option[Axis] = None,
    zaxis: Option[Axis] = None
)
