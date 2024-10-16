CREATE DATABASE IF NOT EXISTS schedule_jwt_db;
USE schedule_jwt_db;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       created_at DATETIME NOT NULL,
                       updated_at DATETIME NOT NULL
);

CREATE TABLE schedules (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(100) NOT NULL,
                           content TEXT NOT NULL,
                           creator_id BIGINT NOT NULL,
                           created_at DATETIME NOT NULL,
                           updated_at DATETIME NOT NULL,
                           FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE comments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          content TEXT NOT NULL,
                          user_id BIGINT NOT NULL,
                          schedule_id BIGINT NOT NULL,
                          created_at DATETIME NOT NULL,
                          updated_at DATETIME NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);

CREATE TABLE schedule_user_assignments (
                                           schedule_id BIGINT NOT NULL,
                                           user_id BIGINT NOT NULL,
                                           PRIMARY KEY (schedule_id, user_id),
                                           FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE,
                                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
