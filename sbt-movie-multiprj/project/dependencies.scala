import sbt._
import Keys._

object Dependencies {
  val akkaV = "2.3.8"
  val akka = "com.typesafe.akka"          %%  "akka-actor"              % akkaV

  val commonDependencies: Seq[ModuleID] = Seq(
    akka
  )
}

