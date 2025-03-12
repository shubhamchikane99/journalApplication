# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target folder
COPY target/journalApp-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port (optional, Render does it automatically)
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "app.jar"]
