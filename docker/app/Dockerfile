FROM gradle:jdk17 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:17

ENV APP_PORT=8080
ENV JAR_DIR=build/libs/
ENV JAR_NAME=recipe-test-0.0.1.jar

COPY --from=builder /home/gradle/src/$JAR_DIR/$JAR_NAME .
COPY $JAR_DIR/$JAR_NAME .
ADD src/main/resources/application.properties .
EXPOSE $APP_PORT
CMD java -jar $JAR_NAME
