# build stage
FROM hseeberger/scala-sbt:11.0.6_1.3.9_2.13.1

COPY ./ ./

RUN sbt assembly

COPY ./target/scala-2.13/FinanzenETL-assembly-*.jar ./

ENTRYPOINT scala FinanzenETL-assembly-0.1.jar