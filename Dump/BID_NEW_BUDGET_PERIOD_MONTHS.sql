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
-- Table structure for table `BUDGET_PERIOD_MONTHS`
--

DROP TABLE IF EXISTS `BUDGET_PERIOD_MONTHS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BUDGET_PERIOD_MONTHS` (
  `ID_BUDGET_PERIOD_MONTH` int(11) NOT NULL AUTO_INCREMENT,
  `ID_BUDGET_PERIOD` int(11) DEFAULT NULL,
  `ID_MONTH_FIRST` int(11) DEFAULT NULL,
  `ID_MONTH_LAST` int(11) DEFAULT NULL,
  `ID_ACCESS_LEVEL` int(11) DEFAULT '1',
  PRIMARY KEY (`ID_BUDGET_PERIOD_MONTH`),
  KEY `FK_BUDGET_PERIOD_MONTHS2` (`ID_BUDGET_PERIOD`),
  KEY `FK_BUDGET_PERIOD_MONTHS3` (`ID_MONTH_FIRST`),
  KEY `FK_BUDGET_PERIOD_MONTHS4` (`ID_MONTH_LAST`),
  CONSTRAINT `FK_BUDGET_PERIOD_MONTHS1` FOREIGN KEY (`ID_BUDGET_PERIOD`) REFERENCES `C_BUDGET_PERIODS` (`ID_BUDGET_PERIOD`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGET_PERIOD_MONTHS2` FOREIGN KEY (`ID_BUDGET_PERIOD`) REFERENCES `C_BUDGET_PERIODS` (`ID_BUDGET_PERIOD`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGET_PERIOD_MONTHS3` FOREIGN KEY (`ID_MONTH_FIRST`) REFERENCES `C_MONTHS` (`ID_MONTH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGET_PERIOD_MONTHS4` FOREIGN KEY (`ID_MONTH_LAST`) REFERENCES `C_MONTHS` (`ID_MONTH`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BUDGET_PERIOD_MONTHS`
--

LOCK TABLES `BUDGET_PERIOD_MONTHS` WRITE;
/*!40000 ALTER TABLE `BUDGET_PERIOD_MONTHS` DISABLE KEYS */;
INSERT INTO `BUDGET_PERIOD_MONTHS` VALUES (1,1,1,1,1),(2,1,2,2,1),(3,1,3,3,1),(4,1,4,4,1),(5,1,5,5,1),(6,1,6,6,1),(7,1,7,7,1),(8,1,8,8,1),(9,1,9,9,1),(10,1,10,10,1),(11,1,11,11,1),(12,1,12,12,1),(13,2,1,2,1),(14,2,3,4,1),(15,2,5,6,1),(16,2,7,8,1),(17,2,9,10,1),(18,2,11,12,1),(19,3,1,3,1),(20,3,4,6,1),(21,3,7,9,1),(22,3,10,12,1),(23,4,1,4,1),(24,4,5,8,1),(25,4,9,12,1),(26,5,1,6,1),(27,5,7,12,1),(28,6,1,12,1);
/*!40000 ALTER TABLE `BUDGET_PERIOD_MONTHS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-21 17:37:08
