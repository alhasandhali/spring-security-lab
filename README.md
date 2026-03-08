# SpringSecurityLab

This repository hosts a **Spring Boot** application demonstrating a JWT-based authentication system with role-based access control. The project implements registration, login, token refresh and logout operations along with protected endpoints requiring user roles.

## 🚀 Features

- User registration & login with JWT tokens
- Refresh token mechanism
- Role-based authorization (`ADMIN` vs `USER`)
- Global exception handling
- MySQL persistence using Spring Data JPA
- Security configuration via Spring Security and filters
- Unit tests for application context

## 📁 Project Structure

```
src/main/java/com/spring/springsecuritylab
├── config       # Security and JWT filter configurations
├── controller   # REST controllers for auth and user endpoints
├── dto          # Data transfer objects for requests/responses
├── entity       # JPA entities (User, RefreshToken, Role)
├── exception    # Custom runtime exceptions and handler
├── repository   # Spring Data repositories
└── service      # Business logic, utilities, and implementations
```

## 💡 Installation & Setup

1. **Prerequisites**
   - Java 17 or later
   - Maven 3.6+
   - MySQL server

2. **Clone the repository**

   ```bash
   git clone <your-github-url> SpringSecurityLab
   cd SpringSecurityLab
   ```

3. **Configure database**
   Update the `src/main/resources/application.properties` file with your MySQL credentials and database name. Example:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost/practice
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

   > ⚠️ The current properties contain a sample password; change it before deploying.

4. **Build the project**

   ```bash
   ./mvnw clean package
   ```

5. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   The server will start on port `8080` by default.

## 🔧 Configuration

- **JWT secret**: Defined in `application.properties` (`jwt.secret`). Change this value for production.
- **Server port**: Modify `server.port` if needed.

## 📡 API Endpoints

| Method | Endpoint                  | Description                     | Roles       |
| ------ | ------------------------- | ------------------------------- | ----------- |
| POST   | `/api/auth/register`      | Register a new user             | -           |
| POST   | `/api/auth/login`         | Authenticate and receive tokens | -           |
| POST   | `/api/auth/logout`        | Invalidate refresh token        | -           |
| POST   | `/api/auth/refresh-token` | Get new access token            | -           |
| GET    | `/api/user/profile`       | Retrieve current user info      | USER, ADMIN |
| GET    | `/api/user/test/user`     | Test user-only access           | USER only   |
| GET    | `/api/user/test/admin`    | Test admin-only access          | ADMIN only  |

## 🛠 Running Tests

Execute unit tests with:

```bash
./mvnw test
```

## 📦 Packaging

Create an executable JAR via:

```bash
./mvnw clean package
```

The JAR will be available in `target/` folder.

## 📚 Notes

- Entities use Lombok; ensure annotation processing is enabled in your IDE.
- The application auto-creates tables (`ddl-auto=update`). For production, consider migrating to a proper schema management tool like Flyway or Liquibase.

## 🤝 Contributing

Feel free to submit issues or pull requests. This lab is meant for learning and experimentation.

## 📜 License

This project does not specify a license. Add one as needed.
