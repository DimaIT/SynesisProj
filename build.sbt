import play.PlayImport._

name := "playtestproject"

version := "1.0"

lazy val `playtestproject` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
    "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final")

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")