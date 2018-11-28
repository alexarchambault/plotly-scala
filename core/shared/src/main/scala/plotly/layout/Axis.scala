package plotly
package layout

import java.lang.{ Integer => JInt, Double => JDouble, Boolean => JBoolean }

import plotly.element._

final case class Axis(
           title: Option[String],
       titlefont: Option[Font],
        showgrid: Option[Boolean],
       gridwidth: Option[Int],
       gridcolor: Option[Color],
        showline: Option[Boolean],
  showticklabels: Option[Boolean],
       linecolor: Option[Color],
       linewidth: Option[Int],
        autotick: Option[Boolean],
       tickcolor: Option[Color],
       tickwidth: Option[Int],
       tickangle: Option[Double],
           dtick: Option[Double],
         ticklen: Option[Int],
        tickfont: Option[Font],
        zeroline: Option[Boolean],
   zerolinewidth: Option[Double],
   zerolinecolor: Option[Color],
           range: Option[(Double, Double)],
       autorange: Option[Boolean],
           ticks: Option[Ticks],
          domain: Option[(Double, Double)],
            side: Option[Side],
          anchor: Option[AxisAnchor],
          `type`: Option[AxisType],
      overlaying: Option[AxisAnchor],
        position: Option[Double],
        tickmode: Option[TickMode],
        tickvals: Option[Sequence],
        ticktext: Option[Sequence],
          nticks: Option[Int],
      automargin: Option[Boolean]
)

object Axis {
  def apply(
             title: String           = null,
         titlefont: Font             = null,
          showgrid: JBoolean         = null,
         gridwidth: JInt             = null,
         gridcolor: Color            = null,
          showline: JBoolean         = null,
    showticklabels: JBoolean         = null,
         linecolor: Color            = null,
         linewidth: JInt             = null,
          autotick: JBoolean         = null,
         tickcolor: Color            = null,
         tickwidth: JInt             = null,
         tickangle: JDouble          = null,
             dtick: JDouble          = null,
           ticklen: JInt             = null,
          tickfont: Font             = null,
          zeroline: JBoolean         = null,
     zerolinewidth: JDouble          = null,
     zerolinecolor: Color            = null,
             range: (Double, Double) = null,
         autorange: JBoolean         = null,
             ticks: Ticks            = null,
            domain: (Double, Double) = null,
              side: Side             = null,
            anchor: AxisAnchor       = null,
            `type`: AxisType         = null,
        overlaying: AxisAnchor       = null,
          position: JDouble          = null,
          tickmode: TickMode         = null,
          tickvals: Sequence         = null,
          ticktext: Sequence         = null,
            nticks: JInt             = null,
        automargin: JBoolean         = null,

  ): Axis =
    Axis(
      Option(title),
      Option(titlefont),
      Option(showgrid)       .map(x => x: Boolean),
      Option(gridwidth)      .map(x => x: Int),
      Option(gridcolor),
      Option(showline)       .map(x => x: Boolean),
      Option(showticklabels) .map(x => x: Boolean),
      Option(linecolor),
      Option(linewidth)      .map(x => x: Int),
      Option(autotick)       .map(x => x: Boolean),
      Option(tickcolor),
      Option(tickwidth)      .map(x => x: Int),
      Option(tickangle)      .map(x => x: Double),
      Option(dtick)          .map(x => x: Double),
      Option(ticklen)        .map(x => x: Int),
      Option(tickfont),
      Option(zeroline)       .map(x => x: Boolean),
      Option(zerolinewidth)  .map(x => x: Double),
      Option(zerolinecolor),
      Option(range),
      Option(autorange)      .map(x => x: Boolean),
      Option(ticks),
      Option(domain),
      Option(side),
      Option(anchor),
      Option(`type`),
      Option(overlaying),
      Option(position)       .map(x => x: Double),
      Option(tickmode),
      Option(tickvals),
      Option(ticktext),
      Option(nticks)         .map(x => x: Int),
      Option(automargin)     .map(x => x: Boolean)
    )
}
