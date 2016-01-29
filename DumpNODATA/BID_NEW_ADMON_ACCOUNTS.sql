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
-- Table structure for table `ADMON_ACCOUNTS`
--

DROP TABLE IF EXISTS `ADMON_ACCOUNTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADMON_ACCOUNTS` (
  `ID_ADMON_ACCOUNT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DISTRIBUTOR` int(11) NOT NULL,
  `ID_BANK` int(11) NOT NULL,
  `ACCOUNT_NUMBER` varchar(45) DEFAULT NULL,
  `ACCOUNT_CLABE` varchar(45) DEFAULT NULL,
  `ID_ACCESS_LEVEL` int(11) DEFAULT '1',
  `ID_CURRENCY` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_ADMON_ACCOUNT`),
  KEY `FK_ADMON_ACCOUNTS1` (`ID_BANK`),
  KEY `FK_ADMON_ACCOUNTS2_idx` (`ID_CURRENCY`),
  KEY `FK_ADMON_ACCOUNTS3_idx` (`ID_DISTRIBUTOR`),
  CONSTRAINT `FK_ADMON_ACCOUNTS3` FOREIGN KEY (`ID_DISTRIBUTOR`) REFERENCES `C_DISTRIBUTORS` (`ID_DISTRIBUTOR`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ADMON_ACCOUNTS1` FOREIGN KEY (`ID_BANK`) REFERENCES `C_BANKS` (`ID_BANK`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ADMON_ACCOUNTS2` FOREIGN KEY (`ID_CURRENCY`) REFERENCES `C_CURRENCIES` (`ID_CURRENCY`) ON DELETE NO ACTION ON UPDATE NO ACTION
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

-- Dump completed on 2016-01-29 12:37:47
