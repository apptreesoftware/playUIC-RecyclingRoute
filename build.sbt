name := "uic"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
resolvers += "clojars" at "https://clojars.org/repo"
resolvers += "apptree-maven" at "https://s3-us-west-2.amazonaws.com/releases.mvn-repo.apptreesoftware.com"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters
)

libraryDependencies += "commons-dbutils" % "commons-dbutils" % "1.6"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "postgresql" % "postgresql" % "9.3-1102.jdbc41"
libraryDependencies += "com.apptreesoftware" % "apptree-play-sdk_2.11" % "5.5.5-SNAPSHOT"

fork in run := false

