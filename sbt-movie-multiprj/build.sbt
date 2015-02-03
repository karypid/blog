import Dependencies._

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

lazy val movieCommon = (project in file("movie-common")).
  settings(Commons.settings: _*).
  settings(
    name := "movie-common",
    libraryDependencies ++= commonDependencies
  )

lazy val cast = (project in file("big-lebowski-cast")).
  settings(Commons.settings: _*).
  settings(
    name := "big-lebowski-cast",
    libraryDependencies ++= commonDependencies
  ).
  dependsOn(movieCommon)

lazy val bigLebowski = (project in file("big-lebowski")).
  settings(Commons.settings: _*).
  settings(
    name := "big-lebowski"
  ).
  dependsOn(cast)

