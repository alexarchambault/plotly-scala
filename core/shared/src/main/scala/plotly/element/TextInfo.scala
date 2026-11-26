package plotly
package element

import dataclass.data

/** The information shown in the text of the sectors of pie-like and hierarchical traces, or of
  * the bars of waterfall and funnel traces (`textinfo` in plotly.js).
  *
  * Each trace type accepts some of the flags: `label`, `text`, `value` and `percent` for pie
  * traces, for example. An empty set of flags shows nothing (`"none"` in plotly.js).
  */
@data(
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use copy instead",
  deprecatedSettersSince = "0.8.6"
) final case class TextInfo(flags: Set[TextInfo.Flag])

object TextInfo {
  def apply(flags: Flag*): TextInfo =
    TextInfo(flags.toSet)

  sealed abstract class Flag(val label: String) extends Product with Serializable

  case object Label           extends Flag("label")
  case object Text            extends Flag("text")
  case object Value           extends Flag("value")
  case object Percent         extends Flag("percent")
  case object CurrentPath     extends Flag("current path")
  case object PercentRoot     extends Flag("percent root")
  case object PercentEntry    extends Flag("percent entry")
  case object PercentParent   extends Flag("percent parent")
  case object Initial         extends Flag("initial")
  case object Delta           extends Flag("delta")
  case object Final           extends Flag("final")
  case object PercentInitial  extends Flag("percent initial")
  case object PercentPrevious extends Flag("percent previous")
  case object PercentTotal    extends Flag("percent total")

  val flags: Seq[Flag] = Seq(
    Label,
    Text,
    Value,
    Percent,
    CurrentPath,
    PercentRoot,
    PercentEntry,
    PercentParent,
    Initial,
    Delta,
    Final,
    PercentInitial,
    PercentPrevious,
    PercentTotal
  )
  val flagMap: Map[String, Flag] = flags.map(f => f.label -> f).toMap
}
