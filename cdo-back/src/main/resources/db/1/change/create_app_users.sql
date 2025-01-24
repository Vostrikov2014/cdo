CREATE TABLE app_users (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                          username VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          firstname VARCHAR(255),
                          lastname VARCHAR(255),
                          email VARCHAR(255),
                          passwordhash VARCHAR(255)
);