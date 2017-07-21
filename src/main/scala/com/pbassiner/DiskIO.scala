package com.pbassiner

sealed trait DiskIO[A]

object DiskIO {
  final case class Read(file: String) extends DiskIO[Array[Byte]]
  final case class Write(file: String, contents: Array[Byte]) extends DiskIO[Unit]
  final case class Delete(file: String) extends DiskIO[Boolean]
}
