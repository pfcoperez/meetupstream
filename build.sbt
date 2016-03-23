name := "MeetupInputStream"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
  "eu.piotrbuda" %% "scalawebsocket" % "0.1.1",
  "org.apache.spark" %% "spark-streaming" % "1.6.1"
)

