SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema rutaTuristica
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rutaTuristica` DEFAULT CHARACTER SET utf8mb3 ;
USE `rutaTuristica` ;

-- Tabla Country
CREATE TABLE IF NOT EXISTS `country` (
    id_country INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

-- Tabla City
CREATE TABLE IF NOT EXISTS `city` (
    id_city INT AUTO_INCREMENT PRIMARY KEY,
    id_country INT,
    name VARCHAR(100) UNIQUE NOT NULL,
    FOREIGN KEY (id_country) REFERENCES `country`(id_country)
);

-- Inserts para la tabla Country
INSERT INTO `country` (name) VALUES 
('Spain'),
('United States'),
('United Kingdom');

-- Inserts para la tabla City
INSERT INTO `city` (id_country, name) VALUES 
-- Ciudades de España
(1, 'Seville'),
(1, 'Madrid'),
(1, 'Barcelona'),
-- Ciudades de Estados Unidos
(2, 'New York'),
(2, 'Los Angeles'),
-- Ciudades de Reino Unido
(3, 'London'),
(3, 'Birmingham');


-- Tabla User
CREATE TABLE IF NOT EXISTS `user` (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    rol VARCHAR(50)
);

INSERT INTO `user` (id_user, name, email, username, password, rol) 
VALUES 
(1, 'Miguel Ángel', 'miguelangel@email.com', 'miguelangel', 'Abcd123', 'user');


-- Tabla Route
CREATE TABLE IF NOT EXISTS `route` (
    id_route INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT,
    id_city INT,
    name VARCHAR(100) UNIQUE,
    description TEXT,
    created_date DATE,
    distance FLOAT,
    duration TIME,
    FOREIGN KEY (id_user) REFERENCES `user`(id_user),
    FOREIGN KEY (id_city) REFERENCES `city`(id_city)
);

-- Tabla Checkpoint
CREATE TABLE IF NOT EXISTS `checkpoint` (
    id_checkpoint INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    start_latitude DECIMAL(9,6),
    start_longitude DECIMAL(9,6),
    end_latitude DECIMAL(9,6),
    end_longitude DECIMAL(9,6)
);

-- Tabla intermedia Route_Checkpoint (relación N:M entre Route y Checkpoint)
CREATE TABLE IF NOT EXISTS `route_checkpoint` (
    id_route INT,
    id_checkpoint INT,
    PRIMARY KEY (id_route, id_checkpoint),
    FOREIGN KEY (id_route) REFERENCES `route`(id_route) ON DELETE CASCADE,
    FOREIGN KEY (id_checkpoint) REFERENCES `checkpoint`(id_checkpoint) ON DELETE CASCADE
);

-- Tabla Comment
CREATE TABLE IF NOT EXISTS `comment` (
    id_comment INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT,
    id_route INT,
    description TEXT,
    created_date DATE,
    FOREIGN KEY (id_user) REFERENCES `user`(id_user) ON DELETE CASCADE,
    FOREIGN KEY (id_route) REFERENCES `route`(id_route) ON DELETE CASCADE
);

-- Restauración de configuraciones
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET SQL_MODE = @OLD_SQL_MODE;
