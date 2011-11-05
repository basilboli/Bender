import AssemblyKeys._

scalaVersion := "2.9.1"

name := "Bender"

libraryDependencies += "jline" % "jline" % "0.9.94"


mainClass := Some("bender.Main")



seq(assemblySettings: _*)


resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "se.scalablesolutions.akka" % "akka-actor" % "1.2"
