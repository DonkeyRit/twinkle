# Twinkle

![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)![Flyway](https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white)![Docker](https://img.shields.io/badge/Docker-2496ED.svg?style=for-the-badge&logo=Docker&logoColor=white)![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36.svg?style=for-the-badge&logo=Apache-Maven&logoColor=white)![Hibernate](https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=fff&style=for-the-badge)![JUnit5](https://img.shields.io/badge/JUnit5-25A162?logo=junit5&logoColor=fff&style=for-the-badge)

## Project Setup and Launch Guide

This README provides instructions on how to set up and launch the local development environment for the project. Follow these steps to get started 🚀.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- Docker and Docker Compose
- Maven
- JDK 17 or newer

## 🐳 Launching Docker Compose for Local Development

1. **Start the Docker Containers**

   Navigate to the `build/local` directory of your project where the `docker-compose.yml` file is located. Run the following command to start your local development databases:

   ```bash
   cd build/local
   docker-compose up -d
   ```

   This command will start all the services defined in your `docker-compose.yml` file in detached mode.

2. **Verify the Containers are Running**

   You can check if the containers are up and running using:

   ```bash
   docker-compose ps
   ```

   Ensure that the databases are listed as "Up".

## 🛠 Running Maven with Database Configuration

Before initiating the integration tests, it's crucial to ensure that the databases required for testing are active and accessible. To achieve this, start by launching Docker Compose, which will spin up the necessary database instances. Following this setup, compile your application using Maven, and don't forget to include the database configurations—URL, user, and password—via Maven flags. This preparatory step is essential for successfully launching your integration tests.

1. **Set Environment Variables (Optional)**

   Navigate back to the root directory of your project (if you're not already there from running Docker Compose) and compile your project sources with Maven:

   Instead of passing sensitive information directly in the command line, you can set them as environment variables:

   ```bash
   export DATABASE_URL=jdbc:postgresql://localhost:5432/carrent
   export DATABASE_USER=localuser
   export DATABASE_PASSWORD=localpassword
   ```

2. **Compile the Application**

   With the environment variables set, you can now run building your application with Maven:

   ```bash
   mvn clean generate-sources -Ddatabase.url=${env.DATABASE_URL} -Ddatabase.user=${env.DATABASE_USER} -Ddatabase.password=${env.DATABASE_PASSWORD} -X
   ```

   If you prefer not to use environment variables, you can directly insert the values into the command. However, be mindful of security implications.

   ```bash
   mvn clean generate-sources -Ddatabase.url=jdbc:postgresql://localhost:5432/carrent -Ddatabase.user=localuser -Ddatabase.password=localpassword -X
   ```

## 🎉 Congratulations

You have successfully set up and launched your local development environment. Happy coding!
