CREATE DATABASE IF NOT EXISTS `access`;
USE `access`;

CREATE TABLE IF NOT EXISTS `access`.`access_ip` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ip_address` VARCHAR(45) NOT NULL,
  `restricted` BOOLEAN NOT NULL DEFAULT false,
  `cause` VARCHAR(255) NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `access`.`access_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accessTime` DATETIME NOT NULL,
  `addressId` INT NOT NULL,
  `method` VARCHAR(10) NOT NULL,
  `status` INT NOT NULL,
  `client` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`addressId`) REFERENCES `access`.`access_ip`(`id`)
)
ENGINE = InnoDB;
