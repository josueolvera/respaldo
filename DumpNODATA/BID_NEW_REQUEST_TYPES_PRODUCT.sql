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
-- Table structure for table `REQUEST_TYPES_PRODUCT`
--

DROP TABLE IF EXISTS `REQUEST_TYPES_PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REQUEST_TYPES_PRODUCT` (
  `ID_REQUEST_TYPE_PRODUCT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_REQUEST_CATEGORY` int(11) NOT NULL,
  `ID_REQUEST_TYPE` int(11) NOT NULL,
  `ID_PRODUCT_TYPE` int(11) NOT NULL,
  `ESTIMATIONS_QUANTITY` int(11) NOT NULL DEFAULT '1',
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_REQUEST_TYPE_PRODUCT`),
  KEY `FK_REQUEST_TYPES_PRODUCT1_idx` (`ID_REQUEST_CATEGORY`),
  KEY `FK_REQUEST_TYPES_PRODUCT2` (`ID_REQUEST_TYPE`),
  KEY `FK_REQUEST_TYPES_PRODUCT3` (`ID_PRODUCT_TYPE`),
  CONSTRAINT `FK_REQUEST_TYPES_PRODUCT1` FOREIGN KEY (`ID_REQUEST_CATEGORY`) REFERENCES `C_REQUESTS_CATEGORIES` (`ID_REQUEST_CATEGORY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_REQUEST_TYPES_PRODUCT2` FOREIGN KEY (`ID_REQUEST_TYPE`) REFERENCES `C_REQUEST_TYPES` (`ID_REQUEST_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_REQUEST_TYPES_PRODUCT3` FOREIGN KEY (`ID_PRODUCT_TYPE`) REFERENCES `C_PRODUCT_TYPES` (`ID_PRODUCT_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-30 16:53:48
