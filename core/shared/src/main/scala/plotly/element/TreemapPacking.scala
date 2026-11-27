package plotly
package element

/** The algorithm laying out the rectangles of treemap traces */
sealed abstract class TreemapPacking(val label: String) extends Product with Serializable

object TreemapPacking {
  case object Squarify  extends TreemapPacking("squarify")
  case object Binary    extends TreemapPacking("binary")
  case object Dice      extends TreemapPacking("dice")
  case object Slice     extends TreemapPacking("slice")
  case object SliceDice extends TreemapPacking("slice-dice")
  case object DiceSlice extends TreemapPacking("dice-slice")
}
