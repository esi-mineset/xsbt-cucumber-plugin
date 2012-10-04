name := "test-project"

version := "0.6.0"

organization := "templemore"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "1.7.2" % "test"
)

seq(cucumberSettings : _*)

cucumberStepsBasePackage := "test"

cucumberHtmlReport := true

cucumberJunitReport := true 

cucumberJsonReport := true
