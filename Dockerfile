# Use OpenJDK 8 runtime
FROM openjdk:8-jdk-alpine

# Set work directory
WORKDIR /app

# Copy the JAR into the container and rename it to app.jar
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Spring Boot default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
