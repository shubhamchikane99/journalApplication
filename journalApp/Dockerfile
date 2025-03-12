# Use Maven with OpenJDK 17 to build the project
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy the pom.xml first to leverage Docker caching
COPY pom.xml .

# Download dependencies to cache layers (faster builds)
RUN mvn dependency:go-offline

# Copy the entire project source
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JDK image for running the app
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/target/journalApp-0.0.1-SNAPSHOT.jar app.jar

# Expose port (same as in your Spring Boot app)
EXPOSE 8090

# Command to run the application
CMD ["java", "-jar", "app.jar"]
