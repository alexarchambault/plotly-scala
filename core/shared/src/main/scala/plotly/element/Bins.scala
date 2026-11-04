package plotly.element

import dataclass.data

@data final case class Bins(
    start: Double,
    end: Double,
    size: Double
) extends BinsSetters
