FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/user-api-service-0.1.jar user-api-service-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "user-api-service-app.jar"]