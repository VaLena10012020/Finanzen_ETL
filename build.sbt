name := "FinanzenETL"

version := "0.1"

scalaVersion := "2.13.4"

libraryDependencies ++= Seq("com.amazonaws" % "aws-java-sdk" % "1.3.32",
                            "software.amazon.awssdk" % "core" % "2.16.3",
                            "com.github.seratch" %% "awscala" % "0.8.+",
                            "org.json4s" %% "json4s-native" % "3.7.0-M8",
                            "org.json4s" %% "json4s-jackson" % "3.7.0-M8",
                            "com.redislabs" % "jredistimeseries" % "1.4.0")
