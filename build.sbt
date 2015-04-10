name := """pegdownTest"""
organization := "com.micronautics"

version := "0.1.0"

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-target:jvm-1.7", "-unchecked",
  "-Ywarn-adapted-args", "-Ywarn-value-discard", "-Xlint")

libraryDependencies += "org.pegdown" % "pegdown" % "1.5.0" withSources()
