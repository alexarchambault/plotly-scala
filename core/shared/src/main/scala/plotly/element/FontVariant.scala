package plotly
package element

sealed abstract class FontVariant(val label: String) extends Product with Serializable

object FontVariant {
  case object Normal        extends FontVariant("normal")
  case object SmallCaps     extends FontVariant("small-caps")
  case object AllSmallCaps  extends FontVariant("all-small-caps")
  case object AllPetiteCaps extends FontVariant("all-petite-caps")
  case object PetiteCaps    extends FontVariant("petite-caps")
  case object Unicase       extends FontVariant("unicase")
}
