package plotly
package element

import scala.collection.immutable.Seq

import dataclass.data

import java.lang.{ Boolean => JBoolean, Double => JDouble }

sealed abstract class Error(val `type`: String) extends Product with Serializable

object Error {
  @data class Data(
         array: Seq[Double],
       visible: Option[Boolean],
     symmetric: Option[Boolean],
    arrayminus: Option[Seq[Double]]
  ) extends Error("data")

  object Data {
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

  @data class Percent(
    value: Double,
    visible: Option[Boolean],
    symmetric: Option[Boolean],
    valueminus: Option[Double]
  ) extends Error("percent")

  object Percent {
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

  @data class Constant(
        value: Double,
        color: Option[String],
    thickness: Option[Double],
      opacity: Option[Double],
        width: Option[Double]
  ) extends Error("constant")

  object Constant {
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
