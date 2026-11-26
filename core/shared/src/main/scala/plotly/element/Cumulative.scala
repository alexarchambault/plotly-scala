package plotly.element

import dataclass.data

@data(
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use copy instead",
  deprecatedSettersSince = "0.8.6"
) final case class Cumulative(enabled: Boolean)
