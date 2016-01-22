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
-- Table structure for table `EMPLOYEE_ATTRIBUTES`
--

DROP TABLE IF EXISTS `EMPLOYEE_ATTRIBUTES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EMPLOYEE_ATTRIBUTES` (
  `ID_EMPLOYEE_ATTRIBUTE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DW_ENTERPRISE` int(11) DEFAULT NULL,
  `ID_ROLE` int(11) DEFAULT NULL,
  `ID_ATTRIBUTE` int(11) DEFAULT NULL,
  `ID_DATA_TYPE` int(11) DEFAULT NULL,
  `REQUIRED` int(11) DEFAULT NULL,
  `SELECTABLE` int(11) DEFAULT '0',
  PRIMARY KEY (`ID_EMPLOYEE_ATTRIBUTE`),
  UNIQUE KEY `UNIQUE_ATTRIBUTE` (`ID_DW_ENTERPRISE`,`ID_ROLE`,`ID_ATTRIBUTE`),
  KEY `EMPLOYEE_ATTRIBUTES_C_ROLES_ID_ROLE_fk` (`ID_ROLE`),
  KEY `EMPLOYEE_ATTRIBUTES_C_ATTRIBUTES_ID_ATTRIBUTE_fk` (`ID_ATTRIBUTE`),
  KEY `EMPLOYEE_ATTRIBUTES_C_DATA_TYPES_ID_DATA_TYPE_fk` (`ID_DATA_TYPE`),
  CONSTRAINT `EMPLOYEE_ATTRIBUTES_C_ATTRIBUTES_ID_ATTRIBUTE_fk` FOREIGN KEY (`ID_ATTRIBUTE`) REFERENCES `C_ATTRIBUTES` (`ID_ATTRIBUTE`),
  CONSTRAINT `EMPLOYEE_ATTRIBUTES_C_DATA_TYPES_ID_DATA_TYPE_fk` FOREIGN KEY (`ID_DATA_TYPE`) REFERENCES `C_DATA_TYPES` (`ID_DATA_TYPE`),
  CONSTRAINT `EMPLOYEE_ATTRIBUTES_C_ROLES_ID_ROLE_fk` FOREIGN KEY (`ID_ROLE`) REFERENCES `C_ROLES` (`ID_ROLE`),
  CONSTRAINT `EMPLOYEE_ATTRIBUTES_DW_ENTERPRISES_ID_DW_ENTERPRISE_fk` FOREIGN KEY (`ID_DW_ENTERPRISE`) REFERENCES `DW_ENTERPRISES` (`ID_DW_ENTERPRISE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EMPLOYEE_ATTRIBUTES`
--

LOCK TABLES `EMPLOYEE_ATTRIBUTES` WRITE;
/*!40000 ALTER TABLE `EMPLOYEE_ATTRIBUTES` DISABLE KEYS */;
/*!40000 ALTER TABLE `EMPLOYEE_ATTRIBUTES` ENABLE KEYS */;
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
