package plotly
package element

import dataclass.data
import plotly.layout.Font

/** The bar showing the path from the root to the current sector of treemap and icicle traces
  * (`pathbar` in plotly.js)
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class PathBar(
    visible: Option[Boolean] = None,
    // top or bottom
    side: Option[Side] = None,
    // the shape of the edges of its items: ">", "<", "|", "/" or "\\"
    edgeshape: Option[String] = None,
    thickness: Option[Double] = None,
    textfont: Option[Font] = None
)
