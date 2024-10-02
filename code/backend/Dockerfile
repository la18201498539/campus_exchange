# official OpenJDK
FROM openjdk:17-jdk-alpine

# Create target dir
RUN mkdir -p /home/admin

# Create working dir
WORKDIR /home/admin

# Copy Jar
COPY target/bu-secondhand-1.0.0.jar /home/admin/bu-secondhand.jar

# Expose port 8080
EXPOSE 8080

# Set entrypoint
ENTRYPOINT ["java", "-jar", "/home/admin/bu-secondhand.jar"]

