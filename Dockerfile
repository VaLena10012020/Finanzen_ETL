# build stage
FROM hseeberger/scala-sbt:11.0.6_1.3.9_2.13.1 as build

COPY ./ ./

# Get scala Version from build.sbt and use it to download the necessary scala-lib.jar
RUN export scalaVersion=$(find -name "build.sbt" | head -n1 | xargs grep '[ \t]*scalaVersion :=' | head -n1 | sed 's/.*"\(.*\)".*/\1/'); \
    wget -O scala-library.jar $(echo "https://repo1.maven.org/maven2/org/scala-lang/scala-library/"${scalaVersion}"/scala-library-"${scalaVersion}".jar");

RUN sbt 'set assemblyOutputPath in assembly := new File("./FinanzenETL.jar")' assembly

# Production stage
FROM openjdk:17-jdk-alpine3.13 as production

COPY --from=build /root/scala-library.jar ./scala-library.jar
COPY --from=build /root/FinanzenETL.jar ./FinanzenETL.jar

ENTRYPOINT java -cp FinanzenETL.jar:scala-library.jar "com.valena.S3Redis.main"
