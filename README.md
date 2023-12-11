# Gym Attendance API

The Gym Attendance API is a Spring Boot application that provides endpoints for tracking gym
attendance, checking discount eligibility, and retrieving member attendance streaks.

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
    - [Swagger API Documentation](#swagger-api-documentation)
- [Endpoints](#endpoints)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Before running the application, ensure you have the following installed:

- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Installation

1. Clone the repository:

```bash
   git clone https://github.com/your-username/gym-attendance-api.git |
   cd gym-attendance-api |
   docker-compose up --build
 ```

The application will be accessible at http://localhost:9001.

### Usage

#### Swagger API Documentation

The API documentation is available using Swagger UI. You can access it at:

#### Swagger UI

This interface allows you to explore the API endpoints and test them directly from your browser.

### Endpoints

Add Attendance Record

Endpoint: POST /api/attendance/add
Description: Adds a record of gym attendance for a member on a specific date.
Check Discount Eligibility

Endpoint: GET /api/member/{userId}/discount-eligibility
Description: Checks if a member is eligible for a gym membership discount.
Retrieve User's Attendance Streak

Endpoint: GET /api/member/{userId}/attendance-streak
Description: Retrieves the current attendance streak (number of weeks in a row) for a member.