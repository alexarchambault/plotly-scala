package plotly.element

import dataclass.{data, since}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Cumulative(
    enabled: Boolean,
    @since("0.8.6")
    direction: Option[CumulativeDirection] = None,
    currentbin: Option[CurrentBin] = None
)
