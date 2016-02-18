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
-- Table structure for table `PROJECT_TRANSACTIONS`
--

DROP TABLE IF EXISTS `PROJECT_TRANSACTIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROJECT_TRANSACTIONS` (
  `ID_PROJECT_TRANSACTIONS` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PROJECT` int(11) NOT NULL,
  `ID_TRANSACTION` int(11) NOT NULL,
  `PERCENT` decimal(10,4) NOT NULL,
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_PROJECT_TRANSACTIONS`),
  KEY `FK_PROJECT_TRANSACTIONS_1_idx` (`ID_PROJECT`),
  KEY `FK_PROJECT_TRANSACTIONS_2_idx` (`ID_TRANSACTION`),
  CONSTRAINT `FK_PROJECT_TRANSACTIONS_1` FOREIGN KEY (`ID_PROJECT`) REFERENCES `PROJECTS` (`ID_PROJECT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROJECT_TRANSACTIONS_2` FOREIGN KEY (`ID_TRANSACTION`) REFERENCES `TRANSACTIONS` (`ID_TRANSACTION`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:43:35
