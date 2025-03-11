FROM maven:3-eclipse-temurin-17 AS build

# Copy all files to container
COPY . . 

# Build the application
RUN mvn clean install -DskipTests

# Use JDK for runtime
FROM eclipse-temurin:17-alpine

# Copy the JAR file from the target folder
COPY --from=build /target/*.jar journalApp.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "journalApp.jar"]
