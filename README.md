![Project version](https://img.shields.io/badge/Project_version-1.0.0-gree)
![Contributors](https://img.shields.io/badge/Contributors-1-blue)
[![Static Badge](https://img.shields.io/badge/Documentation-5A2FF2)](backend/target/documentation/dokka/index.html)

# The Sun Reader Project

"The Sun Reader" project is a full-stack web application developed for study
purposes.
It serves as a news aggregator with an additional bookmarking functionality.

## Features

- Browsing and reading news articles from various sources.
- Registration and login functionality.
- Bookmarking articles for future reference.
- User profile page for bookmarks managing.
- Additional capabilities for moderators and administrators.
- Integration with external APIs.

## Technologies Stack

- **Front-end:** NodeJS, ReactJS, HTML5, CSS3
- **Back-end:** Spring Boot (Kotlin), Mockk, JUnit5, Dokka
- **Database:** MySQL
- **Docker:** Containerization and deployment
- **HTTP Client:** Axios
- **Version Control:** Git

## Quick Start

To quickstart **The Sun Reader** project, follow these steps:

1. Clone the repository to your local machine.

   ```shell
   git clone https://github.com/watisluv1910/mirea_5s_server-side-development_course-work.git
   ```

2. Make sure you have [Docker](https://www.docker.com/) installed and enabled.
3. Build and run the Docker containers using the `docker-compose up` command in
   project's [root directory](.).
4. Access the application in your browser at http://localhost:3000.

## Local Development

- [Getting Started](#getting-started)
- [Coding Rules](#coding-rules)
- [Commit Message Guidelines](#commit-message-guidelines)
- [General Development Workflow](#general-development-workflow)

### Getting started

To start the project locally:

1. Clone the repository to your local machine.

   ```shell
   git clone https://github.com/watisluv1910/mirea_5s_server-side-development_course-work.git
   ```

2. Run the following command from project's [root directory](.):

   For Unix-like systems:
   ```shell
   ./mvnw clean package
   ```
   For Windows:
   ```shell
   mvnw.cmd clean package
   ```

3. Install the tools listed below if you **don't already have them**:
    - [Java Development Kit](https://www.oracle.com/java/technologies/downloads/) >= v17.0.9.
    - [MySQL Server](https://dev.mysql.com/downloads/mysql/) >= v8.2.0.
    - [Node JS]() >= 20.13.1.
<br><br>
4. In `.env` file located in the [root directory](.) modify **_MYSQL_USER_** and
   **_MYSQL_PASSWORD_** to match
   your [MySQL Server](https://dev.mysql.com/downloads/mysql/)
   credentials. Modify other environmental variables according to your
   preferences and needs.
   Note that:
   - **_ENV_FILE_** variable **should NOT be modified**.
   - Provided **_MYSQL_USER_** should at least have the following privileges:
     - ALTER, CREATE, CREATE TABLESPACE, DROP, INDEX, REFERENCES;
     - SELECT, UPDATE, DELETE.
<br><br>
5. Check [MySQL Server](https://dev.mysql.com/downloads/mysql/) is running at **_MYSQL_TCP_PORT_**.
   Change **_MYSQL_TCP_PORT_** environmental variable if needed.
<br><br>
6. Run backend server by executing the following commands from
   project's [root directory](.):

   ```shell
   java -jar backend/target/backend-<version>.jar
   ```

7. Run frontend server by executing the following commands from
   project's [root directory](.):

   ```shell
   cd react-ui && npm run start
   ```

8. Access the application in your browser at http://localhost:3000
   (or another port if you changed **_FRONTEND_DEFAULT_PORT_** environment
   variable).

### Coding Rules

To ensure consistency throughout the source code, keep these rules in mind as
you are working:

- All features or bug fixes must be tested by one or more specs (unit-tests).
- All public API methods must be documented with [Doc comments](https://www.jetbrains.com/help/webstorm/creating-jsdoc-comments.html).
- We follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) and [Commitlint](https://commitlint.js.org/#/) configs.

### Commit Message Guidelines

We have very precise rules over how git commit messages can be formatted.
This leads to more readable messages that are easy to follow when looking
through the project history.

#### Commit message format

Each commit message consists of a **header**, a **body** and a **footer**.
The header has a special format that includes a **type**, a **scope** and a **subject**:

```
<type>(<scope1>, <scope2>): <subject>  
<BLANK LINE> 
<body>  
<BLANK LINE>
<footer>
```

The **header** is mandatory and the **scopes** of the header are optional.

Samples:

```
refactor(env): update example .env file to match services requirements
```

```
fix(proxy)!: implemented test proxy configuration

BREAKING CHANGE!
```

### General Development Workflow

1. Make changes to the code in the respective folders.
2. Run the application locally using the provided scripts or commands.
3. Test your changes and verify that the application is functioning as expected.
4. Commit your changes to a new branch and push them to the remote repository.
5. Create a pull request for review and merge it into the main branch.

## Contact

For any inquiries or questions, please contact at **nasevich.vlasd.03@gmail.com** with tag `SunReader`.
