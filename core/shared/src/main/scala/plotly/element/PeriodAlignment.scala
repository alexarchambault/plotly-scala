package plotly
package element

/** Where the points of period-positioned traces are drawn within their period (`xperiodalignment`
  * and `yperiodalignment` in plotly.js)
  */
sealed abstract class PeriodAlignment(val label: String) extends Product with Serializable

object PeriodAlignment {
  case object Start  extends PeriodAlignment("start")
  case object Middle extends PeriodAlignment("middle")
  case object End    extends PeriodAlignment("end")
}
