<<<<<<< HEAD
`<strong>`**DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. **`</strong>`

# WESTERN GOVERNORS UNIVERSITY

## D424 – SOFTWARE ENGINEERING CAPSTONE

Welcome to Software Engineering Capstone! This is an opportunity for students to develop full stack software engineering documentation and applications. They will execute documentation, unit testing, revision of software applications, and deploy software applications with scripts and containers on a cloud platform.

FOR SPECIFIC TASK INSTRUCTIONS AND REQUIREMENTS FOR THIS ASSESSMENT, PLEASE REFER TO THE COURSE PAGE.
BASIC INSTRUCTIONS
For this assessment, you will deploy your developed full stack software product to a web service of your choice.

## SUPPLEMENTAL RESOURCES

1. How to clone a project to IntelliJ using Git?

> Ensure that you have Git installed on your system and that IntelliJ is installed using [Toolbox](https://www.jetbrains.com/toolbox-app/). Make sure that you are using version 2022.3.2. Once this has been confirmed, click the clone button and use the 'IntelliJ IDEA (HTTPS)' button. This will open IntelliJ with a prompt to clone the proejct. Save it in a safe location for the directory and press clone. IntelliJ will prompt you for your credentials. Enter in your WGU Credentials and the project will be cloned onto your local machine.

2. How to create a branch and start Development?

- GitLab method

> Press the '+' button located near your branch name. In the dropdown list, press the 'New branch' button. This will allow you to create a name for your branch. Once the branch has been named, you can select 'Create Branch' to push the branch to your repository.

- IntelliJ method

> In IntelliJ, Go to the 'Git' button on the top toolbar. Select the new branch option and create a name for the branch. Make sure checkout branch is selected and press create. You can now add a commit message and push the new branch to the local repo.

## SUPPORT

If you need additional support, please navigate to the course page and reach out to your course instructor.

## FUTURE USE

Take this opportunity to create or add to a simple resume portfolio to highlight and showcase your work for future use in career search, experience, and education!
===================================================================================================================================================================

# WGU Task Management System

A full-stack web application built with Spring Boot and Java for the WGU Software Engineering Capstone (D424 - Assessment 3).

## Project Overview

This Task Management System demonstrates comprehensive software engineering skills including:

- **Object-Oriented Programming**: Inheritance, Polymorphism, and Encapsulation
- **Full Stack Development**: Spring Boot backend with Thymeleaf frontend
- **Database Management**: PostgreSQL with Spring Data JPA
- **Security**: Spring Security with BCrypt password encryption
- **Testing**: Unit and integration tests
- **Deployment**: Docker containerization

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: PostgreSQL 14+ (production), H2 (development/testing)
- **Security**: Spring Security with BCrypt
- **Template Engine**: Thymeleaf
- **CSS Framework**: Bootstrap 5
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, Spring Boot Test
- **Containerization**: Docker & Docker Compose

## OOP Principles Demonstration

### 1. Inheritance

- **BaseEntity** → Task, User, Project entities
- **BaseController** → TaskController, ProjectController, ReportController

### 2. Polymorphism

- **CrudService<T, ID>** interface → TaskService, UserService, ProjectService implementations

### 3. Encapsulation

- Private fields with public getters/setters
- Password field with no getter (User entity)
- Validation methods encapsulated in entities
- Business logic methods in service layer

## Features

✅ **Task Management**

- Create, read, update, delete tasks
- Search and filter functionality
- Multiple row results display
- Task status and priority tracking

✅ **Project Management**

- Organize tasks into projects
- Project statistics and tracking

✅ **Report Generation**

- Comprehensive task reports
- Multiple columns: task name, status, priority, assigned user, due date, project
- Timestamp and descriptive title
- Print-friendly format

✅ **User Management**

- Secure user registration and authentication
- Role-based access control (USER, ADMIN)
- BCrypt password encryption

✅ **Security Features**

- Spring Security integration
- Form-based authentication
- CSRF protection
- Session management

✅ **Responsive UI**

- Bootstrap 5 responsive design
- Mobile-friendly interface
- Intuitive navigation

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose (for containerized deployment)

### Option 1: Run with Docker (Recommended)

```bash
# Clone the repository
git clone <repository-url>
cd task-management-system

# Build and run with Docker Compose
docker-compose up --build

# Access the application
open http://localhost:8080
```

### Option 2: Run Locally (Development)

```bash
# Clone the repository
git clone <repository-url>
cd task-management-system

# Build the project
mvn clean install

# Run the application (uses H2 in-memory database)
mvn spring-boot:run

# Access the application
open http://localhost:8080

# Access H2 Console (development only)
open http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:taskdb
# Username: sa
# Password: (leave blank)
```

### Option 3: Run with PostgreSQL

```bash
# Start PostgreSQL
docker run -d \
  --name taskmanagement-postgres \
  -e POSTGRES_DB=taskmanagement \
  -e POSTGRES_USER=taskuser \
  -e POSTGRES_PASSWORD=taskpass \
  -p 5432:5432 \
  postgres:14-alpine

# Run application with prod profile
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Default Credentials

The application comes with pre-loaded sample data:

| Username   | Password    | Role  |
| ---------- | ----------- | ----- |
| admin      | password123 | ADMIN |
| john.doe   | password123 | USER  |
| jane.smith | password123 | USER  |

## Application Structure

```
task-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/wgu/taskmanagement/
│   │   │   ├── model/              # Entity classes (Inheritance & Encapsulation)
│   │   │   │   ├── BaseEntity.java
│   │   │   │   ├── Task.java
│   │   │   │   ├── User.java
│   │   │   │   └── Project.java
│   │   │   ├── repository/         # Spring Data JPA repositories
│   │   │   ├── service/            # Business logic (Polymorphism)
│   │   │   │   ├── CrudService.java
│   │   │   │   ├── TaskService.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── ProjectService.java
│   │   │   ├── controller/         # MVC controllers (Inheritance)
│   │   │   │   ├── BaseController.java
│   │   │   │   ├── TaskController.java
│   │   │   │   └── ReportController.java
│   │   │   ├── config/             # Configuration classes
│   │   │   ├── security/           # Security configuration
│   │   │   └── exception/          # Exception handling
│   │   └── resources/
│   │       ├── templates/          # Thymeleaf templates
│   │       ├── application.properties
│   │       └── data.sql            # Sample data
│   └── test/                       # Unit and integration tests
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## API Endpoints

### Tasks

- `GET /tasks` - List all tasks (with search/filter)
- `GET /tasks/new` - Show create task form
- `POST /tasks` - Create new task
- `GET /tasks/{id}` - View task details
- `GET /tasks/{id}/edit` - Show edit task form
- `POST /tasks/{id}` - Update task
- `POST /tasks/{id}/delete` - Delete task

### Projects

- `GET /projects` - List all projects
- `GET /projects/new` - Show create project form
- `POST /projects` - Create new project
- `GET /projects/{id}` - View project details

### Reports

- `GET /reports/tasks` - Generate task report

### Authentication

- `GET /login` - Login page
- `POST /login` - Process login
- `GET /register` - Registration page
- `POST /register` - Process registration
- `GET /logout` - Logout

## Testing

```bash
# Run all tests
mvn test

# Run tests with coverage report
mvn test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

## Building for Production

```bash
# Build JAR file
mvn clean package

# Run JAR file
java -jar target/task-management-system-1.0.0.jar --spring.profiles.active=prod
```

## Docker Commands

```bash
# Build Docker image
docker build -t task-management-system .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/taskmanagement \
  -e SPRING_DATASOURCE_USERNAME=taskuser \
  -e SPRING_DATASOURCE_PASSWORD=taskpass \
  task-management-system

# Using Docker Compose
docker-compose up -d          # Start in background
docker-compose logs -f app    # View logs
docker-compose down           # Stop and remove containers
```

>>>>>>> 
>>>>>>>
>>>>>>
>>>>>
>>>>
>>>
>>
