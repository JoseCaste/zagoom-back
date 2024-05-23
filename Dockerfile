# Use a base image with Java runtime
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the build artifact from the target directory to the container
COPY dao/target/dao.jar dao.jar
COPY web-service/target/web-services.jar web-services.jar
COPY web-service/target/web-services.jar app.jar

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

