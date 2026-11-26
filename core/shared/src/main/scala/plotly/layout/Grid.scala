package plotly.layout

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues"
) class Grid(
    rows: Option[Int] = None,
    columns: Option[Int] = None,
    pattern: Option[Pattern] = None,
    roworder: Option[RowOrder] = None,
    subplots: Option[Seq[Seq[String]]] = None
)
