package plotly.layout

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Grid(
    rows: Option[Int] = None,
    columns: Option[Int] = None,
    pattern: Option[Pattern] = None,
    roworder: Option[RowOrder] = None,
    subplots: Option[Seq[Seq[String]]] = None
)
