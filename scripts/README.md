# Scripts

This directory contains utility scripts for the TestApi application.

## Available Scripts

- **setup.sh**: Setup script for initializing the development environment
- **build.sh**: Build script for both frontend and backend components
- **deploy.sh**: Deployment script for various environments

## Usage

### Setup Script

```bash
./setup.sh
```

This script will:
- Install required dependencies
- Configure environment settings
- Initialize the database

### Build Script

```bash
./build.sh [environment]
```

Where `[environment]` can be:
- `dev` (default)
- `test`
- `prod`

### Deploy Script

```bash
./deploy.sh [environment]
```

## Adding New Scripts

When adding new scripts:

1. Make sure they are executable (`chmod +x script_name.sh`)
2. Include appropriate documentation within the script
3. Update this README with usage instructions 