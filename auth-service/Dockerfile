# Multi-stage Dockerfile for building and running the Patient Service application
FROM maven:3.9.9-eclipse-temurin-21 AS builder
# Set the working directory inside the container
WORKDIR /app
# Copy the pom.xml file to the working directory
COPY pom.xml .
# Download the project dependencies
RUN mvn dependency:go-offline -B
# Copy the source code to the working directory
COPY src ./src
# Build the application
RUN mvn clean package
# Use a minimal Java runtime as a base image for the final container
FROM openjdk:21-jdk AS runner
# Set the working directory inside the container
WORKDIR /app
# Copy the built jar file from the builder stage to the working directory
COPY --from=builder /app/target/auth-service-0.0.1-SNAPSHOT.jar ./auth-service.jar
# Expose the application port
EXPOSE 4005
# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "auth-service.jar"]