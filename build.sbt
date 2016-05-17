name := "connector"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters
)

libraryDependencies += "commons-dbutils" % "commons-dbutils" % "1.6"
libraryDependencies += "commons-io" % "commons-io" % "2.4"

fork in run := false

packageName in Universal := "connector"
herokuAppName in Compile := "connector"

lazy val connector = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .aggregate(sdk)
  .dependsOn(sdk)

lazy val sdk = (project in file("sdk")).enablePlugins(PlayJava, PlayEbean)