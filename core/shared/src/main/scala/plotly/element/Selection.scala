package plotly
package element

import dataclass.data

/** The style of the selected or unselected points of a trace (`selected` and `unselected` in
  * plotly.js)
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Selection(
    marker: Option[SelectionMarker] = None,
    textfont: Option[SelectionTextFont] = None
)

/** The style of the markers of selected or unselected points (`size` is only for markers that
  * aren't bars)
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class SelectionMarker(
    color: Option[Color] = None,
    opacity: Option[Double] = None,
    size: Option[Double] = None
)

/** The style of the text of selected or unselected points */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class SelectionTextFont(
    color: Option[Color] = None
)
