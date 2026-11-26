package plotly
package element

import dataclass.data

@data(
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use copy instead",
  deprecatedSettersSince = "0.8.6"
) final case class ScatterMode(flags: Set[ScatterMode.Flag])

object ScatterMode {
  def apply(flags: Flag*): ScatterMode =
    ScatterMode(flags.toSet)

  sealed abstract class Flag(val label: String) extends Product with Serializable

  case object Markers extends Flag("markers")
  case object Text    extends Flag("text")
  case object Lines   extends Flag("lines")

  val flags   = Seq(Markers, Text, Lines)
  val flagMap = flags.map(m => m.label -> m).toMap
}
