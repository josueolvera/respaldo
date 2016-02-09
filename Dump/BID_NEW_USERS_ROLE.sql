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
-- Table structure for table `USERS_ROLE`
--

DROP TABLE IF EXISTS `USERS_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS_ROLE` (
  `ID_USER_ROLE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_USER` int(11) NOT NULL,
  `ID_SYSTEM_ROLE` int(11) NOT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_USER_ROLE`),
  KEY `FK2w5c2aulk0lx1sp25o8f9jmdd` (`ID_SYSTEM_ROLE`),
  KEY `FKlgjo5odvjowk3lqb6r4tgio0` (`ID_USER`),
  CONSTRAINT `FKlgjo5odvjowk3lqb6r4tgio0` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`),
  CONSTRAINT `FK2w5c2aulk0lx1sp25o8f9jmdd` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`),
  CONSTRAINT `FK_USERS_ROLE1` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USERS_ROLE2` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS_ROLE`
--

LOCK TABLES `USERS_ROLE` WRITE;
/*!40000 ALTER TABLE `USERS_ROLE` DISABLE KEYS */;
INSERT INTO `USERS_ROLE` VALUES (1,1,1,'2015-11-12 00:21:50'),(2,2,1,'2015-11-13 23:07:07'),(3,2,3,'2015-11-23 23:45:37');
/*!40000 ALTER TABLE `USERS_ROLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-09 17:43:41
