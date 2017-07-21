package com.pbassiner

sealed trait Free[F[_], A] {

  import cats._
  import Free._

  def map[B](f: A => B): Free[F, B] =
    Fmap(this, f)

  def flatMap[B](f: A => Free[F, B]): Free[F, B] =
    Bind(this, f)

  def foldMap[G[_]: Monad](nt: F ~> G): G[A] = this match {
    case Pure(a) => Monad[G].pure(a)
    case Suspend(fa) => nt(fa)
    case Fmap(target, f) =>
      Monad[G].map(target.foldMap(nt)) { e =>
        f(e)
      }
    case Bind(target, f) =>
      Monad[G].flatMap(target.foldMap(nt)) { e =>
        f(e).foldMap(nt)
      }
  }
}

object Free {

  def pure[F[_], A](a: A): Free[F, A] = Pure(a)

  def liftM[F[_], A](fa: F[A]): Free[F, A] = Suspend(fa)

  final case class Pure[F[_], A](a: A) extends Free[F, A]
  final case class Suspend[F[_], A](fa: F[A]) extends Free[F, A]
  final case class Fmap[F[_], E, A](target: Free[F, E], f: E => A) extends Free[F, A]
  final case class Bind[F[_], E, A](target: Free[F, E], f: E => Free[F, A]) extends Free[F, A]
}
