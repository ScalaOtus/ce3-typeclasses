package me.chuwy.otusfp

import scala.concurrent.Future
import scala.concurrent.duration._

import cats.Monad

import org.specs2.mutable.Specification
import cats.effect.testing.specs2.CatsEffect
import cats.implicits._

import cats.effect.{MonadCancel, IO}
import cats.effect.implicits._

class CancelSpec extends Specification with CatsEffect {
  "Cancel" should {
    "provide forceR" in {
      val a = IO.pure(42).productR(IO.pure(10))
      val b = IO.raiseError(new RuntimeException("Boom!")) *> IO.pure(10)
      MonadCancel

      IO.raiseError(new RuntimeException("Boom!")).forceR(IO.pure(42))
      ok

    }


    "provide uncancellable" in {
      val justSleep =
        (IO.sleep(1.second) *> IO.println("Still not cancelled"))
      val alsoSleepAndThrow =
        IO.sleep(100.millis) *> IO.raiseError(new RuntimeException("Boom!"))

      List(justSleep, alsoSleepAndThrow).parSequence.map { _ =>
        ok
      }

    }

    "provide onCancel" in {

      val justSleep =
        (IO.sleep(1.second) *> IO.println("Still not cancelled")).onCancel(IO.println("I'm getting cancelled"))
      val alsoSleepAndThrow =
        IO.sleep(100.millis) *> IO.raiseError(new RuntimeException("Boom!"))

      List(justSleep, alsoSleepAndThrow).parSequence.map { _ =>
        ok
      }
    }
  }
}
