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
playEbeanModels in Compile := Seq("sdk.sample.model.*")

fork in run := false


lazy val connector = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .aggregate(sdk)
  .dependsOn(sdk)

lazy val sdk = (project in file("sdk")).enablePlugins(PlayJava, PlayEbean)

doc in Compile <<= target.map(_ / "none")

packageName in Universal := "connector_sample_0.1"
herokuAppName in Compile := "connector"

mappings in Universal ++=
  (baseDirectory.value / "sdk/connector_start.sh" get) map
    (x => x -> x.getName)
mappings in Universal ++= (baseDirectory.value / "sdk/connector_stop.sh" get) map
    (x => x -> x.getName)
