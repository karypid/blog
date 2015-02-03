version := "0.0.1"

scalaVersion  := "2.11.4"

name := "big-lebowski-movie"

resolvers += Opts.resolver.mavenLocalFile

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
  val akkaV = "2.3.8"
  Seq(
    "com.typesafe.akka"          %%  "akka-actor"              % akkaV
  )
}

