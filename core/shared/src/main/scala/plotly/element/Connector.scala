package plotly
package element

import dataclass.data

/** The lines (waterfall traces) or areas (funnel traces) connecting adjacent bars */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Connector(
    visible: Option[Boolean] = None,
    line: Option[Line] = None,
    // waterfall traces only
    mode: Option[ConnectorMode] = None,
    // funnel traces only
    fillcolor: Option[Color] = None
)
