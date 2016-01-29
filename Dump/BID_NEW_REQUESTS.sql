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
-- Table structure for table `REQUESTS`
--

DROP TABLE IF EXISTS `REQUESTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REQUESTS` (
  `ID_REQUEST` int(11) NOT NULL AUTO_INCREMENT,
  `ID_REQUEST_TYPE_PRODUCT` int(11) NOT NULL,
  `ID_BUDGET_MONTH_BRANCH` int(11) NOT NULL,
  `FOLIO` varchar(40) NOT NULL,
  `USER_REQUEST` int(11) NOT NULL,
  `USER_RESPONSABLE` int(11) NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `PURPOSE` text,
  `ID_REQUEST_STATUS` int(11) NOT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_REQUEST`),
  KEY `FK_REQUESTS1` (`ID_REQUEST_TYPE_PRODUCT`),
  KEY `FK_REQUESTS4` (`ID_REQUEST_STATUS`),
  KEY `FK_REQUESTS2_idx` (`ID_BUDGET_MONTH_BRANCH`),
  KEY `FK_REQUESTS5_idx` (`USER_REQUEST`),
  KEY `FK_REQUESTS6_idx` (`USER_RESPONSABLE`),
  CONSTRAINT `FK_REQUESTS1` FOREIGN KEY (`ID_REQUEST_TYPE_PRODUCT`) REFERENCES `REQUEST_TYPES_PRODUCT` (`ID_REQUEST_TYPE_PRODUCT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_REQUESTS2` FOREIGN KEY (`ID_BUDGET_MONTH_BRANCH`) REFERENCES `BUDGET_MONTH_BRANCH` (`ID_BUDGET_MONTH_BRANCH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_REQUESTS4` FOREIGN KEY (`ID_REQUEST_STATUS`) REFERENCES `C_REQUEST_STATUS` (`ID_REQUEST_STATUS`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_REQUESTS5` FOREIGN KEY (`USER_REQUEST`) REFERENCES `USERS` (`ID_USER`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_REQUESTS6` FOREIGN KEY (`USER_RESPONSABLE`) REFERENCES `USERS` (`ID_USER`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REQUESTS`
--

LOCK TABLES `REQUESTS` WRITE;
/*!40000 ALTER TABLE `REQUESTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REQUESTS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-29 12:38:16
