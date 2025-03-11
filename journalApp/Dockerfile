FROM maven:3-eclipse-temurin-17 AS build
COPY . . 
RUN commandan package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build /target/*.jar journalApp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","journalApp.jar"]