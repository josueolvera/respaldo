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
-- Table structure for table `ATTRIBUTES_TABLES`
--

DROP TABLE IF EXISTS `ATTRIBUTES_TABLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ATTRIBUTES_TABLES` (
  `ID_ATTRIBUTE_TABLE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_TABLE` int(11) NOT NULL,
  `ID_ATTRIBUTE` int(11) NOT NULL,
  `ID_DATA_TYPE` int(11) NOT NULL,
  PRIMARY KEY (`ID_ATTRIBUTE_TABLE`),
  KEY `FK_ATTRIBUTES_TABLES1` (`ID_TABLE`),
  KEY `FK_ATTRIBUTES_TABLES2` (`ID_ATTRIBUTE`),
  KEY `FK_ATTRIBUTES_TABLES3` (`ID_DATA_TYPE`),
  CONSTRAINT `FK_ATTRIBUTES_TABLES1` FOREIGN KEY (`ID_TABLE`) REFERENCES `C_TABLES` (`ID_TABLE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ATTRIBUTES_TABLES2` FOREIGN KEY (`ID_ATTRIBUTE`) REFERENCES `C_ATTRIBUTES` (`ID_ATTRIBUTE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ATTRIBUTES_TABLES3` FOREIGN KEY (`ID_DATA_TYPE`) REFERENCES `C_DATA_TYPES` (`ID_DATA_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ATTRIBUTES_TABLES`
--

LOCK TABLES `ATTRIBUTES_TABLES` WRITE;
/*!40000 ALTER TABLE `ATTRIBUTES_TABLES` DISABLE KEYS */;
INSERT INTO `ATTRIBUTES_TABLES` VALUES (1,19,1,1),(2,19,4,1);
/*!40000 ALTER TABLE `ATTRIBUTES_TABLES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:36:42
