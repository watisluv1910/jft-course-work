![Project version](https://img.shields.io/badge/Project_version-1.0.0-gree)
![Contributors](https://img.shields.io/badge/Contributors-1-blue)

# The Sun Reader Project

"The Sun Reader" project is a full-stack web application developed for study purposes.
It serves as a news aggregator with an additional bookmarking functionality
to provide users with a seamless news browsing experience.

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
2. Make sure you have [Docker](https://www.docker.com/) installed.
3. Update the environment variables in the `.env.example` file with your desired configuration by copying it into `.env` file from **the same** directory.
4. Build and run the Docker containers using the `docker-compose up` command in the [root directory](.).
5. Access the application in your browser at http://localhost:3001.

## Local Development

We would love for you to contribute to Angular and help make it even better than it is today! 
As a contributor, here are the guidelines we would like you to follow:

- [Getting Started](#getting-started)
- [Coding Rules](#coding-rules)
- [Commit Message Guidelines](#commit-message-guidelines)
- [General Development Workflow](#general-development-workflow)

### Getting started

To start the project locally:

1. Clone the repository to your local machine.
2. Update the environment variables in the `.env.example` file with your desired configuration by copying it into `.env` file from **the same** directory.
3. Install the tools listed below if you **don't already have them**:
   - [Node JS](https://nodejs.org/en/download) > v18.0.2.
   - [Java Development Kit](https://www.oracle.com/java/technologies/downloads/) > v19.0.1.
   - [MySQL Server](https://dev.mysql.com/downloads/mysql/) > v8.0.31.
4. **Check** MySQL server is running and use [MySQL Command Line Client](https://dev.mysql.com/doc/refman/8.0/en/mysql.html) 
or [MySQL Workbench](https://www.mysql.com/products/workbench/) to execute database 
initial script from [/mysql-database](./mysql-database) directory.
5. Execute **the main class** of a Spring Boot Server application through your IDE
or open a terminal in a root directory and run the following commands (insert the [current project version](#the-sun-reader-project)):
   
   ```shell
   mvn clean package
   java -jar springboot-backend/target/spring-backend-<version>-SNAPSHOT.jar fully.qualified.package.Application 
   ```
   
6. Go to [/react-ui](./react-ui) directory and execute the following code:

   ```shell
   npm run start
   ```
   
7. Access the application in your browser at http://localhost:3000.

### Coding Rules

To ensure consistency throughout the source code, keep these rules in mind as you are working:

- All features or bug fixes must be tested by one or more specs (unit-tests).
- All public API methods must be documented with [Doc comments](https://www.jetbrains.com/help/webstorm/creating-jsdoc-comments.html).
- We follow [Google's JavaScript Style Guide](https://google.github.io/styleguide/jsguide.html), but wrap all code at 100 characters.

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

