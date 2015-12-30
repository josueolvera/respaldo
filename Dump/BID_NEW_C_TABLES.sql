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
-- Table structure for table `C_TABLES`
--

DROP TABLE IF EXISTS `C_TABLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `C_TABLES` (
  `ID_TABLE` int(11) NOT NULL AUTO_INCREMENT,
  `TABLE_NAME` varchar(100) NOT NULL,
  `URI` varchar(200) DEFAULT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_TABLE`),
  UNIQUE KEY `TABLE_NAME_UNIQUE` (`TABLE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `C_TABLES`
--

LOCK TABLES `C_TABLES` WRITE;
/*!40000 ALTER TABLE `C_TABLES` DISABLE KEYS */;
INSERT INTO `C_TABLES` VALUES (1,'ACCESS_LEVEL',NULL,'2015-11-06 16:39:57'),(2,'ACCESS_LEVELS_ROLE',NULL,'2015-11-06 16:39:57'),(3,'ACCOUNTS',NULL,'2015-11-06 16:39:57'),(4,'ACCOUNTS_PAYABLE',NULL,'2015-11-06 16:39:57'),(5,'ADMON_ACCOUNTS',NULL,'2015-11-06 16:39:57'),(6,'ATTRIBUTES_ARTICLES',NULL,'2015-11-06 16:39:57'),(7,'ATTRIBUTES_TABLES',NULL,'2015-11-06 16:39:57'),(8,'AUTHORIZATIONS',NULL,'2015-11-06 16:39:57'),(9,'BALANCES',NULL,'2015-11-06 16:39:57'),(10,'BUDGETS',NULL,'2015-11-06 16:39:57'),(11,'BUDGET_MONTH',NULL,'2015-11-06 16:39:57'),(12,'BUDGET_PERIOD_MONTHS',NULL,'2015-11-06 16:39:57'),(13,'C_ACCOUNTS_PAYABLE_STATUS',NULL,'2015-11-06 16:39:57'),(14,'C_ACCOUNTS_TYPES',NULL,'2015-11-06 16:39:57'),(15,'C_ARTICLES',NULL,'2015-11-06 16:39:57'),(16,'C_ATTRIBUTES',NULL,'2015-11-06 16:39:57'),(17,'C_AUTHORIZATION_STATUS',NULL,'2015-11-06 16:39:57'),(18,'C_BANKS',NULL,'2015-11-06 16:39:57'),(19,'C_BRANCHS','branchs','2015-11-06 16:39:57'),(20,'C_BUDGET_AREAS',NULL,'2015-11-06 16:39:57'),(21,'C_BUDGET_PERIODS',NULL,'2015-11-06 16:39:57'),(22,'C_BUDGET_TYPES',NULL,'2015-11-06 16:39:57'),(23,'C_COMPONENTS',NULL,'2015-11-06 16:39:57'),(24,'C_DATA_TYPES',NULL,'2015-11-06 16:39:57'),(25,'C_ESTIMATION_STATUS',NULL,'2015-11-06 16:39:57'),(26,'C_FIELDS',NULL,'2015-11-06 16:39:57'),(27,'C_FOLIOS',NULL,'2015-11-06 16:39:57'),(28,'C_MODULES',NULL,'2015-11-06 16:39:57'),(29,'C_MONTHS',NULL,'2015-11-06 16:39:57'),(30,'C_PERIODICITY',NULL,'2015-11-06 16:39:57'),(31,'C_PRODUCTS',NULL,'2015-11-06 16:39:57'),(32,'C_PRODUCT_TYPES',NULL,'2015-11-06 16:39:57'),(33,'C_PROOFS_FILE_TYPES',NULL,'2015-11-06 16:39:57'),(34,'C_PROOF_STATUS',NULL,'2015-11-06 16:39:57'),(35,'PROPERTIES',NULL,'2015-11-06 16:39:57'),(36,'C_REQUESTS_CATEGORIES',NULL,'2015-11-06 16:39:57'),(37,'C_REQUEST_STATUS',NULL,'2015-11-06 16:39:57'),(38,'C_REQUEST_TYPES',NULL,'2015-11-06 16:39:57'),(39,'C_SYSTEMS',NULL,'2015-11-06 16:39:57'),(40,'C_TABLES',NULL,'2015-11-06 16:39:57'),(41,'C_TASKS',NULL,'2015-11-06 16:39:57'),(42,'C_TRANSACTIONS_STATUS',NULL,'2015-11-06 16:39:57'),(43,'C_VIEWS',NULL,'2015-11-06 16:39:57'),(44,'EMPLOYEES_ACCOUNTS',NULL,'2015-11-06 16:39:57'),(45,'PERIODICS_PAYMENTS',NULL,'2015-11-06 16:39:57'),(46,'PRICE_ESTIMATIONS',NULL,'2015-11-06 16:39:57'),(47,'PROOF_FILES',NULL,'2015-11-06 16:39:57'),(48,'PROVIDERS',NULL,'2015-11-06 16:39:57'),(49,'PROVIDERS_ACCOUNTS',NULL,'2015-11-06 16:39:57'),(50,'P_BRANCHS',NULL,'2015-11-06 16:39:57'),(51,'REQUESTS',NULL,'2015-11-06 16:39:57'),(52,'REQUEST_TYPES_PRODUCT',NULL,'2015-11-06 16:39:57'),(53,'STOCKS',NULL,'2015-11-06 16:39:57'),(54,'SYSTEM_ROLES',NULL,'2015-11-06 16:39:58'),(55,'TABLES_FIELD',NULL,'2015-11-06 16:39:58'),(56,'TABLES_FIELDS_ROLE',NULL,'2015-11-06 16:39:58'),(57,'TASKS_ROLE',NULL,'2015-11-06 16:39:58'),(58,'TRANSACTIONS',NULL,'2015-11-06 16:39:58'),(59,'TRANSACTIONS_PROOFS',NULL,'2015-11-06 16:39:58'),(60,'USERS',NULL,'2015-11-06 16:39:58'),(61,'USERS_ROLE',NULL,'2015-11-06 16:39:58'),(62,'VIEWS_COMPONENT',NULL,'2015-11-06 16:39:58'),(63,'VIEWS_COMPONENTS_ROLE',NULL,'2015-11-06 16:39:58'),(64,'VIEWS_ROLE',NULL,'2015-11-06 16:39:58'),(129,'C_DISTRIBUTORS',NULL,'0000-00-00 00:00:00'),(130,'C_AREAS','areas','2015-11-17 20:02:31');
/*!40000 ALTER TABLE `C_TABLES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-30 16:53:23
