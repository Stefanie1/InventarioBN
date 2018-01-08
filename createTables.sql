
-- Generated: 2017-11-18 18:53
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Jovani Martinez Rico

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `HotelAdvisor` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `HotelAdvisor`.`Hotels` (
  `hotelId` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(70) NOT NULL,
  `city` VARCHAR(21) NOT NULL,
  `state` CHAR(2) NOT NULL,
  `streetAddress` VARCHAR(100) NOT NULL,
  `lat` DOUBLE NOT NULL,
  `lon` DOUBLE NOT NULL,
  `Hotelscol` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`hotelId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `HotelAdvisor`.`Reviews` (
  `reviewId` INT(11) NOT NULL AUTO_INCREMENT,
  `hotelId` INT(11) NOT NULL,
  `rating` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `review` VARCHAR(45) NOT NULL,
  `isRecom` TINYINT(4) NOT NULL,
  `date` DATETIME NOT NULL,
  `Reviewscol` VARCHAR(45) NOT NULL,
  `userId` INT(11) NOT NULL,
  PRIMARY KEY (`reviewId`),
  INDEX `fk_Reviews_Hotels_idx` (`hotelId` ASC),
  INDEX `fk_Reviews_users1_idx` (`userId` ASC),
  CONSTRAINT `fk_Reviews_Hotels`
    FOREIGN KEY (`hotelId`)
    REFERENCES `HotelAdvisor`.`Hotels` (`hotelId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reviews_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `HotelAdvisor`.`users` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `HotelAdvisor`.`users` (
  `userid` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` CHAR(64) NOT NULL,
  `usersalt` CHAR(32) NOT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
