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
-- Table structure for table `VIEWS_COMPONENTS_ROLE`
--

DROP TABLE IF EXISTS `VIEWS_COMPONENTS_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VIEWS_COMPONENTS_ROLE` (
  `ID_SYSTEM_ROLE` int(11) NOT NULL,
  `ID_VIEW_COMPONENT` int(11) NOT NULL,
  `ID_VIEW_COMPONENT_ROLE` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID_VIEW_COMPONENT_ROLE`),
  KEY `FKch4ly8rnmf6gyp1mg6o6oro1o` (`ID_SYSTEM_ROLE`),
  KEY `FKi8tmcch3bnl35skploatdjjg6` (`ID_VIEW_COMPONENT`),
  CONSTRAINT `FKi8tmcch3bnl35skploatdjjg6` FOREIGN KEY (`ID_VIEW_COMPONENT`) REFERENCES `VIEWS_COMPONENT` (`ID_VIEW_COMPONENT`),
  CONSTRAINT `FKch4ly8rnmf6gyp1mg6o6oro1o` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`),
  CONSTRAINT `FK_VIEWS_COMPONENTS_ROLE1` FOREIGN KEY (`ID_VIEW_COMPONENT`) REFERENCES `VIEWS_COMPONENT` (`ID_VIEW_COMPONENT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VIEWS_COMPONENTS_ROLE2` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VIEWS_COMPONENTS_ROLE`
--

LOCK TABLES `VIEWS_COMPONENTS_ROLE` WRITE;
/*!40000 ALTER TABLE `VIEWS_COMPONENTS_ROLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `VIEWS_COMPONENTS_ROLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:36:48
