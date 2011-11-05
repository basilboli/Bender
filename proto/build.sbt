import AssemblyKeys._

scalaVersion := "2.9.1"

name := "Bender"

libraryDependencies += "jline" % "jline" % "0.9.94"


mainClass := Some("bender.Main")



seq(assemblySettings: _*)
