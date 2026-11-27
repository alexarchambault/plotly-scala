package plotly
package element

import dataclass.data

/** The nodes of sankey traces (`node` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class SankeyNode(
    label: Option[Sequence] = None,
    color: Option[OneOrSeq[Color]] = None,
    line: Option[Line] = None,
    // the gap between nodes, and their thickness, in pixels
    pad: Option[Double] = None,
    thickness: Option[Double] = None,
    // the positions of the nodes, as fractions of the plot area (with arrangement = SankeyArrangement.Fixed, say)
    x: Option[Seq[Double]] = None,
    y: Option[Seq[Double]] = None,
    align: Option[SankeyNodeAlign] = None,
    sort: Option[SankeySort] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None
)
