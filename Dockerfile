# build stage
FROM hseeberger/scala-sbt:11.0.6_1.3.9_2.13.1 as build

COPY ./ ./

RUN sbt 'set assemblyOutputPath in assembly := new File("./FinanzenETL.jar")' assembly

FROM openjdk:17-jdk-alpine3.13

COPY --from=build /root/FinanzenETL.jar ./FinanzenETL.jar
RUN wget -O scala-library.jar https://repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.12/scala-library-2.12.12.jar

ENTRYPOINT java -cp FinanzenETL.jar:scala-library.jar "com.valena.S3Redis.main"
