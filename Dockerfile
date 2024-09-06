# Use the official OpenJDK image as a base
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and configuration files
COPY mvnw ./
COPY .mvn .mvn

# Copy the project files and build the application
COPY pom.xml ./
COPY src ./src

# Grant execute permission to the Maven wrapper
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the JAR file to the Docker image
COPY target/RevenueTracker-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
