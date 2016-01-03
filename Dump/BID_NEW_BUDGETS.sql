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
-- Table structure for table `BUDGETS`
--

DROP TABLE IF EXISTS `BUDGETS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BUDGETS` (
  `ID_BUDGET` int(11) NOT NULL AUTO_INCREMENT,
  `ID_GROUP` int(11) DEFAULT NULL,
  `ID_AREA` int(11) DEFAULT NULL,
  `ID_BUDGET_CATEGORY` int(11) DEFAULT NULL,
  `ID_BUDGET_SUBCATEGORY` int(11) NOT NULL,
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_BUDGET`),
  KEY `FK_BUDGETS1` (`ID_BUDGET_SUBCATEGORY`),
  KEY `FK_BUDGETS4` (`ID_BUDGET_CATEGORY`),
  KEY `FK_BUDGETS7` (`ID_AREA`),
  KEY `FK_BUDGETS5` (`ID_GROUP`),
  CONSTRAINT `FK_BUDGETS1` FOREIGN KEY (`ID_BUDGET_SUBCATEGORY`) REFERENCES `C_BUDGET_SUBCATEGORIES` (`ID_BUDGET_SUBCATEGORY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGETS4` FOREIGN KEY (`ID_BUDGET_CATEGORY`) REFERENCES `C_BUDGET_CATEGORIES` (`ID_BUDGET_CATEGORY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGETS5` FOREIGN KEY (`ID_GROUP`) REFERENCES `C_GROUPS` (`ID_GROUP`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGETS7` FOREIGN KEY (`ID_AREA`) REFERENCES `C_AREAS` (`ID_AREA`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=226 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BUDGETS`
--

LOCK TABLES `BUDGETS` WRITE;
/*!40000 ALTER TABLE `BUDGETS` DISABLE KEYS */;
INSERT INTO `BUDGETS` VALUES (1,1,1,1,5,1),(4,1,1,1,6,1),(5,1,1,2,2,1),(6,1,1,2,1,1),(7,1,1,2,7,1),(8,1,1,3,8,1),(9,1,1,3,9,1),(10,1,1,3,2,1),(11,1,1,4,10,1),(12,1,1,4,11,1),(13,1,1,4,12,1),(14,1,1,4,13,1),(15,1,1,5,26,1),(16,1,1,6,4,1),(17,1,1,6,14,1),(18,1,1,7,15,1),(19,1,1,7,16,1),(20,1,1,8,17,1),(21,1,1,8,18,1),(22,1,1,8,19,1),(23,1,1,9,21,1),(24,1,1,9,23,1),(25,1,1,10,24,1),(26,1,2,1,5,1),(27,1,2,1,6,1),(28,1,2,2,2,1),(29,1,2,2,1,1),(30,1,2,2,7,1),(31,1,2,3,8,1),(32,1,2,3,9,1),(33,1,2,3,2,1),(34,1,2,4,10,1),(35,1,2,4,11,1),(36,1,2,4,12,1),(37,1,2,4,13,1),(38,1,2,5,26,1),(39,1,2,6,3,1),(40,1,2,6,4,1),(41,1,2,6,14,1),(42,1,2,7,15,1),(43,1,2,7,16,1),(44,1,2,8,17,1),(45,1,2,8,18,1),(46,1,2,8,19,1),(47,1,2,9,21,1),(48,1,2,9,23,1),(49,1,2,10,24,1),(50,1,2,10,25,1),(51,1,3,1,5,1),(52,1,3,1,6,1),(53,1,3,2,2,1),(54,1,3,2,1,1),(55,1,3,2,7,1),(56,1,3,3,8,1),(57,1,3,3,9,1),(58,1,3,3,2,1),(59,1,3,4,10,1),(60,1,3,4,11,1),(61,1,3,4,12,1),(62,1,3,4,13,1),(63,1,3,5,26,1),(64,1,3,6,14,1),(65,1,3,7,15,1),(66,1,3,7,16,1),(67,1,3,8,17,1),(68,1,3,8,18,1),(69,1,3,8,19,1),(70,1,3,8,20,1),(71,1,3,9,21,1),(72,1,3,9,23,1),(73,1,3,10,24,1),(74,1,4,1,5,1),(75,1,4,1,6,1),(76,1,4,2,2,1),(77,1,4,2,1,1),(78,1,4,2,7,1),(79,1,4,3,8,1),(80,1,4,3,9,1),(81,1,4,3,2,1),(82,1,4,4,10,1),(83,1,4,4,11,1),(84,1,4,4,12,1),(85,1,4,4,13,1),(86,1,4,5,26,1),(87,1,4,6,14,1),(88,1,4,7,15,1),(89,1,4,7,16,1),(90,1,4,8,17,1),(91,1,4,8,18,1),(92,1,4,8,19,1),(93,1,4,9,21,1),(94,1,4,9,23,1),(95,1,4,10,24,1),(96,1,5,1,5,1),(97,1,5,1,6,1),(98,1,5,2,2,1),(99,1,5,2,1,1),(100,1,5,2,7,1),(101,1,5,3,8,1),(102,1,5,3,9,1),(103,1,5,3,2,1),(104,1,5,4,10,1),(105,1,5,4,11,1),(106,1,5,4,12,1),(107,1,5,4,13,1),(108,1,5,5,26,1),(109,1,5,6,14,1),(110,1,5,7,15,1),(111,1,5,8,17,1),(112,1,5,8,18,1),(113,1,5,8,19,1),(114,1,5,9,21,1),(115,1,5,9,22,1),(116,1,5,9,23,1),(117,1,5,10,24,1),(118,1,6,1,5,1),(119,1,6,1,6,1),(120,1,6,2,2,1),(121,1,6,2,1,1),(122,1,6,2,7,1),(123,1,6,3,8,1),(124,1,6,3,9,1),(125,1,6,3,2,1),(126,1,6,4,10,1),(127,1,6,4,11,1),(128,1,6,4,12,1),(129,1,6,4,13,1),(130,1,6,5,26,1),(131,1,6,6,14,1),(132,1,6,7,15,1),(133,1,6,8,17,1),(134,1,6,8,18,1),(135,1,6,8,19,1),(136,1,6,8,20,1),(137,1,6,9,21,1),(138,1,6,9,23,1),(139,1,6,10,24,1),(140,1,7,1,5,1),(141,1,7,1,6,1),(142,1,7,2,2,1),(143,1,7,2,1,1),(144,1,7,2,7,1),(145,1,7,3,8,1),(146,1,7,3,9,1),(147,1,7,3,2,1),(148,1,7,4,10,1),(149,1,7,4,11,1),(150,1,7,4,12,1),(151,1,7,4,13,1),(152,1,7,5,26,1),(153,1,7,6,14,1),(154,1,7,7,15,1),(155,1,7,8,17,1),(156,1,7,8,18,1),(157,1,7,8,19,1),(158,1,7,9,21,1),(159,1,7,9,23,1),(160,1,7,10,24,1),(161,1,8,1,5,1),(162,1,8,1,6,1),(163,1,8,2,2,1),(164,1,8,2,1,1),(165,1,8,2,7,1),(166,1,8,3,8,1),(167,1,8,3,9,1),(168,1,8,3,2,1),(169,1,8,4,10,1),(170,1,8,4,11,1),(171,1,8,4,12,1),(172,1,8,4,13,1),(173,1,8,5,26,1),(174,1,8,6,14,1),(175,1,8,7,15,1),(176,1,8,8,17,1),(177,1,8,8,18,1),(178,1,8,8,19,1),(179,1,8,9,21,1),(180,1,8,9,23,1),(181,1,8,10,24,1),(182,1,9,1,5,1),(183,1,9,1,6,1),(184,1,9,2,2,1),(185,1,9,2,1,1),(186,1,9,2,7,1),(187,1,9,3,8,1),(188,1,9,3,9,1),(189,1,9,3,2,1),(190,1,9,4,10,1),(191,1,9,4,11,1),(192,1,9,4,12,1),(193,1,9,4,13,1),(194,1,9,5,26,1),(195,1,9,6,14,1),(196,1,9,7,15,1),(197,1,9,8,17,1),(198,1,9,8,18,1),(199,1,9,8,19,1),(200,1,9,9,21,1),(201,1,9,9,23,1),(202,1,9,10,24,1),(203,1,10,1,5,1),(204,1,10,1,6,1),(205,1,10,2,2,1),(206,1,10,2,1,1),(207,1,10,2,7,1),(208,1,10,3,8,1),(209,1,10,3,9,1),(210,1,10,3,2,1),(211,1,10,4,10,1),(212,1,10,4,11,1),(213,1,10,4,12,1),(214,1,10,4,13,1),(215,1,10,5,26,1),(216,1,10,6,14,1),(217,1,10,7,15,1),(218,1,10,7,16,1),(219,1,10,8,17,1),(220,1,10,8,18,1),(221,1,10,8,19,1),(222,1,10,8,20,1),(223,1,10,9,21,1),(224,1,10,9,23,1),(225,1,10,10,24,1);
/*!40000 ALTER TABLE `BUDGETS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-30 16:53:26
