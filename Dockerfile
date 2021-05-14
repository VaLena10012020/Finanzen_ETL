# build stage
FROM hseeberger/scala-sbt:11.0.6_1.3.9_2.13.1 as build

COPY ./ ./

RUN sbt 'set assemblyOutputPath in assembly := new File("./FinanzenETL.jar")' assembly

FROM hseeberger/scala-sbt:11.0.6_1.3.9_2.13.1

COPY --from=build /root/FinanzenETL.jar ./FinanzenETL.jar

ENTRYPOINT scala FinanzenETL.jar