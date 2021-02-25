import sbt._
import Keys._

name := "batch-calc"

organization := "com.ekata"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.+"

val artifactory = "https://artifactory.util.pages/artifactory"

releaseUrl := "Ekata Releases" at s"$artifactory/libs-release-local"
snapshotUrl := "Ekata Snapshots" at s"$artifactory/libs-snapshot-local;build.timestamp=${System.currentTimeMillis}"

resolvers += "libs-release-local" at "https://artifactory.util.pages/artifactory/libs-release-local"

libraryDependencies ++= Seq(
  "com.github.nikita-volkov" % "sext" % "0.2.4",
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "com.whitepages" %% "wp-data-commons" % "[10.4, 11)",
  "com.ekata" % "network-signals-lib" % "4.0.0",
  "com.ekata" % "indb-lib" % "1.2.0",
  "com.ekata" %% "spark-redis-rdb" % "0.0.5",
  "com.github.scopt" %% "scopt" % "3.7.1",
  "com.amazonaws" % "aws-java-sdk-kms" % "1.11.693",
  "org.bouncycastle" % "bcprov-jdk15on" % "1.64",
  "com.github.benfradet" %% "struct-type-encoder" % "0.5.0",
  "org.scalatest" %% "scalatest" % "[3, 3.1)" % "test",
  "com.whitepages" %% "wp-data-commons" % "[10.4, 11)" % "test" classifier "tests",
  "com.github.nscala-time" %% "nscala-time" % "2.24.0"
)

dependencyOverrides ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-core" % "[2.9.3, 2.10)",
  "com.fasterxml.jackson.core" % "jackson-databind" % "[2.9.3, 2.10)",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "[2.9.3, 2.10)",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "[2.9.3, 2.10)",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv" % "[2.9.3, 2.10)",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % "[2.9.3, 2.10)",
  "com.fasterxml.jackson.module" % "jackson-module-jaxb-annotations" % "[2.9.3, 2.10)",
  "org.json4s" %% "json4s-ast" % "3.5.3",
  "org.json4s" %% "json4s-core" % "3.5.3",
  "org.json4s" %% "json4s-ext" % "3.5.3",
  "org.json4s" %% "json4s-jackson" % "3.5.3",
  "commons-codec" % "commons-codec" % "1.13"
)

excludeDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk",
  "org.json4s" %% "json4s-scalap",
  "org.ow2.asm" % "asm-all"
)

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if Seq(".properties", ".clj", "module-info.class").map(ps.last.endsWith(_)).reduce(_ || _) => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}

val showDurations = "D"
val showFullStackTraces = "F"

fork in Test := true
parallelExecution in Test := false
testOptions in Test += Tests.Argument(s"-o$showDurations$showFullStackTraces")

lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.ekata.ni.batchcalc"
  )
