CREATE DATABASE  IF NOT EXISTS `BID_NEW` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `BID_NEW`;
-- MySQL dump 10.13  Distrib 5.6.27, for debian-linux-gnu (x86_64)
--
-- Host: 192.168.1.149    Database: BID_NEW
-- ------------------------------------------------------
-- Server version	5.5.41-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BUDGET_MONTH_CONCEPTS`
--

DROP TABLE IF EXISTS `BUDGET_MONTH_CONCEPTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BUDGET_MONTH_CONCEPTS` (
  `ID_BUDGET_MONTH_CONCEPT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_BUDGET_MONTH_BRANCH` int(11) NOT NULL,
  `ID_BUDGET_CONCEPT` int(11) NOT NULL,
  `AMOUNT` decimal(12,2) DEFAULT NULL,
  `ID_CURRENCY` int(11) NOT NULL DEFAULT '1',
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_BUDGET_MONTH_CONCEPT`),
  KEY `FK_BUDGET_MONTH_CONCEPTS_1_idx` (`ID_BUDGET_MONTH_BRANCH`),
  KEY `FK_BUDGET_MONTH_CONCEPTS_2_idx` (`ID_BUDGET_CONCEPT`),
  KEY `FK_BUDGET_MONTH_CONCEPTS_3_idx` (`ID_CURRENCY`),
  CONSTRAINT `FK_BUDGET_MONTH_CONCEPTS_3` FOREIGN KEY (`ID_CURRENCY`) REFERENCES `C_CURRENCIES` (`ID_CURRENCY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGET_MONTH_CONCEPTS_1` FOREIGN KEY (`ID_BUDGET_MONTH_BRANCH`) REFERENCES `BUDGET_MONTH_BRANCH` (`ID_BUDGET_MONTH_BRANCH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGET_MONTH_CONCEPTS_2` FOREIGN KEY (`ID_BUDGET_CONCEPT`) REFERENCES `C_BUDGET_CONCEPTS` (`ID_BUDGET_CONCEPT`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=741 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-29 12:37:49
