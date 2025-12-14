# Financial Track App

A full-stack financial tracking application that allows users to securely manage income, expenses, budgets, and recurring transactions.

## Features
- JWT-based authentication and authorization
- Income and expense tracking with categories
- Monthly budgeting by category
- Recurring transactions via scheduled jobs
- Analytics endpoints for monthly summaries
- RESTful API built with Spring Boot

## Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Docker
- Maven

## Running Locally
```bash
docker compose up -d
mvn spring-boot:run
