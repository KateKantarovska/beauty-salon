DROP DATABASE IF EXISTS beauty_salon_test;

CREATE DATABASE beauty_salon_test DEFAULT CHARACTER SET utf8;
USE beauty_salon_test;

CREATE TABLE clients
(
    id           INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email        VARCHAR(50)                    NOT NULL UNIQUE,
    password     VARCHAR(50)                    NOT NULL,
    name         VARCHAR(50)                    NOT NULL,
    surname      VARCHAR(50)                    NOT NULL,
    phone_number VARCHAR(15),
    balance      INT     DEFAULT 0,
    active       BOOLEAN DEFAULT true
);

CREATE TABLE masters
(
    id           INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email        VARCHAR(50)                    NOT NULL UNIQUE,
    password     VARCHAR(50)                    NOT NULL,
    name_ua      VARCHAR(50)                    NOT NULL,
    surname_ua   VARCHAR(50)                    NOT NULL,
    name_en      VARCHAR(50)                    NOT NULL,
    surname_en   VARCHAR(50)                    NOT NULL,
    phone_number VARCHAR(15),
    rating       FLOAT,
    active       BOOLEAN DEFAULT true
);

CREATE TABLE admins
(
    id       INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email    VARCHAR(50)                    NOT NULL UNIQUE,
    password VARCHAR(50)                    NOT NULL,
    name     VARCHAR(50)                    NOT NULL,
    surname  VARCHAR(50)                    NOT NULL
);

CREATE TABLE services_categories
(
    id      INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name_ua VARCHAR(20) UNIQUE,
    name_en VARCHAR(20) UNIQUE
);

CREATE TABLE services
(
    id          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    category_id INT                            NOT NULL,
    name_ua     VARCHAR(50)                    NOT NULL,
    name_en     VARCHAR(50)                    NOT NULL,
    price_uah   INT                            NOT NULL,
    price_usd   INT                            NOT NULL,
    FOREIGN KEY (category_id) REFERENCES services_categories (id) ON DELETE CASCADE
);

CREATE TABLE masters_services
(
    master_id           INT NOT NULL,
    service_category_id INT NOT NULL,
    FOREIGN KEY (master_id) REFERENCES masters (id) ON DELETE CASCADE,
    FOREIGN KEY (service_category_id) REFERENCES services_categories (id) ON DELETE CASCADE
);

CREATE TABLE appointments
(
    id         INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    service_id INT                                             DEFAULT NULL,
    master_id  INT                            NOT NULL,
    date       DATE                           NOT NULL,
    timeslot   TIME                           NOT NULL,
    status     ENUM ('free', 'booked', 'pending', 'completed') DEFAULT 'free',
    client_id  INT                                             DEFAULT NULL,
    FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services (id) ON DELETE CASCADE,
    FOREIGN KEY (master_id) REFERENCES masters (id) ON DELETE CASCADE
);

CREATE TABLE reviews
(
    id        INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    client_id INT                            NOT NULL,
    master_id INT                            NOT NULL,
    text      VARCHAR(255)                   NOT NULL,
    rating    INT                            NOT NULL,
    visible   BOOLEAN DEFAULT true,
    FOREIGN KEY (client_id) REFERENCES clients (id),
    FOREIGN KEY (master_id) REFERENCES masters (id)
);