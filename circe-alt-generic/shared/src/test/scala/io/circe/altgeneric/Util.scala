package io.circe.altgeneric

import org.scalacheck.{ Prop, Test }
import utest._

object Util {

  implicit class PropExtensions(val prop: Prop) extends AnyVal {
    def validate: Unit = {
      val result = Test.check(Test.Parameters.default, prop)
      assert(result.passed)
    }
  }

}
