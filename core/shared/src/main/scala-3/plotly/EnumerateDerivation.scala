package plotly

import plotly.internals.EnumerateMacros

private[plotly] trait EnumerateDerivation {

  /** Enumerates the values of booleans, case objects, sealed hierarchies of enumerable types, and classes whose
    * fields are all enumerable
    */
  implicit inline def derive[T]: Enumerate[T] = ${ EnumerateMacros.derive[T] }
}
