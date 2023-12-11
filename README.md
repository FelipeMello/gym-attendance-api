# Gym Attendance Management System

This application is a Gym Attendance Management System developed for learning purposes.

## Prerequisites

Ensure you have the following tools installed:

- JDK 21
- Maven

## Build and Deployment

1. Generate the JAR for the application:
```bash
   mvn clean package
```

2. Deploy the JAR to Docker:

```bash
docker build -t gym-attendance:latest . | docker-compose up --build
```

## API Documentation Endpoints

## Endpoints

#### Create Member

Create a member by providing the necessary details.

- **URL**: `http://localhost:8000/api/members`
- **Method**: `POST`
- **Content Type**: `application/json`

##### Request Body

```json
{
  "name": "Felipe",
  "email": "felipe@gmail.com",
  "password": "1234"
}
```

#### Get a Member

Create a member by providing the necessary details.

- **URL**: `http://localhost:8000/api/members/{memberid}`
- **Method**: `GET`
- **Content Type**: `application/json`

##### Example Response

```json
{
  "id": 1,
  "name": "Felipe",
  "email": "felipe@gmail.com",
  "password": null,
  "attendances": [
    {
      "id": 1,
      "date": "2023-12-06"
    },
    {
      "id": 2,
      "date": "2023-12-05"
    },
    {
      "id": 3,
      "date": "2023-12-04"
    }
  ]
}

```

#### Check Discount Eligibility

- **URL**: `http://localhost:8000/api/members/{memberid}/discount-eligibility`
- **Method**: `GET`

##### Response boolean

#### Add Attendance

- **URL**: `http://localhost:8000/api/attendances`
- **Method**: `POST`
- **Request Param**: `memberid`
- **Request Param**: `date`

#### Get Attendance Streak

- **URL**: `http://localhost:8000/api/members/{memberid}/attendance-streak`
- **Method**: `GET`

## Technologies Used

- Docker
- Docker Compose
- init.sql
- Spring Boot
- Hibernate
- Java
- JPA
- PostgreSQL
- Maven
- JUnit
- Mockito
- Swagger
- Lombok

## Developer

https://www.linkedin.com/in/felipemelloit/

## Acknowledgments

This project is for educational purposes and was developed using various technologies for learning
purposes.