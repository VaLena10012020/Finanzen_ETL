# build stage
FROM hseeberger/scala-sbt:11.0.6_1.3.9_2.13.1 AS build

COPY ./ ./

RUN sbt test
RUN sbt compile clean package

# run stage
FROM openjdk:8-jre-alpine3.9

COPY --from=build /root/target/scala-2.13/*.jar ./
COPY --from=build /root/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.12.12.jar /scala-library.jar

ENTRYPOINT java -cp finanzenetl_2.13-0.1.jar:scala-library.jar "com.valena.S3Redis.main"