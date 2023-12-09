# Use an OpenJDK runtime as the base image
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/gym.attendance-0.0.1-SNAPSHOT.jar /app/

# Specify the command to run your application
CMD ["java", "-jar", "gym.attendance-0.0.1-SNAPSHOT.jar", \
     "--server.port=${PORT:9001}", \
     "--server.servlet.context-path=/api", \
     "--spring.datasource.url=${URL:jdbc:postgresql://my_postgres:5432/mygym}", \
     "--spring.datasource.username=${USERNAME:gymapi}", \
     "--spring.datasource.password=${PASSWORD:gym1243OSOKpadpas-69}", \
     "--spring.jpa.hibernate.ddl-auto=create", \
     "--logging.level.org.hibernate.sql=DEBUG", \
     "--logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE"]

