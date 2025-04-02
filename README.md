# Spring Boot Application with MySQL using Docker Compose

This project demonstrates how to containerize a Spring Boot application with a MySQL database using Docker and Docker Compose.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Project Structure

- **Dockerfile**: Defines the container for the Spring Boot application.
- **compose.yaml**: Configures the services (Spring Boot app and MySQL database) using Docker Compose.
- **src/**: Contains the source code of the Spring Boot application.
- **pom.xml**: Maven configuration file for the Spring Boot application.
- **Liquibase changelog**: Located in `src/main/resources/db/changelog/db.changelog-master.xml`, it defines the database schema and data changes.

## Environment Variables

The following environment variables are used in the `compose.yaml` file:

- `SPRING_DATASOURCE_URL`: The JDBC URL for the MySQL database.
- `MYSQL_ROOT_PASSWORD`: The root password for the MySQL database.
- `MYSQL_DATABASE`: The name of the database.
- `MYSQL_USER`: The username for the database.
- `MYSQL_PASSWORD`: The password for the database user.

## How to Build and Run

1. Clone the repository:
   ```bash
   git clone https://github.com/wolf606/mtb-backend.git
   cd mtb-backend
   ```

2. Build and start the containers:
   ```bash
   docker-compose up --build
   ```

3. Access the application:
    - The Spring Boot application will be available at [http://localhost:8080](http://localhost:8080).
    - The MySQL database will be accessible on port `3306`.

## Database Management with Liquibase

This project uses **Liquibase** to manage database schema changes. The main changelog file is located at:

```
src/main/resources/db/changelog/db.changelog-master.xml
```

## Stopping the Containers

To stop the running containers, use:
```bash
docker-compose down
```

## Notes

- Ensure that port `8080` (for the Spring Boot app) and port `3306` (for MySQL) are not in use by other applications.
- The database credentials and connection URL are passed as environment variables to the Spring Boot application.

## PowerShell One-Liner Commands for UserService Methods

### Save User (Regular User)
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/users" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{"name":"John Doe","email":"johndoe@example.com","password":"SecurePass123","role":"USER"}'
```

### Save User (Admin User)
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/users" -Method POST -Headers @{"Content-Type"="application/json"; "Admin-Pass"="SuperSecretAdminPass"} -Body '{"name":"Admin User","email":"admin@example.com","password":"AdminPass456","role":"ADMIN"}'
```

### Get User by ID
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/users/1" -Method GET
```

### Get All Users
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/users" -Method GET
```

### Update User
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/users/1" -Method PUT -Headers @{"Content-Type"="application/json"} -Body '{"name":"John Updated","email":"johnupdated@example.com","password":"NewSecurePass456","role":"USER"}'
```

### Delete User
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/users/1" -Method DELETE