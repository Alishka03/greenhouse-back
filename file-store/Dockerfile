FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/file-store-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]