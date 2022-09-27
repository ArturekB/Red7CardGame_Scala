name := "Red7_scala"

version := "0.1"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-Ymacro-annotations"
)

scalaVersion := "2.13.8"
val circeVersion = "0.13.0"
val http4sVersion = "0.21.22"
val logbackVersion = "1.2.11"

idePackagePrefix := Some("org.sample")

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.11" % Runtime,
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
)
