package me.chuwy.otusfp

import org.specs2.mutable.Specification
import cats.effect.testing.specs2.CatsEffect

import cats.{ MonadError, Monad }
import cats.data.State

import cats.implicits._
import cats.effect.IO

class ErrorSpec extends Specification with CatsEffect {
  type MyMonad[F[_]] = MonadError[F, Unit]
  type MyError[A] = Either[Unit, A]

  "Either" should {
    "can short-circuit" in {
      val s = for {
        a <- State { (s: Int) => (s + 1, s) }
        b <- State { (s: Int) => (s + 1, s) }
        c <- State { (s: Int) => (s + 1, s) }
        d <- State { (s: Int) => (s + 1, s) }
      } yield a + b + c + d

      val o = for {
        a <- Some(3)
        b <- Some(3)
        c <- None
        d <- Some(3)
      } yield a + b + c + d

      // Option

      def withError[F[_]: MyMonad]: F[Int] =
        for {
          a <- MonadError[F, Unit].pure(42)
          b <- MonadError[F, Unit].pure(42)
          d <- MonadError[F, Unit].raiseError[Int](())
          c <- MonadError[F, Unit].pure(42)
        } yield a + b + c

      withError[Option] should beSome
    }
  }

  "MonadError" should {
    "provide error handling" in {
      def withHandling[F[_]: MyMonad] = {
        val value: F[String] = MonadError[F, Unit].raiseError(())
//        value.attempt must beLeft

      }
      ok
    }
  }

  "IO" should {
    "be a MonadError" in {
      val a = IO.raiseError(new RuntimeException("Boom!"))

      a.attempt *> IO.println("Hello, World!")
      ok
    }
  }
}
