package plotly
package element

import dataclass._

sealed abstract class Error(val `type`: String) extends Product with Serializable

object Error {
  @data(
    optionSetters = true,
    nullOptionBuilderMethodName = "from",
    nullOptionCopyMethodName = "withValues",
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use withValues instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class Data(
      array: Seq[Double],
      @since
      visible: Option[Boolean] = None,
      symmetric: Option[Boolean] = None,
      arrayminus: Option[Seq[Double]] = None
  ) extends Error("data")

  @data(
    optionSetters = true,
    nullOptionBuilderMethodName = "from",
    nullOptionCopyMethodName = "withValues",
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use withValues instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class Percent(
      value: Double,
      @since
      visible: Option[Boolean] = None,
      symmetric: Option[Boolean] = None,
      valueminus: Option[Double] = None
  ) extends Error("percent")

  @data(
    optionSetters = true,
    nullOptionBuilderMethodName = "from",
    nullOptionCopyMethodName = "withValues",
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use withValues instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class Constant(
      value: Double,
      color: Option[String] = None,
      thickness: Option[Double] = None,
      opacity: Option[Double] = None,
      width: Option[Double] = None
  ) extends Error("constant")
}
