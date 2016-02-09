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
-- Table structure for table `DW_EMPLOYEES`
--

DROP TABLE IF EXISTS `DW_EMPLOYEES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DW_EMPLOYEES` (
  `ID_DW_EMPLOYEE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_EMPLOYEE` int(11) NOT NULL,
  `ID_DW_ENTERPRISE` int(11) NOT NULL,
  `ID_ROLE` int(11) NOT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_DW_EMPLOYEE`),
  KEY `FK_DW_EMPLOYEES_1_idx` (`ID_EMPLOYEE`),
  KEY `FK_DW_EMPLOYEES_2_idx` (`ID_DW_ENTERPRISE`),
  KEY `FK_DW_EMPLOYEES_3_idx` (`ID_ROLE`),
  CONSTRAINT `FKokj43pyyh7lw80gh97vknta7m` FOREIGN KEY (`ID_ROLE`) REFERENCES `C_ROLES` (`ID_ROLE`),
  CONSTRAINT `FK475hsey3wix7m37o1bwn6dcma` FOREIGN KEY (`ID_DW_ENTERPRISE`) REFERENCES `DW_ENTERPRISES` (`ID_DW_ENTERPRISE`),
  CONSTRAINT `FKtrfp76nnsgktd1s7db2bd8nsk` FOREIGN KEY (`ID_EMPLOYEE`) REFERENCES `EMPLOYEES` (`ID_EMPLOYEE`),
  CONSTRAINT `FK_DW_EMPLOYEES_1` FOREIGN KEY (`ID_EMPLOYEE`) REFERENCES `EMPLOYEES` (`ID_EMPLOYEE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_DW_EMPLOYEES_2` FOREIGN KEY (`ID_DW_ENTERPRISE`) REFERENCES `DW_ENTERPRISES` (`ID_DW_ENTERPRISE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_DW_EMPLOYEES_3` FOREIGN KEY (`ID_ROLE`) REFERENCES `C_ROLES` (`ID_ROLE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-09 17:44:18
