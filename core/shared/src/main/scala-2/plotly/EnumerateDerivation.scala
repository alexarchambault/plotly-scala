package plotly

import plotly.internals.EnumerateMacros

import scala.language.experimental.macros

private[plotly] trait EnumerateDerivation {

  /** Enumerates the values of booleans, case objects, sealed hierarchies of enumerable types, and classes whose
    * fields are all enumerable
    */
  implicit def derive[T]: Enumerate[T] = macro EnumerateMacros.derive[T]
}
