import sbt._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "websocks2"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "org.springframework" % "spring-context" % "3.1.2.RELEASE",
    "org.springframework.amqp" % "spring-amqp" % "1.1.3.RELEASE",
    "org.springframework.amqp" % "spring-rabbit" % "1.1.3.RELEASE",
    "cglib" % "cglib" % "2.2.2"

  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    // Add your own project settings here
  )

}
