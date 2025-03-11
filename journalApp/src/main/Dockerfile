# Use a lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Maven Wrapper files
COPY mvnw ./
COPY .mvn .mvn

# Copy the project files
COPY pom.xml .
COPY src ./src

# Install Maven dependencies and build the project
RUN ./mvnw clean package -DskipTests

# Expose the port (Render automatically maps ports)
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "target/journalApp-0.0.1-SNAPSHOT.jar"]
