# spring-auditing-hibernate-envers
This project demonstrates how to enable **auditing and entity versioning** in a Spring Boot application using **Hibernate Envers**.

## Features
- Automatic auditing of JPA entities (`@Audited` annotation).
- Tracks insert, update, and delete operations with historical revisions.
- Custom revision entity for storing metadata (e.g., user info).
- Example REST endpoints to query current and historical data.
- Configurable audit table suffix and delete tracking.

## Tech Stack
- Java 17
- Spring Boot 4.0
- Spring Data JPA
- Hibernate Envers
- MySql

## Use Cases
- Regulatory compliance (GDPR, SOX)
- Debugging and troubleshooting
- Data integrity and rollback support
