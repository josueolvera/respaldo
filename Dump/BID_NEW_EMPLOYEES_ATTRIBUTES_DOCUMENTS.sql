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
-- Table structure for table `EMPLOYEES_ATTRIBUTES_DOCUMENTS`
--

DROP TABLE IF EXISTS `EMPLOYEES_ATTRIBUTES_DOCUMENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EMPLOYEES_ATTRIBUTES_DOCUMENTS` (
  `ID_EMPLOYEE_ATTRIBUTE_DOCUMENT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_EMPLOYEE_ATTRIBUTE` int(11) DEFAULT NULL,
  `ID_DOCUMENT_TYPE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_EMPLOYEE_ATTRIBUTE_DOCUMENT`),
  UNIQUE KEY `UNIQUE_ATTRIBUTE_DOCUMENT` (`ID_EMPLOYEE_ATTRIBUTE`,`ID_DOCUMENT_TYPE`),
  KEY `ID_DOCUMENT_TYPE` (`ID_DOCUMENT_TYPE`),
  CONSTRAINT `EMPLOYEES_ATTRIBUTES_DOCUMENTS_ibfk_1` FOREIGN KEY (`ID_EMPLOYEE_ATTRIBUTE`) REFERENCES `EMPLOYEE_ATTRIBUTES` (`ID_EMPLOYEE_ATTRIBUTE`),
  CONSTRAINT `EMPLOYEES_ATTRIBUTES_DOCUMENTS_ibfk_2` FOREIGN KEY (`ID_DOCUMENT_TYPE`) REFERENCES `C_EMPLOYEE_DOCUMENTS_TYPES` (`ID_DOCUMENT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EMPLOYEES_ATTRIBUTES_DOCUMENTS`
--

LOCK TABLES `EMPLOYEES_ATTRIBUTES_DOCUMENTS` WRITE;
/*!40000 ALTER TABLE `EMPLOYEES_ATTRIBUTES_DOCUMENTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `EMPLOYEES_ATTRIBUTES_DOCUMENTS` ENABLE KEYS */;
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
