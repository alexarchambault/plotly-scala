package plotly
package element

import dataclass.data

/** The box plot drawn inside violins (`box` of violin traces in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class ViolinBox(
    visible: Option[Boolean] = None,
    // relative to the violins' width
    width: Option[Double] = None,
    fillcolor: Option[Color] = None,
    line: Option[Line] = None
)
