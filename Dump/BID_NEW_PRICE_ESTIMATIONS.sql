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
-- Table structure for table `PRICE_ESTIMATIONS`
--

DROP TABLE IF EXISTS `PRICE_ESTIMATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRICE_ESTIMATIONS` (
  `ID_ESTIMATION` int(11) NOT NULL AUTO_INCREMENT,
  `ID_REQUEST` int(11) NOT NULL,
  `ID_ACCOUNT` int(11) NOT NULL,
  `ID_ESTIMATION_STATUS` int(11) NOT NULL,
  `AMOUNT` decimal(14,2) NOT NULL DEFAULT '0.00',
  `FILE_PATH` varchar(100) DEFAULT NULL,
  `FILE_NAME` varchar(45) DEFAULT NULL,
  `SKU` varchar(45) DEFAULT NULL,
  `ID_CURRENCY` int(11) NOT NULL DEFAULT '1',
  `USER_ESTIMATION` int(11) NOT NULL,
  `USER_AUTHORIZATION` int(11) DEFAULT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `AUTHORIZATION_DATE` timestamp NULL DEFAULT NULL,
  `OUT_OF_BUDGET` int(11) NOT NULL DEFAULT '0',
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  `RATE` decimal(10,8) NOT NULL DEFAULT '1.00000000',
  PRIMARY KEY (`ID_ESTIMATION`),
  KEY `FK_PRICE_ESTIMATIONS4_idx` (`ID_CURRENCY`),
  KEY `FK_PRICE_ESTIMATIONS5_idx` (`USER_ESTIMATION`),
  KEY `FK_PRICE_ESTIMATIONS6_idx` (`USER_AUTHORIZATION`),
  KEY `FKav9ps4bw1e4gxkkgp3wtl030k` (`ID_ACCOUNT`),
  KEY `FKhurqtmy94jt47pm585kls3y9p` (`ID_ESTIMATION_STATUS`),
  KEY `FK6wa8i85j2s15xqt45byj4pp7o` (`ID_REQUEST`),
  CONSTRAINT `FK22cw3xt9fvbxbi4teuoj465ou` FOREIGN KEY (`USER_AUTHORIZATION`) REFERENCES `USERS` (`ID_USER`),
  CONSTRAINT `FK29rqognvqgv0kdaxd9t32h1ff` FOREIGN KEY (`ID_CURRENCY`) REFERENCES `C_CURRENCIES` (`ID_CURRENCY`),
  CONSTRAINT `FK612pyltxlbvmwd5os8f9r0cya` FOREIGN KEY (`USER_ESTIMATION`) REFERENCES `USERS` (`ID_USER`),
  CONSTRAINT `FK6wa8i85j2s15xqt45byj4pp7o` FOREIGN KEY (`ID_REQUEST`) REFERENCES `REQUESTS` (`ID_REQUEST`),
  CONSTRAINT `FKav9ps4bw1e4gxkkgp3wtl030k` FOREIGN KEY (`ID_ACCOUNT`) REFERENCES `ACCOUNTS` (`ID_ACCOUNT`),
  CONSTRAINT `FKhurqtmy94jt47pm585kls3y9p` FOREIGN KEY (`ID_ESTIMATION_STATUS`) REFERENCES `C_ESTIMATION_STATUS` (`ID_ESTIMATION_STATUS`),
  CONSTRAINT `FK_PRICE_ESTIMATIONS1` FOREIGN KEY (`ID_REQUEST`) REFERENCES `REQUESTS` (`ID_REQUEST`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRICE_ESTIMATIONS2` FOREIGN KEY (`ID_ESTIMATION_STATUS`) REFERENCES `C_ESTIMATION_STATUS` (`ID_ESTIMATION_STATUS`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRICE_ESTIMATIONS3` FOREIGN KEY (`ID_ACCOUNT`) REFERENCES `ACCOUNTS` (`ID_ACCOUNT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRICE_ESTIMATIONS4` FOREIGN KEY (`ID_CURRENCY`) REFERENCES `C_CURRENCIES` (`ID_CURRENCY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRICE_ESTIMATIONS5` FOREIGN KEY (`USER_ESTIMATION`) REFERENCES `USERS` (`ID_USER`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRICE_ESTIMATIONS6` FOREIGN KEY (`USER_AUTHORIZATION`) REFERENCES `USERS` (`ID_USER`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRICE_ESTIMATIONS`
--

LOCK TABLES `PRICE_ESTIMATIONS` WRITE;
/*!40000 ALTER TABLE `PRICE_ESTIMATIONS` DISABLE KEYS */;
/*!40000 ALTER TABLE `PRICE_ESTIMATIONS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:36:44
