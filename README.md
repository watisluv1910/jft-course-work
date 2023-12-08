![Project version](https://img.shields.io/badge/Project_version-1.0.0-gree)
![Contributors](https://img.shields.io/badge/Contributors-1-blue)
[![Static Badge](https://img.shields.io/badge/Documentation-5A2FF2)](backend/target/documentation/dokka/index.html)

# The Sun Reader Project

"The Sun Reader" project is a full-stack web application developed for study purposes.
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
- **Back-end:** Spring Boot (Java)  
- **Database:** MySQL  
- **Docker:** Containerization and deployment  
- **HTTP Client:** Axios  
- **Version Control:** Git

## Quick Start

To quickstart the **"The Sun Reader"** project, follow these steps:

1. Clone the repository to your local machine.

   ```shell
   git clone https://github.com/watisluv1910/mirea_5s_server-side-development_course-work.git
   cd jft-course-work
   ```
   
2. Make sure you have [Docker](https://www.docker.com/) installed and enabled.
3. Build and run the Docker containers using the `docker-compose up` command in project's [root directory](.).
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
   cd jft-course-work
   ```
   
2. Update the environment variables in the `.env.example` file with your desired configuration by copying it into `.env` file from **the same** directory.
   
   ```shell
   cp .env.example .env
   ```
   
3. Modify _MYSQL_USER_ and _MYSQL_PASSWORD_ to match your [MySQL Server](https://dev.mysql.com/downloads/mysql/) credentials.

   ```shell
   nano .env
   ```
   
4. Install the tools listed below if you **don't already have them**:
   - [Node JS](https://nodejs.org/en/download) > v18.0.2.
   - [Java Development Kit](https://www.oracle.com/java/technologies/downloads/) > v21.0.1.
   - [MySQL Server](https://dev.mysql.com/downloads/mysql/) > v8.2.0.

5. Check [MySQL Server](https://dev.mysql.com/downloads/mysql/) is running at _MYSQL_TCP_PORT_. 
Change _MYSQL_TCP_PORT_ environmental variable if needed.

6. Run the following command from project's [root directory](.):

   ```shell
   ./mvnw clean package
   ```
   
7. Run backend server by executing the following commands from project's [root directory](.):
   
   ```shell
   cd springboot-backend
   java -jar /target/backend-<version>.jar
   ```

8. Run frontend server by executing the following commands from project's [root directory](.):

   ```shell
   cd react-ui
   npm run start
   ```
   
9. Access the application in your browser at http://localhost:3000 
(or another port if you changed _FRONTEND_DEFAULT_PORT_ environment variable).

### Coding Rules

To ensure consistency throughout the source code, keep these rules in mind as you are working:

- All features or bug fixes must be tested by one or more specs (unit-tests).
- All public API methods must be documented with [Doc comments](https://www.jetbrains.com/help/webstorm/creating-jsdoc-comments.html).
- We follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) and [Commitlint](https://commitlint.js.org/#/) configs.

### Commit Message Guidelines

We have very precise rules over how git commit messages can be formatted. 
This leads to more readable messages that are easy to follow when looking through the project history.

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

