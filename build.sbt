name := "free-as-in-monads"
organization := "com.pbassiner"
scalaVersion := "2.12.2"
scalacOptions ++= Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

// DEPENDENCIES

libraryDependencies ++= {
  val scalatestV      = "3.0.3"
  val scalacheckV     = "1.13.5"
  val catsV           = "0.9.0"
  val fs2V            = "0.9.7"
  val fs2CatsV        = "0.3.0"
  Seq(
    "org.scalatest"   %% "scalatest"    % scalatestV,
    "org.scalacheck"  %% "scalacheck"   % scalacheckV,
    "org.typelevel"   %% "cats"         % catsV,
    "co.fs2"          %% "fs2-core"     % fs2V,
    "co.fs2"          %% "fs2-cats"     % fs2CatsV
  )
}

dependencyOverrides ++= {
  Set(
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "org.scala-lang" % "scala-library" % scalaVersion.value
  )
}

// COMPILER

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")

// FORMATTING

scalafmtOnCompile := true
scalafmtOnCompile.in(Sbt) := false
scalafmtVersion := "1.1.0"
