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
-- Table structure for table `TRANSACTIONS`
--

DROP TABLE IF EXISTS `TRANSACTIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TRANSACTIONS` (
  `ID_TRANSACTION` int(11) NOT NULL AUTO_INCREMENT,
  `ID_ACCOUNT_PAYABLE` int(11) NOT NULL,
  `ID_BALANCE` int(11) NOT NULL,
  `ID_OPERATION_TYPE` int(11) NOT NULL,
  `ID_TRANSACTION_STATUS` int(11) NOT NULL,
  `AMOUNT` decimal(14,2) NOT NULL DEFAULT '0.00',
  `TRANSACTION_NUMBER` varchar(45) DEFAULT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ID_CURRENCY` int(11) NOT NULL DEFAULT '1',
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_TRANSACTION`),
  KEY `FK_TRANSACTIONS2` (`ID_TRANSACTION_STATUS`),
  KEY `FK_TRANSACTIONS3` (`ID_BALANCE`),
  KEY `FK_TRANSACTIONS1_idx` (`ID_ACCOUNT_PAYABLE`),
  KEY `FK_TRANSACTIONS4_idx` (`ID_OPERATION_TYPE`),
  KEY `FK_TRANSACTIONS5_idx` (`ID_CURRENCY`),
  CONSTRAINT `FK_TRANSACTIONS5` FOREIGN KEY (`ID_CURRENCY`) REFERENCES `C_CURRENCIES` (`ID_CURRENCY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TRANSACTIONS1` FOREIGN KEY (`ID_ACCOUNT_PAYABLE`) REFERENCES `ACCOUNTS_PAYABLE` (`ID_ACCOUNT_PAYABLE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TRANSACTIONS2` FOREIGN KEY (`ID_TRANSACTION_STATUS`) REFERENCES `C_TRANSACTIONS_STATUS` (`ID_TRANSACTION_STATUS`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TRANSACTIONS3` FOREIGN KEY (`ID_BALANCE`) REFERENCES `BALANCES` (`ID_BALANCE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TRANSACTIONS4` FOREIGN KEY (`ID_OPERATION_TYPE`) REFERENCES `C_OPERATION_TYPES` (`ID_OPERATION_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION
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

-- Dump completed on 2016-02-09 17:44:14
