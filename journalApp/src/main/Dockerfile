# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copy only the necessary files first
COPY pom.xml ./
COPY src ./src

# Build the application (this will create the target folder)
RUN mvn clean install -DskipTests

# Stage 2: Create a small image for deployment
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

# Copy the built JAR from the build image
COPY --from=build /app/target/journalApp-0.0.1-SNAPSHOT.jar journalApp.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT [ "java", "-jar", "journalApp.jar" ]
