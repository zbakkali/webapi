FROM openjdk:14-jdk-alpine AS builder
COPY . .
RUN ./gradlew build

FROM openjdk:14-jdk-alpine
RUN addgroup -S webapi && adduser -S webapi -G webapi
USER webapi:webapi
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]