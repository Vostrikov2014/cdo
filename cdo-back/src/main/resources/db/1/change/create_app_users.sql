CREATE TABLE app_users (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                          user_name VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          first_name VARCHAR(255),
                          last_name VARCHAR(255),
                          email VARCHAR(255)
);