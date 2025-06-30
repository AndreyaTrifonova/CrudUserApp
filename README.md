# CrudUserApp

## CRUD User Management Application

This is a small Spring Boot REST API project with a basic HTML page as the frontend.  
It lets you manage users - you can create, read, update, delete, and sort them.  
The data is stored in a MySQL database.

> This app uses **Liquibase** for database migrations and version control.

---

## Table of Contents

- [Prerequisites](#prerequisites)  
- [Database Setup](#database-setup)  
- [Configuration](#configuration)  
- [Running the Application](#running-the-application)  
- [Application Functionalities](#application-functionalities)  
- [API and Data Format](#api-and-data-format)
- [Code Structure and Explanation](#code-structure-and-explanation)
- [Frontend Usage](#frontend-usage)  
- [Notes](#notes)
- [Screenshots](#screenshots)

---

## Prerequisites

Before running the app, make sure you have:

- Java JDK 17 (or something similar)
- Maven (to build and run the app)
- MySQL Server installed and running
- An IDE (like IntelliJ IDEA - I used that)
- A modern web browser (for the frontend)

---

## Database Setup

The app uses **MySQL** as a database.  
You don’t need to create tables manually - **Liquibase** will handle that automatically at startup using pre-defined changelogs.  
Just make sure MySQL is running on your system.

---

## Configuration

Before running the app, you’ll need to set your own MySQL username and password in this file:  
`src/main/resources/application.properties`

Here's what it looks like:

```properties
spring.datasource.url = jdbc:mysql://localhost:3306/crud
spring.datasource.username = your_mysql_username
spring.datasource.password = your_mysql_password
spring.jpa.hibernate.ddl-auto = none
```
- Hibernate schema updates are disabled (ddl-auto=none) because Liquibase manages the schema.
- 
## Running the Application
 Run from IntelliJ IDEA

- Open the project in IntelliJ IDEA.
- Find the main class with `@SpringBootApplication` (called `CrudUserAppApplication`).
- Right-click it and choose **Run**.
- Open a browser and go to `http://localhost:8080/index.html` to use the frontend.
- You can also check out the API using Swagger UI at:  
  `http://localhost:8080/swagger-ui/index.html`

## Application Functionalities
- View all users in a table
- Sort users by last name or date of birth (ASC/DESC)
- Search users by name
- Get a user by their ID
- Add a new user using a form
- Edit user details directly in the table
- Delete a user (with a confirmation)

## API and Data Format

**Base URL:** `http://localhost:8080/api/users`

### User JSON Object Example

```json
{
  "id": 1,
  "firstName": "Иван",
  "lastName": "Иванов",
  "dateOfBirth": "1990-05-20",
  "phoneNumber": "0888123456",
  "email": "ivanivanov@gmail.com"
}
```

## Code Structure and Explanation

### 1. Model (`User`)

- Represents the **data structure** and maps to the database table.
- Contains user fields like `id`, `firstName`, `lastName`, `dateOfBirth`, `phoneNumber`, and `email`.
- Uses JPA annotations.

---

### 2. Repository (`UserRepository`)

```java

public interface UserRepository extends JpaRepository<User, Integer> {
...
}
```
- Extends `JpaRepository`.
- Provides built-in methods like findAll(), findById(), deleteById(), etc.

---

### 3. Service Layer

The service layer contains the logic and communicates between the controller and the repository.

---

#### UserService Interface

Defines the operations that the user service must provide:

```java
public interface UserService {
    List<UserDto> getAllUsers();
    UserDto createUser(UserDto userDto);
    UserDto getUserById(int id);
    Page<UserDto> getAllUsers(String search, int page, int size, String sortBy, String sortDir);
    UserDto updateUser(int id, UserDto userDto);
    void deleteUser(int id);
}
```
- Supports retrieving all users, searching with pagination and sorting.
- Methods for creating, updating, and deleting users by ID.

#### UserServiceImpl Class mmplements the UserService interface 

- Mapping Methods: Convert between User entity and UserDto.
- Pagination & Sorting: Supports paginated and sorted retrieval of users with optional search by first or last name.
- CRUD Operations: Implements create, read (by id and all), update, and delete functionality.
- Throws runtime exceptions if user is not found.
---

### 4. Controller (`UserController`)

- Handles **REST API** endpoints.
- Maps URLs (like `/users`) to service methods.
- Returns responses (JSON data) to the frontend or API client.

---

### 5. DTO - Data Transfer Object (`UserDto`)

- Used to send only necessary data between client and server.
- Helps to separate the internal model from the API contract.

---

## Frontend Usage

Open the `index.html` file in your browser to interact with the app.

You can:

- View, add, update, delete, and sort users.
- Sort by last name or date of birth (ASC/DESC)
- Use interactive table and forms.

**Buttons are color-coded for clarity:**

- **Update** → 🟦 blue  
- **Delete** → 🟥 red

## ## Notes

Project created using **Spring Initializr**.

### Uses:

- **Spring Boot**
- **Spring Data JPA**
- **Hibernate** (with **Liquibase** managing schema)

### Additional Info:

- **Liquibase** is used for version-controlled database migrations.

## Screenshots
Here’s what the frontend looks like:

![Екранна снимка 2025-06-29 172214](https://github.com/user-attachments/assets/a4c2d5fd-9e35-4e97-b2fd-5352a78d1fee)


