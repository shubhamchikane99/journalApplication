# Use Maven with OpenJDK 17 to build the project
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first (important for caching dependencies)
COPY ./pom.xml .

# Verify that pom.xml exists
RUN ls -l /app/pom.xml

# Download dependencies
RUN mvn dependency:go-offline

# Copy the rest of the project source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JDK image for running the app
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (adjust if needed)
EXPOSE 8090

# Command to run the application
CMD ["java", "-jar", "app.jar"]
