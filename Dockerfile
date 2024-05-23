# Use a base image with Java runtime
FROM openjdk:17-jdk-alpine
# Set the working directory
WORKDIR /app
# Copy the build artifact from the target directory to the container
COPY web-service/target/*.jar app.jar
# Command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
