package plotly
package element

import dataclass.data
import plotly.layout.Font

/** The contour levels of contour traces, and how they're drawn (`contours` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Contours(
    start: Option[Double] = None,
    end: Option[Double] = None,
    size: Option[Double] = None,
    coloring: Option[ContourColoring] = None,
    showlines: Option[Boolean] = None,
    showlabels: Option[Boolean] = None,
    labelfont: Option[Font] = None,
    labelformat: Option[String] = None
)
