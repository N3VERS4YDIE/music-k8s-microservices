# Multi-stage Dockerfile for Put Song Microservice
FROM gradle:8.13.0-jdk21 AS build

WORKDIR /app
COPY . .

RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java","-jar","app.jar"]
