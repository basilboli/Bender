import AssemblyKeys._

scalaVersion := "2.9.1"

name := "Bender"

libraryDependencies += "jline" % "jline" % "0.9.94" intransitive()


mainClass := Some("bender.Main")



seq(assemblySettings: _*)

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "se.scalablesolutions.akka" % "akka-actor" % "1.2"


libraryDependencies += "org.clapper" % "classutil_2.9.0" % "0.3.6"


libraryDependencies += "org.clapper" %% "grizzled-slf4j" % "0.6.6"


resolvers += "Java.net Maven 2 Repo" at "http://download.java.net/maven/2"

libraryDependencies += "org.clapper" %% "avsl" % "0.3.6"


libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1" % "test"


libraryDependencies +=  "commons-codec" % "commons-codec" % "1.3"

libraryDependencies +=  "org.apache.httpcomponents" % "httpclient" % "4.0.1"


libraryDependencies +=   "org.apache.httpcomponents" % "httpcore" % "4.0.1"


resolvers +="gson" at "http://google-gson.googlecode.com/svn/mavenrepo/"

libraryDependencies += "com.google.code.gson" % "gson" % "1.7.1"

resolvers +="httpclient" at "http://repo1.maven.org/maven2/org/apache/httpcomponents/httpcomponents-client/4.1"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.1"


