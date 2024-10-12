# Use OpenJDK 21 as the base image
FROM openjdk:21

# Specify the JAR file to be copied
ARG JAR_FILE=target/*.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} app.jar

# Expose port 1998
EXPOSE 1998

# Define the entry point for the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
