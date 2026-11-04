package plotly

import java.lang.{Boolean => JBoolean}

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait ConfigDeprecatedApply {
  @deprecated("Use Config() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      editable: JBoolean = null,
      responsive: JBoolean = null,
      showEditInChartStudio: JBoolean = null,
      plotlyServerURL: String = null
  ): Config =
    new Config(
      Option(editable),
      Option(responsive),
      Option(showEditInChartStudio),
      Option(plotlyServerURL)
    )
}
