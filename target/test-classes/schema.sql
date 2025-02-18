DROP TABLE IF EXISTS customer_request;

CREATE TABLE customer_request (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_account_number VARCHAR(20) NOT NULL,
    reference_number VARCHAR(20) NOT NULL UNIQUE,
    type VARCHAR(3) NOT NULL,
    channel VARCHAR(3) NOT NULL,
    content VARCHAR(2048) NOT NULL,
    priority VARCHAR(3) NOT NULL,
    status VARCHAR(3) NOT NULL,
    assigned_to VARCHAR(50) NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completion_date TIMESTAMP NULL,
    notes VARCHAR(MAX) NULL
);