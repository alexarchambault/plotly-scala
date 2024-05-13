package plotly

import shapeless.{:+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr, Strict}

sealed abstract class Enumerate[T] {
  def apply(): Seq[T]
}

object Enumerate {
  def apply[T](implicit enumerate: Enumerate[T]): Enumerate[T] = enumerate

  private def instance[T](values: => Seq[T]): Enumerate[T] =
    new Enumerate[T] {
      def apply() = values
    }

  implicit val boolean: Enumerate[Boolean] =
    instance(Seq(true, false))

  implicit val hnil: Enumerate[HNil] =
    instance(Seq(HNil))
  implicit def hcons[H, T <: HList](implicit
      head: Strict[Enumerate[H]],
      tail: Enumerate[T]
  ): Enumerate[H :: T] =
    instance {
      for {
        h <- head.value()
        t <- tail()
      } yield h :: t
    }

  implicit val cnil: Enumerate[CNil] =
    instance(Seq())
  implicit def ccons[H, T <: Coproduct](implicit
      head: Strict[Enumerate[H]],
      tail: Enumerate[T]
  ): Enumerate[H :+: T] =
    instance(head.value().map(Inl(_)) ++ tail().map(Inr(_)))

  implicit def generic[F, G](implicit
      gen: Generic.Aux[F, G],
      underlying: Strict[Enumerate[G]]
  ): Enumerate[F] =
    instance(underlying.value().map(gen.from))
}
