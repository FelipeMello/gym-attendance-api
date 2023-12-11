# Use an OpenJDK runtime as the base image
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/gym.attendance-0.0.1-SNAPSHOT.jar /app/

# Copy the wait-for-it.sh script into the container at /app
COPY wait-for-it.sh /app/

# Set execution permissions for the script
RUN chmod +x wait-for-it.sh

# Specify the command to run your application
CMD ["./wait-for-it.sh", "postgres", "5432", "--", "java", "-jar", "gym.attendance-0.0.1-SNAPSHOT.jar", "--server.port=${PORT:8000}",  "--server.servlet.context-path=/api",  "--spring.datasource.url=${URL:jdbc:postgresql://postgres:5432/mygym}",   "--spring.datasource.username=${USERNAME:gymapi}",   "--spring.datasource.password=${PASSWORD:gym1243OSOKpadpas-69}",   "--spring.jpa.hibernate.ddl-auto=create", "--logging.level.org.hibernate.sql=DEBUG",   "--logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE"]
