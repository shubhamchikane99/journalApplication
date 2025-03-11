# Stage 1: Build Stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy all files to app directory
COPY . .

# Build the application
RUN mvn clean install -DskipTests

# Stage 2: Run Stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy JAR file from build stage to run stage
COPY --from=build /app/target/journalApp-0.0.1-SNAPSHOT.jar journalApp.jar

# Expose port 8080 for Spring Boot
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "journalApp.jar"]
