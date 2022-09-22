name := "Red7_scala"

version := "0.1"

scalaVersion := "2.13.9"

idePackagePrefix := Some("org.sample")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.9.0" % Test,
)
