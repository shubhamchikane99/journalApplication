# Use Maven to build the app
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy all files to the container
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use Java for runtime
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the required port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
