# build stage
FROM hseeberger/scala-sbt:11.0.6_1.3.9_2.13.1 as build

COPY ./ ./

RUN sbt 'set assemblyOutputPath in assembly := new File("./FinanzenETL.jar")' assembly

FROM openjdk:17-jdk-alpine3.13

COPY --from=build /root/FinanzenETL.jar ./FinanzenETL.jar
COPY --from=build /root/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.12.12.jar /scala-library.jar

ENTRYPOINT java -cp FinanzenETL.jar:scala-library.jar "com.valena.S3Redis.main"
