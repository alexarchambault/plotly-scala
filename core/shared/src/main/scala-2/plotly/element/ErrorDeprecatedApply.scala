package plotly
package element

import java.lang.{Boolean => JBoolean, Double => JDouble}

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait ErrorDataDeprecatedApply {
  import Error._

  @deprecated("Use Data(array) and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      array: Seq[Double],
      visible: JBoolean = null,
      symmetric: JBoolean = null,
      arrayminus: Seq[Double] = null
  ): Data =
    Data(
      array,
      Option(visible).map(x => x: Boolean),
      Option(symmetric).map(x => x: Boolean),
      Option(arrayminus)
    )
}

private[plotly] trait ErrorPercentDeprecatedApply {
  import Error._

  @deprecated("Use Percent(value) and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      value: Double,
      visible: JBoolean = null,
      symmetric: JBoolean = null,
      valueminus: JDouble = null
  ): Percent =
    Percent(
      value,
      Option(visible).map(x => x: Boolean),
      Option(symmetric).map(x => x: Boolean),
      Option(valueminus).map(d => d: Double)
    )
}

private[plotly] trait ErrorConstantDeprecatedApply {
  import Error._

  @deprecated("Use Constant(value) and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      value: Double,
      color: String = null,
      thickness: JDouble = null,
      opacity: JDouble = null,
      width: JDouble = null
  ): Constant =
    Constant(
      value,
      Option(color),
      Option(thickness).map(x => x: Double),
      Option(opacity).map(x => x: Double),
      Option(width).map(x => x: Double)
    )
}
