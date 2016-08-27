package io.circe.altgeneric

import shapeless._

import utest._

object LowPriorityTests extends TestSuite {

  object Simple {
    trait TC[T] {
      def prop: Boolean
    }

    object TC {
      def apply[T](implicit tc: TC[T]): TC[T] = tc

      implicit val intTC: TC[Int] = new TC[Int] { def prop = true }
    }

    case class CC(s: String)

    object CC {
      implicit val ccTC: TC[CC] = new TC[CC] { def prop = true }
    }

    case class CC2(s: String)

    object Extra {
      implicit def extraTC[T](implicit notFound: LowPriority): TC[T] =
        new TC[T] { def prop = false }
    }
  }

  object WithIgnoring {
    trait TC[T] {
      def prop: Option[Boolean]
    }

    trait LowPriTC {
      implicit def anyTC[T]: TC[T] = new TC[T] { def prop = None }
    }

    object TC extends LowPriTC {
      def apply[T](implicit tc: TC[T]): TC[T] = tc

      implicit val intTC: TC[Int] = new TC[Int] { def prop = Some(true) }
    }

    case class CC(s: String)

    object CC {
      implicit val ccTC: TC[CC] = new TC[CC] { def prop = Some(true) }
    }

    case class CC2(s: String)

    object Extra {
      implicit def extraTC[T](implicit notFound: LowPriority.Ignoring[Witness.`"anyTC"`.T]): TC[T] =
        new TC[T] { def prop = Some(false) }
    }
  }


  def simpleInt = {
    import Simple._
    import Extra._
    TC[Int]
  }
  def simpleCC = {
    import Simple._
    import Extra._
    TC[CC]
  }
  def simpleString = {
    import Simple._
    import Extra._
    TC[String]
  }
  def simpleCC2 = {
    import Simple._
    import Extra._
    TC[CC2]
  }
  def simpleCC2_0 = {
    import Simple._
    import Extra._

    {
      implicit val cc2TC: TC[CC2] = new TC[CC2] { def prop = true }
      TC[CC2]
    }
  }

  def withIgnoringInt = {
    import WithIgnoring._
    import Extra._
    TC[Int]
  }
  def withIgnoringCC = {
    import WithIgnoring._
    import Extra._
    TC[CC]
  }
  def withIgnoringString = {
    import WithIgnoring._
    import Extra._
    TC[String]
  }
  def withIgnoringCC2 = {
    import WithIgnoring._
    import Extra._
    TC[CC2]
  }
  def withIgnoringCC2_0 = {
    import WithIgnoring._
    import Extra._

    {
      implicit val cc2TC: TC[CC2] = new TC[CC2] { def prop = Some(true) }
      TC[CC2]
    }
  }

  val tests = TestSuite {
    'simple - {
      // `Extra` provides extra implicit instances of `TC[T]`
      // We check here that these do not take precedence over the already existing implicit instances.

      assert(simpleInt.prop)
      assert(simpleCC.prop)
      assert(!simpleString.prop)
      assert(!simpleCC2.prop)

      assert(simpleCC2_0.prop)
    }

    'withIgnoring - {
      import WithIgnoring._
      import Extra._

      // `Extra` provides extra implicit instances of `TC[T]`
      // We check here that these do not take precedence over the already existing implicit instances.

      assert(withIgnoringInt.prop == Some(true))
      assert(withIgnoringCC.prop == Some(true))
      assert(withIgnoringString.prop == Some(false))
      assert(withIgnoringCC2.prop == Some(false))

      assert(withIgnoringCC2_0.prop == Some(true))
    }
  }

}
