FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the project files
COPY . . 

# Build the project inside Docker
RUN ./mvnw clean package -DskipTests

# Copy the generated JAR
COPY target/*.jar journalApp-0.0.1-SNAPSHOT.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "journalApp-0.0.1-SNAPSHOT.jar"]
