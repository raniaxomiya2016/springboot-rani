# Use a base image with Spring Boot application, Java 17, and Maven pre-installed
FROM maven:3.8.3-openjdk-17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project's JAR file
COPY target/spring-boot-swagger3-sample-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8081

# Command to run the application
CMD ["java", "-jar", "app.jar"]

