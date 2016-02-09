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
-- Table structure for table `C_CURRENCIES`
--

DROP TABLE IF EXISTS `C_CURRENCIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `C_CURRENCIES` (
  `ID_CURRENCY` int(11) NOT NULL AUTO_INCREMENT,
  `CURRENCY` varchar(50) DEFAULT NULL,
  `ACRONYM` varchar(10) NOT NULL,
  `RATE` decimal(10,8) NOT NULL DEFAULT '1.00000000',
  PRIMARY KEY (`ID_CURRENCY`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `C_CURRENCIES`
--

LOCK TABLES `C_CURRENCIES` WRITE;
/*!40000 ALTER TABLE `C_CURRENCIES` DISABLE KEYS */;
INSERT INTO `C_CURRENCIES` VALUES (1,'Peso(s)','MXN',1.00000000),(2,'Dólar(es)','USD',0.05359000),(3,'Euro(s)','EUR',0.04827500),(4,'Dólar(es) Canadiense(s)','CAD',0.07476400);
/*!40000 ALTER TABLE `C_CURRENCIES` ENABLE KEYS */;
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
