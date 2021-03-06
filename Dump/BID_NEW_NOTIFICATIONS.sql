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
-- Table structure for table `NOTIFICATIONS`
--

DROP TABLE IF EXISTS `NOTIFICATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NOTIFICATIONS` (
  `ID_NOTIFICATION` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(200) DEFAULT NULL,
  `SUBTITLE` varchar(200) DEFAULT NULL,
  `TEXT` varchar(200) DEFAULT NULL,
  `ID_RESOURCE_TASK` int(11) NOT NULL,
  `ID_RESOURCE` int(11) NOT NULL,
  `ID_USER` int(11) DEFAULT NULL,
  `ID_NOTIFICATION_TYPE` int(11) NOT NULL,
  `ID_NOTIFICATION_STATUS` int(11) NOT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DUE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID_NOTIFICATION`),
  KEY `FK_NOTIFICATIONS_RESOURCES_TASKS` (`ID_RESOURCE_TASK`),
  KEY `FK_NOTIFICATIONS_USERS` (`ID_USER`),
  KEY `FK_NOTIFICATIONS_C_NOTIFICATION_TYPES` (`ID_NOTIFICATION_TYPE`),
  KEY `FK_NOTIFICATIONS_C_NOTIFICATIONS_STATUS` (`ID_NOTIFICATION_STATUS`),
  CONSTRAINT `FK_NOTIFICATIONS_C_NOTIFICATIONS_STATUS` FOREIGN KEY (`ID_NOTIFICATION_STATUS`) REFERENCES `C_NOTIFICATIONS_STATUS` (`ID_NOTIFICATION_STATUS`),
  CONSTRAINT `FK_NOTIFICATIONS_C_NOTIFICATION_TYPES` FOREIGN KEY (`ID_NOTIFICATION_TYPE`) REFERENCES `C_NOTIFICATION_TYPES` (`ID_NOTIFICATION_TYPE`),
  CONSTRAINT `FK_NOTIFICATIONS_RESOURCES_TASKS` FOREIGN KEY (`ID_RESOURCE_TASK`) REFERENCES `RESOURCES_TASKS` (`ID_RESOURCE_TASK`),
  CONSTRAINT `FK_NOTIFICATIONS_USERS` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NOTIFICATIONS`
--

LOCK TABLES `NOTIFICATIONS` WRITE;
/*!40000 ALTER TABLE `NOTIFICATIONS` DISABLE KEYS */;
INSERT INTO `NOTIFICATIONS` VALUES (1,'Solicitud','Requiere autorizacion','\"\"',1,1,2,1,1,'2016-02-10 19:07:18','2016-02-16 15:00:00'),(2,'Transacción ','Requiere autorizacion','\"\"',1,2,2,2,1,'2016-02-10 22:59:04','2016-02-17 15:00:00'),(3,'Inventario','Requiere autorizacion','\"\"',1,3,2,3,1,'2016-02-10 22:59:04','2016-02-26 15:00:00'),(4,'Cuenta por pagar','Requiere autorizacion','\"\"',1,4,2,4,1,'2016-02-10 22:59:04','2016-02-29 15:00:00'),(5,'Cuenta por pagar','Requiere autorizacion','\"\"',1,2,2,4,1,'2016-02-16 23:46:32','2016-02-22 23:46:32'),(6,'Inventario','Requiere autorizacion','\"\"',1,3,2,3,1,'2016-02-17 00:21:07','2016-02-17 00:21:07'),(7,'Solicitud','Requiere autorizacion','\"\"',1,4,2,1,1,'2016-02-17 00:27:02','2016-02-20 00:27:02');
/*!40000 ALTER TABLE `NOTIFICATIONS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:36:47
