package me.chuwy.otusfp

import scala.concurrent.Future
import scala.concurrent.duration._

import cats.Monad

import org.specs2.mutable.Specification
import cats.effect.testing.specs2.CatsEffect
import cats.implicits._

import cats.effect.{Async, MonadCancel, Clock, IO, Sync}
import cats.effect.implicits._
import cats.effect.kernel.{Concurrent, Unique}
import cats.effect.std.Random

class SyncSpec extends Specification with CatsEffect {
  "apply" should {
    "suspend" in {
      def test[F[_]: Sync] =
        Sync[F].delay(println("Boom!"))

      val a = test[IO]

      Async[IO].async_()

      ok
    }
  }
}
