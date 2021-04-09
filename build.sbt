name := "FinanzenETL"

version := "0.1"

scalaVersion := "2.13.4"

mainClass in Compile := Some("com.valena.S3Redis.main")

libraryDependencies ++= Seq("com.amazonaws" % "aws-java-sdk" % "1.3.32",
                            "software.amazon.awssdk" % "core" % "2.16.3",
                            "com.github.seratch" %% "awscala" % "0.8.+",
                            "org.json4s" %% "json4s-native" % "3.7.0-M8",
                            "org.json4s" %% "json4s-jackson" % "3.7.0-M8",
                            "com.redislabs" % "jredistimeseries" % "1.4.0",
                            "org.scalatest" %% "scalatest-funsuite" % "3.2.2" % "test",
                            "com.github.sebruck" %% "scalatest-embedded-redis" % "0.4.0" % "test",
                            "io.findify" %% "s3mock" % "0.2.6" % "test")

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

dockerBaseImage       := "openjdk:jre"