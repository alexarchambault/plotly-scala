package plotly
package element

import dataclass._

sealed abstract class Error(val `type`: String) extends Product with Serializable

object Error {
  @data(
    optionSetters = true,
    nullOptionBuilderMethodName = "from",
    nullOptionCopyMethodName = "updated",
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use updated instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class Data(
      array: Seq[Double],
      @since
      visible: Option[Boolean] = None,
      symmetric: Option[Boolean] = None,
      arrayminus: Option[Seq[Double]] = None,
      @since("0.8.6")
      color: Option[Color] = None,
      thickness: Option[Double] = None,
      width: Option[Double] = None,
      copy_ystyle: Option[Boolean] = None,
      traceref: Option[Int] = None,
      tracerefminus: Option[Int] = None
  ) extends Error("data")

  @data(
    optionSetters = true,
    nullOptionBuilderMethodName = "from",
    nullOptionCopyMethodName = "updated",
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use updated instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class Percent(
      value: Double,
      @since
      visible: Option[Boolean] = None,
      symmetric: Option[Boolean] = None,
      valueminus: Option[Double] = None,
      @since("0.8.6")
      color: Option[Color] = None,
      thickness: Option[Double] = None,
      width: Option[Double] = None,
      copy_ystyle: Option[Boolean] = None,
      traceref: Option[Int] = None,
      tracerefminus: Option[Int] = None
  ) extends Error("percent")

  @data(
    optionSetters = true,
    nullOptionBuilderMethodName = "from",
    nullOptionCopyMethodName = "updated",
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use updated instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class Constant(
      value: Double,
      color: Option[String] = None,
      thickness: Option[Double] = None,
      width: Option[Double] = None,
      @since("0.8.6")
      visible: Option[Boolean] = None,
      symmetric: Option[Boolean] = None,
      valueminus: Option[Double] = None,
      copy_ystyle: Option[Boolean] = None,
      traceref: Option[Int] = None,
      tracerefminus: Option[Int] = None
  ) extends Error("constant")

  /** Error bars proportional to the square roots of the values */
  @data(
    optionSetters = true,
    nullOptionBuilderMethodName = "from",
    nullOptionCopyMethodName = "updated",
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use updated instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class Sqrt(
      visible: Option[Boolean] = None,
      color: Option[Color] = None,
      thickness: Option[Double] = None,
      width: Option[Double] = None,
      copy_ystyle: Option[Boolean] = None
  ) extends Error("sqrt")
}
