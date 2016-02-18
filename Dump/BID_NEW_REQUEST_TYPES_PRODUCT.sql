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
  KEY `FKhmnno96bjb50eyc4d8q2yiqbq` (`ID_PRODUCT_TYPE`),
  KEY `FK82u5msrthaqlyemomwvj5ynu6` (`ID_REQUEST_TYPE`),
  CONSTRAINT `FK82u5msrthaqlyemomwvj5ynu6` FOREIGN KEY (`ID_REQUEST_TYPE`) REFERENCES `C_REQUEST_TYPES` (`ID_REQUEST_TYPE`),
  CONSTRAINT `FK9bndkv6rcef2it2u3dm1gkueu` FOREIGN KEY (`ID_REQUEST_CATEGORY`) REFERENCES `C_REQUESTS_CATEGORIES` (`ID_REQUEST_CATEGORY`),
  CONSTRAINT `FKhmnno96bjb50eyc4d8q2yiqbq` FOREIGN KEY (`ID_PRODUCT_TYPE`) REFERENCES `C_PRODUCT_TYPES` (`ID_PRODUCT_TYPE`),
  CONSTRAINT `FK_REQUEST_TYPES_PRODUCT1` FOREIGN KEY (`ID_REQUEST_CATEGORY`) REFERENCES `C_REQUESTS_CATEGORIES` (`ID_REQUEST_CATEGORY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_REQUEST_TYPES_PRODUCT2` FOREIGN KEY (`ID_REQUEST_TYPE`) REFERENCES `C_REQUEST_TYPES` (`ID_REQUEST_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_REQUEST_TYPES_PRODUCT3` FOREIGN KEY (`ID_PRODUCT_TYPE`) REFERENCES `C_PRODUCT_TYPES` (`ID_PRODUCT_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REQUEST_TYPES_PRODUCT`
--

LOCK TABLES `REQUEST_TYPES_PRODUCT` WRITE;
/*!40000 ALTER TABLE `REQUEST_TYPES_PRODUCT` DISABLE KEYS */;
INSERT INTO `REQUEST_TYPES_PRODUCT` VALUES (1,1,1,1,3,1),(3,1,1,2,3,1),(5,1,1,3,0,1),(7,3,3,4,0,1),(8,1,3,4,3,1),(9,3,3,5,0,1),(10,3,2,1,3,1),(11,3,2,6,3,1),(12,3,2,7,0,1),(13,3,2,8,0,1);
/*!40000 ALTER TABLE `REQUEST_TYPES_PRODUCT` ENABLE KEYS */;
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
