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
-- Table structure for table `ACCOUNTS_PAYABLE`
--

DROP TABLE IF EXISTS `ACCOUNTS_PAYABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACCOUNTS_PAYABLE` (
  `ID_ACCOUNT_PAYABLE` int(11) NOT NULL AUTO_INCREMENT,
  `FOLIO` varchar(40) NOT NULL,
  `AMOUNT` decimal(14,2) NOT NULL DEFAULT '0.00',
  `PAID_AMOUNT` decimal(14,2) NOT NULL DEFAULT '0.00',
  `PAY_NUM` int(11) NOT NULL DEFAULT '0',
  `TOTAL_PAYMENTS` int(11) NOT NULL DEFAULT '0',
  `ID_OPERATION_TYPE` int(11) NOT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ID_ACCOUNT_PAYABLE_STATUS` int(11) DEFAULT NULL,
  `ID_ACCESS_LEVEL` int(11) DEFAULT '1',
  PRIMARY KEY (`ID_ACCOUNT_PAYABLE`),
  KEY `FK_ACCOUNTS_PAYABLE1` (`FOLIO`),
  KEY `FK_ACCOUNTS_PAYABLE2` (`ID_ACCOUNT_PAYABLE_STATUS`),
  KEY `FK_ACCOUNTS_PAYABLE3_idx` (`ID_OPERATION_TYPE`),
  CONSTRAINT `FK_ACCOUNTS_PAYABLE3` FOREIGN KEY (`ID_OPERATION_TYPE`) REFERENCES `C_OPERATION_TYPES` (`ID_OPERATION_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ACCOUNTS_PAYABLE1` FOREIGN KEY (`FOLIO`) REFERENCES `C_FOLIOS` (`FOLIO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ACCOUNTS_PAYABLE2` FOREIGN KEY (`ID_ACCOUNT_PAYABLE_STATUS`) REFERENCES `C_ACCOUNTS_PAYABLE_STATUS` (`ID_ACCOUNT_PAYABLE_STATUS`) ON DELETE NO ACTION ON UPDATE NO ACTION
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

-- Dump completed on 2016-01-04 18:44:34