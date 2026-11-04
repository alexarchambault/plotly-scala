package plotly
package layout

import java.lang.{Boolean => JBoolean, Integer => JInt}

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait MarginDeprecatedApply {
  @deprecated("Use Margin() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      autoexpand: JBoolean = null,
      l: JInt = null,
      r: JInt = null,
      t: JInt = null,
      b: JInt = null,
      pad: JInt = null
  ): Margin =
    Margin(
      Option(autoexpand).map(b => b: Boolean),
      Option(l).map(n => n: Int),
      Option(r).map(n => n: Int),
      Option(t).map(n => n: Int),
      Option(b).map(n => n: Int),
      Option(pad).map(n => n: Int)
    )
}
