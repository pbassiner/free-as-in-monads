package com.pbassiner

import cats._
import fs2._
import fs2.interop.cats._

import org.scalatest.{ FunSuite, Matchers }

class ProgramSuite extends FunSuite with Matchers {

  import Free._
  import DiskIO._

  test("Program reads the contents of foo.txt and bar.txt, and writes its contents into output.txt") {
    new Program {

      var files: Map[String, Array[Byte]] =
        Map(
          "foo.txt" -> "fooContent".getBytes,
          "bar.txt" -> "barContent".getBytes
        )

      val interpreter: DiskIO ~> Task = new (DiskIO ~> Task) {
        implicit val strategy = fs2.Strategy.fromFixedDaemonPool(2, threadName = "strategy")

        def apply[A](fa: DiskIO[A]): Task[A] = fa match {
          case Read(file) =>
            Task {
              files.get(file).getOrElse(throw new RuntimeException(""))
            }
          case Write(file, contents) =>
            Task {
              files += (file -> contents)
              ()
            }
          case Delete(file) => ???
        }
      }

      foo.foldMap(interpreter).unsafeRun

      val output = files.get("output.txt")
      output shouldBe defined
      output.foreach { content =>
        new String(content) shouldBe "fooContentbarContent"
      }
    }
  }
}
