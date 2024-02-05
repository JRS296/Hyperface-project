# Hyperface-project

Java/Apache Groovy Spring Boot Project - Hyperface Internship, January 2024.

## Progress:
- Week 1 - CRUD Operations for EMS System - 95% (PR [#1](https://github.com/JRS296/Hyperface-project/pull/1))
- Week 2 - Testing - 55% Code Coverage (75/101 TC PASSED) (PR [#3](https://github.com/JRS296/Hyperface-project/pull/3))
- Week 3 - Authentication and Authorization - 100% (PR [#1](https://github.com/JRS296/Hyperface-project/pull/1))
- Week 4 - Transition to Groovy - 100% (PR [#2](https://github.com/JRS296/Hyperface-project/pull/2))
- Miscellaneous - (PR [#4](https://github.com/JRS296/Hyperface-project/pull/4))

## Project Setup

### Project Requirements
- Server - Spring Boot (Use Version: v3.2.1)
- JDK - Java 17
- Groovy - v4.0.17

Build Dependencies
```
// Implementation Dependencies
implementation 'org.apache.groovy:groovy:4.0.17'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.jeasy:easy-random:5.0.0'
implementation 'org.springframework.boot:spring-boot-starter-security:3.2.2'
implementation 'org.springframework.boot:spring-boot-starter-actuator:3.2.0'
implementation 'io.jsonwebtoken:jjwt:0.9.1'
implementation 'javax.xml.bind:jaxb-api:2.2.4'
implementation 'org.springframework.boot:spring-boot-starter-cache:3.2.0'
implementation 'io.jsonwebtoken:jjwt-api:0.11.2'

// Runtime Dependencies
runtimeOnly 'com.mysql:mysql-connector-j'
runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.2'
runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.2'

// Testing Dependencies
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.springframework.boot:spring-boot-starter-webflux'
testImplementation  'com.h2database:h2:1.3.148'

//Optional Dependencies
developmentOnly 'org.springframework.boot:spring-boot-devtools'
```

Use following Gradle File for Gradle Build
```gradle
plugins {
	id 'groovy'
	id 'org.springframework.boot' version '3.2.1'
	id 'io.spring.dependency-management' version '1.1.4'
}

group = 'com.jrs296'
version = '0.0.1-SNAPSHOT'

sourceSets {
	main {
		java { srcDirs = [] }    // no source dirs for the java compiler
		groovy { srcDirs = ["src/main/java", "src/main/groovy"] }  // compile   everything in src/ with groovy
	}
	test{
		groovy { srcDirs = ["src/test/groovy"] }
	}
}
configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}
repositories {
	mavenCentral()
}
dependencies {
	implementation 'org.apache.groovy:groovy:4.0.17'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.jeasy:easy-random:5.0.0'
	implementation 'org.springframework.boot:spring-boot-starter-security:3.2.2'
	implementation 'org.springframework.boot:spring-boot-starter-actuator:3.2.0'
	implementation 'io.jsonwebtoken:jjwt:0.9.1'
	implementation 'javax.xml.bind:jaxb-api:2.2.4'
	implementation 'org.springframework.boot:spring-boot-starter-cache:3.2.0'
	implementation 'io.jsonwebtoken:jjwt-api:0.11.2'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	runtimeOnly 'com.mysql:mysql-connector-j'
	runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.2'
	runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.2'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.boot:spring-boot-starter-webflux'
	testImplementation  'com.h2database:h2:1.3.148'
}
tasks.named('test') {
	useJUnitPlatform()
}
```

Build Application
```bash
cd ems
./gradlew build
```

Run Server
```bash
./gradlew bootRun
```

## Briefing on Controller Flow:

http://localhost:8080   -   root

- [x] /test - TestController

### /api/auth - AuthController
- [x] (GET) /test                     - test Controller, verifies pre-authorize for all roles.
- [x] (GET) /user/userProfile         - test Controller, verifies pre-authorize for all roles.
- [x] (GET) /admin/adminProfile       - test Controller, verifies pre-authorize for all roles.
- [x] (GET) /manager/managerProfile   - test Controller, verifies pre-authorize for all roles.
- [x] (POST) /register                - all employees are to register, role defaults to USER, Admin is pre-created, ProjectID and DepartmentID == null
- [x] (POST) /login                   - username and password

### /api/assign - AdminController
- [x] (GET) /authority   - Test Endpoint, returns authorities of currently logged-in user
- [ ] (PUT) /manager     - Assigns Role change to either department or project - ONLY if Department (or) Project exists
- [ ] (PUT) /employee    -  Assigns Project and Department to an employee

**TODO - TestCases for manager and employee, specific to migration between departments (***)**

### /api/service/department - DepartmentController ✅
- [x] (GET) /         - ALL - Get all departments
- [x] (GET) /{id}     - ALL - Get department by id
- [x] (POST) /        - ADMIN - Create a department
- [x] (PUT) /         - ADMIN - Update department (only name of department)
- [x] (DELETE) /{id}  - ADMIN - Delete department by id

### /api/service/project - ProjectController
- [x] (GET) /         - ALL - Get all projects
- [x] (GET) /{id}     - ALL - Get project by id
- [ ] (POST) /        - ADMIN, DEPT_MANAGER - Create a project*
- [ ] (PUT) /         - ADMIN, DEPT_MANAGER - Update project (only name of project and *assigned department)
- [ ] (DELETE) /{id}  - ADMIN, DEPT_MANAGER - Delete project by id*

**TODO - Scoped Handling (DEPT_MANAGER) \
TODO - TestCases for PUT and DELETE**

### /api/service/employee - EmployeeController
- [x] (GET) /         - ALL - Get all employees
- [x] (GET) /{id}     - ALL - Get employee by id
- [ ] (PUT) /         - USER - Update employee (name, username, password and email)**
- [ ] (DELETE) /{id}  - ADMIN, DEPT_MANAGER, PROJECT_MANAGER - Delete employee by id*

**TODO - User Specific data change (PUT) \
TODO - Scoped Handling (DEPT_MANAGER and PROJECT_MANAGER)**

## Testing Coverage
### Controller Tests
- [x] - AdminControllerTests
- [x] - AuthControllerTests
- [x] - DepartmentControllerTests
- [ ] - EmployeeControllerTests
- [ ] - ProjectControllerTests

### Service Tests
- [ ] - AdminServiceTests
- [x] - JWTServiceTests
- [x] - DepartmentServiceTests
- [x] - EmployeeServiceTests
- [x] - ProjectServiceTests

### End-To-End Tests
- [x] - AuthSystemTests
- [ ] - TODO - AdminSystemTests
- [ ] - DepartmentSystemTests
- [ ] - EmployeeSystemTests
- [ ] - ProjectSystemTests

### Miscellaneous Tests
- [x] - Exception Tests
- [x] - Miscellaneous Tests

TODO - Scoped Handling (DEPT_MANAGER and PROJECT_MANAGER)**
