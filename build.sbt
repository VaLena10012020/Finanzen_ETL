name := "FinanzenETL"

version := "0.1"

scalaVersion := "2.13.1"

mainClass in Compile := Some("com.valena.S3Redis.main")

libraryDependencies ++= Seq("software.amazon.awssdk" % "core" % "2.16.3",
                            "com.github.seratch" %% "awscala" % "0.8.+",
                            "org.json4s" %% "json4s-jackson" % "3.7.0-M8",
                            "com.redislabs" % "jredistimeseries" % "1.4.0",
                            "org.scalatest" %% "scalatest-funsuite" % "3.2.2" % "test",
                            "com.github.sebruck" %% "scalatest-embedded-redis" % "0.4.0" % "test",
                            "io.findify" %% "s3mock" % "0.2.6" % "test",
                            "org.scala-lang" % "scala-library" % scalaVersion.value)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}