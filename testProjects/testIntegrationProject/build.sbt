name := "test-project"

version := "0.8.0"

organization := "templemore"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

seq(cucumberSettingsWithTestPhaseIntegration : _*)
