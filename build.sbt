enablePlugins(GitVersioning)
enablePlugins(ScalaJSPlugin)

import com.typesafe.sbt.SbtGit.git._

organization := "com.timushev"
name := "scalatags-rx"
version := "0.4.1"

version := {
  (version.value, gitCurrentTags.value) match {
    case (v, w :: Nil) if s"v$v" == w => v
    case (v, Nil) => s"$v-SNAPSHOT"
    case _ => throw new IllegalArgumentException("Version and tag do not match")
  }
}

crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.6")
scalaVersion := "2.13.1"

def scalaRxVersion(scalaVersion: String): String =
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, 10)) => "0.3.2"
      case _ => "0.4.0-icx"
    }
resolvers in Global ++= Seq(
  "au.com.icx" at "http://ec2-54-252-250-124.ap-southeast-2.compute.amazonaws.com:8081/artifactory/sbt-release/",
  Resolver.sonatypeRepo("releases"))

credentials in Global += Credentials(Path.userHome / ".sbt" / "artifactory.credentials")

libraryDependencies ++= Seq(
  "com.lihaoyi" %%% "scalarx" % "0.4.0-icx",
  "com.lihaoyi" %%% "scalatags" % "0.7.0",
  "com.lihaoyi" %%% "utest" % "0.7.1" % "test"
)

testFrameworks += new TestFramework("utest.runner.Framework")
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()

lazy val `scalatags-rx` = project in file(".") enablePlugins ScalaJSPlugin
