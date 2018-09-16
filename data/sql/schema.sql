CREATE DATABASE IF NOT EXISTS `access`;
USE `access`;

CREATE TABLE IF NOT EXISTS `access`.`restricted` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ipAddress` VARCHAR(45) NOT NULL,
  `restricted` BOOLEAN NOT NULL DEFAULT false,
  `cause` VARCHAR(255) NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `access`.`access_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accessTime` DATETIME NOT NULL,
  `ipAddress` VARCHAR(45) NOT NULL,
  `httpMethod` VARCHAR(50) NOT NULL,
  `httpStatus` INT NOT NULL,
  `httpClient` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `ip_address` (`ipAddress`)
)
ENGINE = InnoDB;
