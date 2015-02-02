import Dependencies._

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

lazy val movie = project.
  settings(Commons.settings: _*).
  settings(
    libraryDependencies ++= commonDependencies
  )

lazy val gazzara = (project in file("ben-gazzara")).
  settings(Commons.settings: _*).
  settings(
    libraryDependencies ++= commonDependencies
  ).
  dependsOn(movie)

lazy val bridges = (project in file("jeff-bridges")).
  settings(Commons.settings: _*).
  settings(
    libraryDependencies ++= commonDependencies
  ).
  dependsOn(movie)

lazy val bigLebowski = (project in file("big-lebowski")).
  settings(Commons.settings: _*).
  dependsOn(gazzara, bridges)

