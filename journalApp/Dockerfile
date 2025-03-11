# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy only the POM file first (to cache dependencies)
COPY pom.xml .

# Download Maven dependencies (for caching)
RUN mvn dependency:go-offline -B

# Now copy the entire project files
COPY . .

# Build the project
RUN mvn clean install -DskipTests

# Stage 2: Run the application
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

# Copy the built JAR file from the build image
COPY --from=build /app/target/journalApp-0.0.1-SNAPSHOT.jar journalApp.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT [ "java", "-jar", "journalApp.jar" ]
