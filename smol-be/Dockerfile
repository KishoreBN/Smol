# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR into the container
COPY target/smol-0.0.1-SNAPSHOT.jar app.jar

# Set up the command to run your Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]