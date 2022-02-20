package me.chuwy.otusfp

import scala.concurrent.duration._

import org.specs2.mutable.Specification
import cats.effect.testing.specs2.CatsEffect
import cats.effect.{IO, Spawn, GenSpawn}
import cats.effect.implicits._

class SpawnSpec extends Specification with CatsEffect {
  "start" should {
    "start a fiber" in {
      def longRunningIO: IO[Unit] =
        IO.sleep(100.millis) *> IO.println(s" Hello from ${Thread.currentThread()}") *> longRunningIO

      (Spawn[IO].start(longRunningIO) *> IO.println("The fiber is running in parallel")).timeout(5.seconds)
      ok
    }
  }

}
