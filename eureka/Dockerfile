FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/eureka-2.4.8.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]