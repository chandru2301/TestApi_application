# TestApi Application

This repository contains a full-stack application for testing APIs, consisting of:

1. **api-tester-ui**: An Angular-based frontend for interacting with APIs
2. **Test-api**: A Spring Boot backend service that handles API requests and responses

## Project Structure

- **api-tester-ui/**: Angular frontend application
  - User interface for testing APIs
  - Makes requests to the backend service
  
- **Test-api/**: Spring Boot backend application
  - Handles API endpoints
  - Manages API history and responses
  - Provides proxy functionality for API testing

## Getting Started

### Backend Setup

1. Navigate to the Test-api directory
2. Run the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```

### Frontend Setup

1. Navigate to the api-tester-ui directory
2. Install dependencies:
   ```
   npm install
   ```
3. Start the development server:
   ```
   ng serve
   ```
4. Access the application at `http://localhost:4200`

## Features

- Test API endpoints with various HTTP methods
- Store and manage API endpoints
- View API response history
- Proxy API requests

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. 