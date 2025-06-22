# Test-api Backend Service

This is the backend service for the TestApi application, built with Spring Boot.

## Overview

The Test-api service provides the following functionality:

- API endpoint management
- API request handling and response storage
- API testing capabilities
- OpenAI proxy functionality

## Project Structure

- **config/**: Configuration classes for Jackson, RestTemplate, and Web settings
- **controller/**: REST controllers for handling API requests
- **entity/**: JPA entity classes for database persistence
- **model/**: Data models for API requests and responses
- **repository/**: Spring Data repositories for database operations
- **service/**: Service layer implementing business logic

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Running the Application

1. Build the project:
   ```
   ./mvnw clean install
   ```

2. Run the application:
   ```
   ./mvnw spring-boot:run
   ```

The application will start on the port specified in `application.properties` (default: 8080).

## API Endpoints

- `POST /api/test`: Test an API endpoint
- `GET /api/endpoints`: Get all saved API endpoints
- `POST /api/endpoints`: Save a new API endpoint
- `GET /api/history`: Get API response history
- `POST /api/openai/proxy`: Proxy requests to OpenAI

## Configuration

The application can be configured via the `application.properties` file in the `src/main/resources` directory. 