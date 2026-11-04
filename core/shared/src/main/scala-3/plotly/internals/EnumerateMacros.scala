package plotly.internals

import plotly.Enumerate

import scala.quoted.*

// Scala 3 counterpart of the Scala 2 EnumerateMacros
object EnumerateMacros {

  def derive[T: Type](using q: Quotes): Expr[Enumerate[T]] = {
    import q.reflect.*
    val values0 = values(using q)(TypeRepr.of[T].dealias)
    // values0 only has T elements, but is typed as a Seq[Any]
    '{ Enumerate.instance[T]($values0.asInstanceOf[Seq[T]]) }
  }

  // Returns an expression of type Seq[tpe], listing all the values of tpe
  private def values(using q: Quotes)(tpe: q.reflect.TypeRepr): Expr[Seq[Any]] = {
    import q.reflect.*
    val sym                         = tpe.typeSymbol
    def abort(msg: String): Nothing =
      report.errorAndAbort(msg)
    if (tpe =:= TypeRepr.of[Boolean])
      '{ Seq(true, false) }
    else if (sym.flags.is(Flags.Module))
      '{ Seq(${ Ref(sym.companionModule).asExpr }) }
    else if (tpe.termSymbol.flags.is(Flags.Module))
      '{ Seq(${ Ref(tpe.termSymbol).asExpr }) }
    else if (sym.isClassDef && sym.flags.is(Flags.Sealed)) {
      val children = sym.children.sortBy(_.fullName)
      if (children.isEmpty)
        abort(s"No subclasses found for sealed ${tpe.show}")
      val childrenValues = children.map { child =>
        if (child.isTerm) '{ Seq(${ Ref(child).asExpr }) } // case object, or enum case without parameters
        else values(child.typeRef)
      }
      '{ ${ Expr.ofList(childrenValues) }.flatten }
    } else if (
      sym.isClassDef && !sym.flags.is(Flags.Abstract) && !sym.flags
        .is(Flags.Trait) && sym.typeMembers.forall(!_.isTypeParam)
    ) {
      val ctor = sym.primaryConstructor
      if (ctor.isNoSymbol)
        abort(s"No primary constructor found for ${tpe.show}")
      val paramss     = ctor.paramSymss.filter(_.forall(_.isTerm))
      val params      = paramss.flatten
      val fieldValues = params.map(p => values(tpe.memberType(p)))
      '{
        _root_.plotly.internals.EnumerateMacros.combinations(${ Expr.ofList(fieldValues) }).map { args =>
          ${
            var idx   = 0
            val argss = paramss.map(_.map { p =>
              val i = idx
              idx += 1
              tpe.memberType(p).asType match {
                case '[a] => '{ args(${ Expr(i) }).asInstanceOf[a] }.asTerm
              }
            })
            argss.foldLeft[Term](Select(New(Inferred(tpe)), ctor))(Apply(_, _)).asExpr
          }
        }
      }
    } else
      abort(s"Cannot enumerate values of ${tpe.show}")
  }

  // All the combinations of one value of each of these sequences
  def combinations(values: Seq[Seq[Any]]): Seq[Seq[Any]] =
    values.foldRight(Seq(Seq.empty[Any])) { (headValues, tailCombinations) =>
      headValues.flatMap(h => tailCombinations.map(h +: _))
    }
}
