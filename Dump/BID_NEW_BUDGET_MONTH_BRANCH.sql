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
-- Table structure for table `BUDGET_MONTH_BRANCH`
--

DROP TABLE IF EXISTS `BUDGET_MONTH_BRANCH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BUDGET_MONTH_BRANCH` (
  `ID_BUDGET_MONTH_BRANCH` int(11) NOT NULL AUTO_INCREMENT,
  `ID_BUDGET` int(11) NOT NULL,
  `ID_MONTH` int(11) NOT NULL,
  `ID_DW_ENTERPRISE` int(11) NOT NULL,
  `AMOUNT` decimal(14,2) NOT NULL DEFAULT '0.00',
  `EXPENDED_AMOUNT` decimal(14,2) NOT NULL DEFAULT '0.00',
  `YEAR` int(11) NOT NULL,
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  `IS_AUTHORIZED` int(11) NOT NULL DEFAULT '0',
  `ID_CURRENCY` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_BUDGET_MONTH_BRANCH`),
  KEY `FK_BUDGET_MONTH2` (`ID_MONTH`),
  KEY `FK_BUDGET_MONTH1` (`ID_BUDGET`),
  KEY `FK_BUDGET_MONTH_3_idx` (`ID_DW_ENTERPRISE`),
  KEY `FK_BUDGET_MONTH4_idx` (`ID_CURRENCY`),
  CONSTRAINT `FK_BUDGET_MONTH4` FOREIGN KEY (`ID_CURRENCY`) REFERENCES `C_CURRENCIES` (`ID_CURRENCY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGET_MONTH1` FOREIGN KEY (`ID_BUDGET`) REFERENCES `BUDGETS` (`ID_BUDGET`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGET_MONTH2` FOREIGN KEY (`ID_MONTH`) REFERENCES `C_MONTHS` (`ID_MONTH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGET_MONTH3` FOREIGN KEY (`ID_DW_ENTERPRISE`) REFERENCES `DW_ENTERPRISES` (`ID_DW_ENTERPRISE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BUDGET_MONTH_BRANCH`
--

LOCK TABLES `BUDGET_MONTH_BRANCH` WRITE;
/*!40000 ALTER TABLE `BUDGET_MONTH_BRANCH` DISABLE KEYS */;
INSERT INTO `BUDGET_MONTH_BRANCH` VALUES (30,1,1,81,15350.00,0.00,2015,1,0,1),(31,1,2,81,15750.00,0.00,2015,1,0,1),(32,1,3,81,15750.00,0.00,2015,1,0,1),(33,1,4,81,15750.00,0.00,2015,1,0,1),(34,1,5,81,15750.00,0.00,2015,1,0,1),(35,1,6,81,15750.00,0.00,2015,1,0,1),(36,1,7,81,15750.00,0.00,2015,1,0,1),(37,1,8,81,15750.00,0.00,2015,1,0,1),(38,1,9,81,15750.00,0.00,2015,1,0,1),(39,1,10,81,15750.00,0.00,2015,1,0,1),(40,1,11,81,15750.00,0.00,2015,1,0,1),(41,1,12,81,15750.00,0.00,2015,1,0,1),(42,123,1,86,0.00,0.00,2015,1,0,1),(43,123,2,86,0.00,0.00,2015,1,0,1),(44,123,3,86,0.00,0.00,2015,1,0,1),(45,123,4,86,0.00,0.00,2015,1,0,1),(46,123,5,86,0.00,0.00,2015,1,0,1),(47,123,6,86,0.00,0.00,2015,1,0,1),(48,123,7,86,0.00,0.00,2015,1,0,1),(49,123,8,86,0.00,0.00,2015,1,0,1),(50,123,9,86,0.00,0.00,2015,1,0,1),(51,123,10,86,0.00,0.00,2015,1,0,1),(52,123,11,86,0.00,0.00,2015,1,0,1),(53,123,12,86,0.00,0.00,2015,1,0,1),(54,5,1,81,5000.00,0.00,2015,1,0,1),(55,5,2,81,5000.00,0.00,2015,1,0,1),(56,5,3,81,5000.00,0.00,2015,1,0,1),(57,5,4,81,5000.00,0.00,2015,1,0,1),(58,5,5,81,5000.00,0.00,2015,1,0,1),(59,5,6,81,5000.00,0.00,2015,1,0,1),(60,5,7,81,5000.00,0.00,2015,1,0,1),(61,5,8,81,5000.00,0.00,2015,1,0,1),(62,5,9,81,5000.00,0.00,2015,1,0,1),(63,5,10,81,5000.00,0.00,2015,1,0,1),(64,5,11,81,5000.00,0.00,2015,1,0,1),(65,5,12,81,5000.00,0.00,2015,1,0,1),(66,74,1,84,0.00,0.00,2015,1,0,1),(67,74,2,84,0.00,0.00,2015,1,0,1),(68,74,3,84,0.00,0.00,2015,1,0,1),(69,74,4,84,0.00,0.00,2015,1,0,1),(70,74,5,84,0.00,0.00,2015,1,0,1),(71,74,6,84,0.00,0.00,2015,1,0,1),(72,74,7,84,0.00,0.00,2015,1,0,1),(73,74,8,84,0.00,0.00,2015,1,0,1),(74,74,9,84,0.00,0.00,2015,1,0,1),(75,74,10,84,0.00,0.00,2015,1,0,1),(76,74,11,84,0.00,0.00,2015,1,0,1),(77,74,12,84,0.00,0.00,2015,1,0,1),(78,7,1,81,0.00,0.00,2015,1,0,1),(79,7,2,81,0.00,0.00,2015,1,0,1),(80,7,3,81,0.00,0.00,2015,1,0,1),(81,7,4,81,0.00,0.00,2015,1,0,1),(82,7,5,81,0.00,0.00,2015,1,0,1),(83,7,6,81,0.00,0.00,2015,1,0,1),(84,7,7,81,0.00,0.00,2015,1,0,1),(85,7,8,81,0.00,0.00,2015,1,0,1),(86,7,9,81,0.00,0.00,2015,1,0,1),(87,7,10,81,0.00,0.00,2015,1,0,1),(88,7,11,81,0.00,0.00,2015,1,0,1),(89,7,12,81,0.00,0.00,2015,1,0,1),(90,6,1,81,60.00,0.00,2015,1,0,1),(91,6,2,81,60.00,0.00,2015,1,0,1),(92,6,3,81,60.00,0.00,2015,1,0,1),(93,6,4,81,60.00,0.00,2015,1,0,1),(94,6,5,81,60.00,0.00,2015,1,0,1),(95,6,6,81,60.00,0.00,2015,1,0,1),(96,6,7,81,60.00,0.00,2015,1,0,1),(97,6,8,81,60.00,0.00,2015,1,0,1),(98,6,9,81,60.00,0.00,2015,1,0,1),(99,6,10,81,60.00,0.00,2015,1,0,1),(100,6,11,81,60.00,0.00,2015,1,0,1),(101,6,12,81,60.00,0.00,2015,1,0,1),(102,75,1,84,0.00,0.00,2015,1,0,1),(103,75,2,84,0.00,0.00,2015,1,0,1),(104,75,3,84,0.00,0.00,2015,1,0,1),(105,75,4,84,0.00,0.00,2015,1,0,1),(106,75,5,84,0.00,0.00,2015,1,0,1),(107,75,6,84,0.00,0.00,2015,1,0,1),(108,75,7,84,0.00,0.00,2015,1,0,1),(109,75,8,84,0.00,0.00,2015,1,0,1),(110,75,9,84,0.00,0.00,2015,1,0,1),(111,75,10,84,0.00,0.00,2015,1,0,1),(112,75,11,84,0.00,0.00,2015,1,0,1),(113,75,12,84,0.00,0.00,2015,1,0,1),(114,4,1,81,1010.00,0.00,2015,1,0,1),(115,4,2,81,1010.00,0.00,2015,1,0,1),(116,4,3,81,1010.00,0.00,2015,1,0,1),(117,4,4,81,1010.00,0.00,2015,1,0,1),(118,4,5,81,1010.00,0.00,2015,1,0,1),(119,4,6,81,1010.00,0.00,2015,1,0,1),(120,4,7,81,1010.00,0.00,2015,1,0,1),(121,4,8,81,1010.00,0.00,2015,1,0,1),(122,4,9,81,1010.00,0.00,2015,1,0,1),(123,4,10,81,1010.00,0.00,2015,1,0,1),(124,4,11,81,1010.00,0.00,2015,1,0,1),(125,4,12,81,1010.00,0.00,2015,1,0,1),(126,76,1,84,0.00,0.00,2015,1,0,1),(127,76,2,84,0.00,0.00,2015,1,0,1),(128,76,3,84,0.00,0.00,2015,1,0,1),(129,76,4,84,0.00,0.00,2015,1,0,1),(130,76,5,84,0.00,0.00,2015,1,0,1),(131,76,6,84,0.00,0.00,2015,1,0,1),(132,76,7,84,0.00,0.00,2015,1,0,1),(133,76,8,84,0.00,0.00,2015,1,0,1),(134,76,9,84,0.00,0.00,2015,1,0,1),(135,76,10,84,0.00,0.00,2015,1,0,1),(136,76,11,84,0.00,0.00,2015,1,0,1),(137,76,12,84,0.00,0.00,2015,1,0,1),(138,78,1,84,0.00,0.00,2015,1,0,1),(139,78,2,84,0.00,0.00,2015,1,0,1),(140,78,3,84,0.00,0.00,2015,1,0,1),(141,78,4,84,0.00,0.00,2015,1,0,1),(142,78,5,84,0.00,0.00,2015,1,0,1),(143,78,6,84,0.00,0.00,2015,1,0,1),(144,78,7,84,0.00,0.00,2015,1,0,1),(145,78,8,84,0.00,0.00,2015,1,0,1),(146,78,9,84,0.00,0.00,2015,1,0,1),(147,78,10,84,0.00,0.00,2015,1,0,1),(148,78,11,84,0.00,0.00,2015,1,0,1),(149,78,12,84,0.00,0.00,2015,1,0,1),(150,77,1,84,0.00,0.00,2015,1,0,1),(151,77,2,84,0.00,0.00,2015,1,0,1),(152,77,3,84,0.00,0.00,2015,1,0,1),(153,77,4,84,0.00,0.00,2015,1,0,1),(154,77,5,84,0.00,0.00,2015,1,0,1),(155,77,6,84,0.00,0.00,2015,1,0,1),(156,77,7,84,0.00,0.00,2015,1,0,1),(157,77,8,84,0.00,0.00,2015,1,0,1),(158,77,9,84,0.00,0.00,2015,1,0,1),(159,77,10,84,0.00,0.00,2015,1,0,1),(160,77,11,84,0.00,0.00,2015,1,0,1),(161,77,12,84,0.00,0.00,2015,1,0,1),(162,79,1,84,0.00,0.00,2015,1,0,1),(163,79,2,84,0.00,0.00,2015,1,0,1),(164,79,3,84,0.00,0.00,2015,1,0,1),(165,79,4,84,0.00,0.00,2015,1,0,1),(166,79,5,84,0.00,0.00,2015,1,0,1),(167,79,6,84,0.00,0.00,2015,1,0,1),(168,79,7,84,0.00,0.00,2015,1,0,1),(169,79,8,84,0.00,0.00,2015,1,0,1),(170,79,9,84,0.00,0.00,2015,1,0,1),(171,79,10,84,0.00,0.00,2015,1,0,1),(172,79,11,84,0.00,0.00,2015,1,0,1),(173,79,12,84,0.00,0.00,2015,1,0,1),(174,10,1,81,900.00,0.00,2015,1,0,1),(175,10,2,81,900.00,0.00,2015,1,0,1),(176,10,3,81,900.00,0.00,2015,1,0,1),(177,10,4,81,900.00,0.00,2015,1,0,1),(178,10,5,81,900.00,0.00,2015,1,0,1),(179,10,6,81,900.00,0.00,2015,1,0,1),(180,10,7,81,900.00,0.00,2015,1,0,1),(181,10,8,81,900.00,0.00,2015,1,0,1),(182,10,9,81,900.00,0.00,2015,1,0,1),(183,10,10,81,900.00,0.00,2015,1,0,1),(184,10,11,81,900.00,0.00,2015,1,0,1),(185,10,12,81,900.00,0.00,2015,1,0,1),(186,118,1,86,85.00,0.00,2015,1,0,1),(187,118,2,86,85.00,0.00,2015,1,0,1),(188,118,3,86,85.00,0.00,2015,1,0,1),(189,118,4,86,85.00,0.00,2015,1,0,1),(190,118,5,86,85.00,0.00,2015,1,0,1),(191,118,6,86,85.00,0.00,2015,1,0,1),(192,118,7,86,85.00,0.00,2015,1,0,1),(193,118,8,86,85.00,0.00,2015,1,0,1),(194,118,9,86,85.00,0.00,2015,1,0,1),(195,118,10,86,85.00,0.00,2015,1,0,1),(196,118,11,86,85.00,0.00,2015,1,0,1),(197,118,12,86,85.00,0.00,2015,1,0,1),(198,119,1,86,600.00,0.00,2015,1,0,1),(199,119,2,86,600.00,0.00,2015,1,0,1),(200,119,3,86,600.00,0.00,2015,1,0,1),(201,119,4,86,600.00,0.00,2015,1,0,1),(202,119,5,86,600.00,0.00,2015,1,0,1),(203,119,6,86,600.00,0.00,2015,1,0,1),(204,119,7,86,600.00,0.00,2015,1,0,1),(205,119,8,86,600.00,0.00,2015,1,0,1),(206,119,9,86,600.00,0.00,2015,1,0,1),(207,119,10,86,600.00,0.00,2015,1,0,1),(208,119,11,86,600.00,0.00,2015,1,0,1),(209,119,12,86,600.00,0.00,2015,1,0,1);
/*!40000 ALTER TABLE `BUDGET_MONTH_BRANCH` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-21 17:37:05
