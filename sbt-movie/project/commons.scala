import sbt._
import Keys._

object Commons {
  val appVersion = "0.0.1"

  val settings: Seq[Def.Setting[_]] = Seq(
    version := appVersion,
    scalaVersion  := "2.11.4",
    resolvers += Opts.resolver.mavenLocalFile
  )
}

