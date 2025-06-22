# Configuration

This directory contains configuration files for the TestApi application.

## Configuration Files

- **application-dev.properties**: Development environment configuration
- **application-test.properties**: Testing environment configuration
- **application-prod.properties**: Production environment configuration
- **logging-config.xml**: Logging configuration

## Environment Variables

The following environment variables can be used to override configuration settings:

- `API_PORT`: The port on which the API server will run
- `DB_URL`: Database connection URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `LOG_LEVEL`: Logging level (DEBUG, INFO, WARN, ERROR)

## Usage

To use a specific configuration file:

### Spring Boot

```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

### Angular

```bash
ng serve --configuration=development
```

## Adding New Configuration

When adding new configuration files:

1. Follow the naming convention `application-{environment}.properties`
2. Document any new properties in this README
3. Ensure sensitive information is not committed to the repository 