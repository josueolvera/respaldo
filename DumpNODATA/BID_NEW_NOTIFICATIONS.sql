-- MySQL dump 10.16  Distrib 10.1.13-MariaDB, for Linux (x86_64)
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
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='' */;
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
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_NOTIFICATION`),
  KEY `FK_NOTIFICATIONS_RESOURCES_TASKS` (`ID_RESOURCE_TASK`),
  KEY `FK_NOTIFICATIONS_USERS` (`ID_USER`),
  KEY `FK_NOTIFICATIONS_C_NOTIFICATION_TYPES` (`ID_NOTIFICATION_TYPE`),
  KEY `FK_NOTIFICATIONS_C_NOTIFICATIONS_STATUS` (`ID_NOTIFICATION_STATUS`),
  CONSTRAINT `FK_NOTIFICATIONS_C_NOTIFICATIONS_STATUS` FOREIGN KEY (`ID_NOTIFICATION_STATUS`) REFERENCES `C_NOTIFICATIONS_STATUS` (`ID_NOTIFICATION_STATUS`),
  CONSTRAINT `FK_NOTIFICATIONS_C_NOTIFICATION_TYPES` FOREIGN KEY (`ID_NOTIFICATION_TYPE`) REFERENCES `C_NOTIFICATION_TYPES` (`ID_NOTIFICATION_TYPE`),
  CONSTRAINT `FK_NOTIFICATIONS_RESOURCES_TASKS` FOREIGN KEY (`ID_RESOURCE_TASK`) REFERENCES `RESOURCES_TASKS` (`ID_RESOURCE_TASK`),
  CONSTRAINT `FK_NOTIFICATIONS_USERS` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-20 18:57:04
