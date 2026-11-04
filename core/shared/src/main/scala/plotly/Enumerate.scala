package plotly

sealed abstract class Enumerate[T] {
  def apply(): Seq[T]
}

object Enumerate extends EnumerateDerivation {
  def apply[T](implicit enumerate: Enumerate[T]): Enumerate[T] = enumerate

  def instance[T](values: => Seq[T]): Enumerate[T] =
    new Enumerate[T] {
      lazy val values0 = values
      def apply()      = values0
    }
}
