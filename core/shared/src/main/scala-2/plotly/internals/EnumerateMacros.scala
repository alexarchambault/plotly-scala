package plotly.internals

import scala.reflect.macros.blackbox

class EnumerateMacros(val c: blackbox.Context) {
  import c.universe._

  def derive[T: c.WeakTypeTag]: Tree = {
    val tpe = weakTypeOf[T].dealias
    q"_root_.plotly.Enumerate.instance[$tpe](${values(tpe)})"
  }

  // Returns a tree of type Seq[tpe], listing all the values of tpe
  private def values(tpe: Type): Tree = {
    val sym = tpe.typeSymbol
    if (tpe =:= definitions.BooleanTpe)
      q"_root_.scala.collection.immutable.Seq(true, false)"
    else if (sym.isModuleClass)
      q"_root_.scala.collection.immutable.Seq[$tpe](${internal.gen.mkAttributedRef(sym.asClass.module)})"
    else if (sym.isClass && sym.asClass.isSealed) {
      val children = sym.asClass.knownDirectSubclasses.toList.sortBy(_.fullName)
      if (children.isEmpty)
        c.abort(c.enclosingPosition, s"No subclasses found for sealed $tpe")
      val childrenValues = children.map(child => values(child.asType.toType))
      q"_root_.scala.collection.immutable.List[_root_.scala.collection.immutable.Seq[$tpe]](..$childrenValues).flatten"
    } else if (sym.isClass && !sym.asClass.isAbstract && sym.asClass.typeParams.isEmpty) {
      val ctor = tpe.decls
        .collectFirst { case m: MethodSymbol if m.isPrimaryConstructor => m }
        .getOrElse(c.abort(c.enclosingPosition, s"No primary constructor found for $tpe"))
      val paramss = ctor.paramLists.map(_.map(p => (TermName(c.freshName("p")), p.typeSignature)))
      val init    = q"_root_.scala.collection.immutable.Seq[$tpe](new $tpe(...${paramss.map(_.map(p => q"${p._1}"))}))"
      paramss.flatten.foldRight(init) { case ((name, paramTpe), acc) =>
        q"${values(paramTpe)}.flatMap(($name: $paramTpe) => $acc)"
      }
    } else
      c.abort(c.enclosingPosition, s"Cannot enumerate values of $tpe")
  }
}
