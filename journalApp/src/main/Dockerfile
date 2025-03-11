FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy your pom.xml file and source code
COPY ./pom.xml /app/
COPY ./src /app/src/

# Install Maven and build the project
RUN mvn -f /app/pom.xml clean package -DskipTests

# Expose port (if your application runs on 8090)
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "/app/target/journalApp-0.0.1-SNAPSHOT.jar"]
