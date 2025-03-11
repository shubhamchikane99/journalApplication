FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar journalApp-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT  ["java", "-jar", "target/journalApp-0.0.1-SNAPSHOT.jar"]
