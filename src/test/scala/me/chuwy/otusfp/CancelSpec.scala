package me.chuwy.otusfp

import scala.concurrent.Future
import scala.concurrent.duration._

import cats.Monad

import org.specs2.mutable.Specification
import cats.effect.testing.specs2.CatsEffect
import cats.implicits._

import cats.effect.{MonadCancel, IO, Async, Sync}
import cats.effect.implicits._
import cats.effect.kernel.Concurrent

class CancelSpec extends Specification with CatsEffect {
  "Cancel" should {
    "provide forceR" in {
      ok
    }


    "provide cancellable" in {
      ok
    }

    "provide onCancel" in {
      ok
    }
  }
}
