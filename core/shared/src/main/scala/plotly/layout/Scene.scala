package plotly
package layout

import dataclass.data
import plotly.element._

@data(optionSetters = true) final case class Scene(
    xaxis: Option[Axis] = None,
    yaxis: Option[Axis] = None,
    zaxis: Option[Axis] = None
) extends SceneSetters

object Scene extends SceneDeprecatedApply
