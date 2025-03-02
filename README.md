# TTB-CRMX
An application to manage customer request inquiries using Java21, spring-boot, and MS-SQL server. This application provide RESTful endpoint to enable CRUD operations.

## Prerequisite
- Java 21 or higher
- Maven
- MS-SQL server

## Quick start
- Clone this repository
- Create a table in database with this command
```
CREATE TABLE customer_request (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    customer_name NVARCHAR(255) NOT NULL,
    customer_account_number NVARCHAR(50) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    reference_number NVARCHAR(50) NOT NULL,
    assigned_to NVARCHAR(255) NULL,
    priority NVARCHAR(50) NOT NULL,
    type NVARCHAR(50) NOT NULL,
    channel NVARCHAR(50) NOT NULL,
    status NVARCHAR(50) NOT NULL,
    notes NVARCHAR(MAX) NULL,
    created_date DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    last_modified_date DATETIME2 NULL DEFAULT SYSDATETIME(),
    completion_date DATETIME2 NULL
);

```
- Run `mvn clean install`
- Run `mvn spring-boot:run`

## Overview
You can access this application via port `8080` and context path `/api/v1/customer-requests`. ie. `http://localhost:8080/api/v1/customer-requests`

## Documentation
Start the server. You will find the swagger document at `http://localhost:8080/swagger-ui/`
