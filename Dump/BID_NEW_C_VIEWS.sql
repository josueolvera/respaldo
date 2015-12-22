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
-- Table structure for table `C_VIEWS`
--

DROP TABLE IF EXISTS `C_VIEWS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `C_VIEWS` (
  `ID_MODULE` int(11) NOT NULL,
  `ID_VIEW` int(11) NOT NULL AUTO_INCREMENT,
  `ID_TASK` int(11) DEFAULT NULL,
  `VIEW_NAME` varchar(100) NOT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_VIEW`),
  KEY `FK_VIEWS_MODULES` (`ID_MODULE`),
  KEY `FK_VIEWS_TASKS_idx` (`ID_TASK`),
  CONSTRAINT `FK_VIEWS_TASKS` FOREIGN KEY (`ID_TASK`) REFERENCES `C_TASKS` (`ID_TASK`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VIEWS_MODULES` FOREIGN KEY (`ID_MODULE`) REFERENCES `C_MODULES` (`ID_MODULE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `C_VIEWS`
--

LOCK TABLES `C_VIEWS` WRITE;
/*!40000 ALTER TABLE `C_VIEWS` DISABLE KEYS */;
INSERT INTO `C_VIEWS` VALUES (1,1,27,'Presupuesto Anual','2015-11-30 15:55:10'),(2,2,28,'Solicitudes Pendientes','2015-11-30 16:56:46'),(2,3,29,'Modificacion de Solicitud','2015-11-30 17:04:02'),(3,4,31,'Reservación de Salas','2015-12-02 00:36:03'),(4,5,45,'Control de Inventario','2015-12-10 22:03:19');
/*!40000 ALTER TABLE `C_VIEWS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-21 18:41:24
