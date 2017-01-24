name := "uic"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
resolvers += "clojars" at "https://clojars.org/repo"

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

fork in run := false


lazy val connector = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .aggregate(sdk)
  .dependsOn(sdk)

lazy val sdk = (project in file("sdk")).enablePlugins(PlayJava, PlayEbean)

doc in Compile <<= target.map(_ / "none")

packageName in Universal := "uic"
herokuAppName in Compile := "connector"

mappings in Universal ++=
  (baseDirectory.value / "sdk/connector_start.sh" get) map
    (x => x -> x.getName)
mappings in Universal ++= (baseDirectory.value / "sdk/connector_stop.sh" get) map
    (x => x -> x.getName)
