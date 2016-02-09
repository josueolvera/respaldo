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
-- Table structure for table `EMPLOYEE_DOCUMENTS`
--

DROP TABLE IF EXISTS `EMPLOYEE_DOCUMENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EMPLOYEE_DOCUMENTS` (
  `ID_DOCUMENT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_EMPLOYEE` int(11) DEFAULT NULL,
  `ID_DOCUMENT_TYPE` int(11) DEFAULT NULL,
  `DOCUMENT_URL` varchar(2048) DEFAULT NULL,
  `DOCUMENT_NAME` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID_DOCUMENT`),
  UNIQUE KEY `UNIQUE_DOCUMENT` (`ID_EMPLOYEE`,`ID_DOCUMENT_TYPE`),
  KEY `ID_DOCUMENT_TYPE` (`ID_DOCUMENT_TYPE`),
  CONSTRAINT `EMPLOYEE_DOCUMENTS_ibfk_1` FOREIGN KEY (`ID_EMPLOYEE`) REFERENCES `EMPLOYEES` (`ID_EMPLOYEE`),
  CONSTRAINT `EMPLOYEE_DOCUMENTS_ibfk_2` FOREIGN KEY (`ID_DOCUMENT_TYPE`) REFERENCES `C_EMPLOYEE_DOCUMENTS_TYPES` (`ID_DOCUMENT_TYPE`)
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

-- Dump completed on 2016-02-09 17:44:19
