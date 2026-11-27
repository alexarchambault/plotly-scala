package plotly
package element

import dataclass.data

/** The lines (waterfall traces) or areas (funnel traces) connecting adjacent bars */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Connector(
    visible: Option[Boolean] = None,
    line: Option[Line] = None,
    // waterfall traces only
    mode: Option[ConnectorMode] = None,
    // funnel traces only
    fillcolor: Option[Color] = None
)
