package plotly
package element

/** How the categories of a categorical axis are ordered: in the order of the traces (trace), in the
  * order of `categoryarray` (array), alphabetically, or by a statistic of their values
  */
sealed abstract class CategoryOrder(val label: String) extends Product with Serializable

object CategoryOrder {
  case object Trace                   extends CategoryOrder("trace")
  case object CategoryAscending       extends CategoryOrder("category ascending")
  case object CategoryDescending      extends CategoryOrder("category descending")
  case object Array                   extends CategoryOrder("array")
  case object TotalAscending          extends CategoryOrder("total ascending")
  case object TotalDescending         extends CategoryOrder("total descending")
  case object MinAscending            extends CategoryOrder("min ascending")
  case object MinDescending           extends CategoryOrder("min descending")
  case object MaxAscending            extends CategoryOrder("max ascending")
  case object MaxDescending           extends CategoryOrder("max descending")
  case object SumAscending            extends CategoryOrder("sum ascending")
  case object SumDescending           extends CategoryOrder("sum descending")
  case object MeanAscending           extends CategoryOrder("mean ascending")
  case object MeanDescending          extends CategoryOrder("mean descending")
  case object GeometricMeanAscending  extends CategoryOrder("geometric mean ascending")
  case object GeometricMeanDescending extends CategoryOrder("geometric mean descending")
  case object MedianAscending         extends CategoryOrder("median ascending")
  case object MedianDescending        extends CategoryOrder("median descending")
}
