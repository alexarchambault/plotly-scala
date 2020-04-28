package plotly
package element

import java.lang.{ Boolean => JBoolean, Double => JDouble }

import dataclass._

sealed abstract class Error(val `type`: String) extends Product with Serializable

object Error {
  @data(optionSetters = true) class Data(
         array: Seq[Double],
    @since
       visible: Option[Boolean] = None,
     symmetric: Option[Boolean] = None,
    arrayminus: Option[Seq[Double]] = None
  ) extends Error("data")

  object Data {
    @deprecated("Use Data(array) and chain-call .with* methods on it instead", "0.8.0")
    def apply(
           array: Seq[Double],
         visible: JBoolean    = null,
       symmetric: JBoolean    = null,
      arrayminus: Seq[Double] = null
    ): Data =
      Data(
        array,
        Option(visible).map(x => x: Boolean),
        Option(symmetric).map(x => x: Boolean),
        Option(arrayminus)
      )
  }

  @data(optionSetters = true) class Percent(
    value: Double,
    @since
    visible: Option[Boolean] = None,
    symmetric: Option[Boolean] = None,
    valueminus: Option[Double] = None
  ) extends Error("percent")

  object Percent {
    @deprecated("Use Percent(value) and chain-call .with* methods on it instead", "0.8.0")
    def apply(
      value: Double,
      visible: JBoolean    = null,
      symmetric: JBoolean    = null,
      valueminus: JDouble = null
    ): Percent =
      Percent(
        value,
        Option(visible).map(x => x: Boolean),
        Option(symmetric).map(x => x: Boolean),
        Option(valueminus).map(d => d: Double)
      )
  }

  @data(optionSetters = true) class Constant(
        value: Double,
        color: Option[String] = None,
    thickness: Option[Double] = None,
      opacity: Option[Double] = None,
        width: Option[Double] = None
  ) extends Error("constant")

  object Constant {
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
}
