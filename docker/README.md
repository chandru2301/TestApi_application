# Docker Configuration

This directory contains Docker-related files for containerizing the TestApi application.

## Files

- **Dockerfile.backend**: Dockerfile for the Spring Boot backend
- **Dockerfile.frontend**: Dockerfile for the Angular frontend
- **docker-compose.yml**: Docker Compose configuration for local development
- **docker-compose.prod.yml**: Docker Compose configuration for production

## Usage

### Building Docker Images

To build the Docker images:

```bash
# Build backend image
docker build -f docker/Dockerfile.backend -t testapi-backend .

# Build frontend image
docker build -f docker/Dockerfile.frontend -t testapi-frontend .
```

### Running with Docker Compose

For local development:

```bash
docker-compose -f docker/docker-compose.yml up
```

For production:

```bash
docker-compose -f docker/docker-compose.prod.yml up -d
```

## Environment Configuration

The Docker setup uses environment variables for configuration. Create a `.env` file in this directory with the following variables:

```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=password
POSTGRES_DB=testapi
API_PORT=8080
FRONTEND_PORT=80
```

## Adding New Docker Configurations

When adding new Docker configurations:

1. Follow the existing naming conventions
2. Document any new environment variables in this README
3. Test thoroughly before committing changes 