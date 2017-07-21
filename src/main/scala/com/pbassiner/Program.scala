package com.pbassiner

trait Program {

  import Free._
  import DiskIO._

  val foo: Free[DiskIO, Unit] =
    for {
      foo <- Free.liftM(DiskIO.Read("foo.txt"))
      bar <- Free.liftM(DiskIO.Read("bar.txt"))
      _ <- Free.liftM(DiskIO.Write("output.txt", foo ++ bar))
    } yield ()

}
