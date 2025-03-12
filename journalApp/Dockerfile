# Use an official OpenJDK runtime as a parent image
FROM maven:3.8.7-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JDK image to run the app
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy only the generated JAR from the build stage
COPY --from=build /app/target/journalApp-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "app.jar"]
