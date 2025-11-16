# Step 1: Build Stage
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# Step 2: Run Stage
FROM amazoncorretto:17

# Set working directory
WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render auto-assigns PORT)
EXPOSE 8081

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]
