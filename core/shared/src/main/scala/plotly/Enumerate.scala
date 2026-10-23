package plotly

import plotly.internals.EnumerateMacros

import scala.language.experimental.macros

sealed abstract class Enumerate[T] {
  def apply(): Seq[T]
}

object Enumerate {
  def apply[T](implicit enumerate: Enumerate[T]): Enumerate[T] = enumerate

  def instance[T](values: => Seq[T]): Enumerate[T] =
    new Enumerate[T] {
      lazy val values0 = values
      def apply()      = values0
    }

  /** Enumerates the values of booleans, case objects, sealed hierarchies of enumerable types, and classes whose
    * fields are all enumerable
    */
  implicit def derive[T]: Enumerate[T] = macro EnumerateMacros.derive[T]
}
