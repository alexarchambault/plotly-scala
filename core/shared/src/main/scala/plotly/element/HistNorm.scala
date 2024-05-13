package plotly.element

sealed abstract class HistNorm(val label: String) extends Product with Serializable

object HistNorm {
  case object Count              extends HistNorm("count")
  case object Percent            extends HistNorm("percent")
  case object Probability        extends HistNorm("probability")
  case object Density            extends HistNorm("density")
  case object ProbabilityDensity extends HistNorm("probability density")
}
