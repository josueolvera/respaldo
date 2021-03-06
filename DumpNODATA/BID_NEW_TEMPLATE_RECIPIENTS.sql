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
-- Table structure for table `TEMPLATE_RECIPIENTS`
--

DROP TABLE IF EXISTS `TEMPLATE_RECIPIENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TEMPLATE_RECIPIENTS` (
  `ID_EMAIL_TEMPLATE` int(11) NOT NULL,
  `ID_EMAIL_RECIPIENT` int(11) NOT NULL,
  KEY `TEMPLATE_RECIPIENTS_EMAIL_TEMPLATES` (`ID_EMAIL_TEMPLATE`),
  KEY `TEMPLATE_RECIPIENTS_EMAIL_RECIPIENTS` (`ID_EMAIL_RECIPIENT`),
  CONSTRAINT `TEMPLATE_RECIPIENTS_EMAIL_RECIPIENTS` FOREIGN KEY (`ID_EMAIL_RECIPIENT`) REFERENCES `EMAIL_RECIPIENTS` (`ID_EMAIL_RECIPIENT`),
  CONSTRAINT `TEMPLATE_RECIPIENTS_EMAIL_TEMPLATES` FOREIGN KEY (`ID_EMAIL_TEMPLATE`) REFERENCES `EMAIL_TEMPLATES` (`ID_EMAIL_TEMPLATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-20 18:57:04
