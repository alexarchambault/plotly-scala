package plotly
package layout

import dataclass.{data, since}
import plotly.element._

@data(optionSetters = true) final case class Annotation(
    xref: Option[Ref] = None,
    yref: Option[Ref] = None,
    x: Option[Element] = None,
    y: Option[Element] = None,
    xanchor: Option[Anchor] = None,
    yanchor: Option[Anchor] = None,
    text: Option[Element] = None,
    font: Option[Font] = None,
    showarrow: Option[Boolean] = None,
    @since("0.8.0")
    ax: Option[Double] = None,
    ay: Option[Double] = None
) extends AnnotationSetters

object Annotation extends AnnotationDeprecatedApply
