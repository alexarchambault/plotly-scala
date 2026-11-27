package plotly
package element

import dataclass.data

/** The links of sankey traces (`link` in plotly.js): the flows from their source nodes to their
  * target nodes, both given by their indices in the nodes
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class SankeyLink(
    source: Option[Seq[Int]] = None,
    target: Option[Seq[Int]] = None,
    value: Option[Seq[Double]] = None,
    label: Option[Sequence] = None,
    color: Option[OneOrSeq[Color]] = None,
    hovercolor: Option[OneOrSeq[Color]] = None,
    line: Option[Line] = None,
    // the length of the arrows at the end of links, in pixels
    arrowlen: Option[Double] = None,
    sort: Option[SankeySort] = None,
    hoverinfo: Option[HoverInfo] = None,
    hovertemplate: Option[OneOrSeq[String]] = None
)
