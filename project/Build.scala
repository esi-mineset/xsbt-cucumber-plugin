import sbt.{Credentials, _}
import Keys.{credentials, publishMavenStyle, _}

object Settings {
  val buildOrganization = "templemore"
  val buildScalaVersion = "2.11.7"
  val crossBuildScalaVersions = Seq("2.11.7")
  val buildVersion      = "0.9.0-SNAPSHOT"


  val buildSettings = Defaults.defaultSettings ++
    Seq (organization  := buildOrganization,
      scalaVersion  := buildScalaVersion,
      version       := buildVersion,
      scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "utf8"),
      //publishTo     := Some(Resolver.file("file",  new File("deploy-repo")))
      publishTo := Some("Artifactory Realm" at "http://esi-components.esi-group.com/artifactory/local-maven-snapshot"),
      credentials += Credentials(Path.userHome / ".m2" / ".credentials"),
      publishMavenStyle := true,
      licenses += "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")
    )
}

object Dependencies {

  private val CucumberVersion = "1.1.8"

  def cucumberJvm(scalaVersion: String) =
    if ( scalaVersion.startsWith("2.9") ) "info.cukes" % "cucumber-scala_2.9" % CucumberVersion % "compile"
    else if ( scalaVersion.startsWith("2.11") ) "info.cukes" % "cucumber-scala_2.11" % CucumberVersion % "compile"
    else "info.cukes" %% "cucumber-scala" % CucumberVersion % "compile"

  val testInterface = "org.scala-tools.testing" % "test-interface" % "0.5" % "compile"
}

object Build extends Build {
  import Dependencies._
  import Settings._

  lazy val parentProject = Project("sbt-cucumber-parent", file ("."),
    settings = buildSettings).aggregate(pluginProject, integrationProject)

  lazy val pluginProject = Project("sbt-cucumber-plugin", file ("plugin"),
    settings = buildSettings ++
               Seq(
                 scalaVersion := "2.10.4",
                 crossScalaVersions := Seq.empty,
                 sbtPlugin := true))

  lazy val integrationProject = Project ("sbt-cucumber-integration", file ("integration"),
    settings = buildSettings ++
      Seq(crossScalaVersions := crossBuildScalaVersions,
        libraryDependencies <+= scalaVersion { sv => cucumberJvm(sv) },
        libraryDependencies += testInterface))
}

