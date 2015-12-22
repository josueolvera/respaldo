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
-- Table structure for table `C_TASKS`
--

DROP TABLE IF EXISTS `C_TASKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `C_TASKS` (
  `ID_TASK` int(11) NOT NULL AUTO_INCREMENT,
  `TASK_NAME` varchar(100) NOT NULL,
  `HTTP_METHOD` varchar(10) DEFAULT 'GET',
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_TASK`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `C_TASKS`
--

LOCK TABLES `C_TASKS` WRITE;
/*!40000 ALTER TABLE `C_TASKS` DISABLE KEYS */;
INSERT INTO `C_TASKS` VALUES (1,'prueba/build-permissions','GET','2015-11-04 22:54:51'),(2,'prueba','GET','2015-11-13 22:50:54'),(4,'budget-categories','GET','2015-11-17 06:00:00'),(5,'budget-periods','GET','2015-11-18 00:51:28'),(6,'budget-types','GET','2015-11-18 00:51:28'),(8,'prueba/hashmap','GET','2015-11-18 23:27:36'),(10,'admon/budget','GET','2015-11-19 18:10:38'),(12,'request-types','GET','2015-11-20 17:01:29'),(13,'request-categories','GET','2015-11-20 17:01:53'),(14,'product-types','GET','2015-11-20 17:02:14'),(15,'branchs','GET','2015-11-20 23:46:18'),(16,'areas','GET','2015-11-20 23:46:18'),(17,'budgets','POST','2015-11-23 15:31:13'),(18,'budget-subcategories','GET','2015-11-23 23:21:16'),(19,'groups','GET','2015-11-25 19:00:20'),(20,'distributors','GET','2015-11-25 19:00:20'),(21,'request-types/#','GET','2015-11-27 16:12:27'),(22,'budgets/#/#/#/#','GET','2015-11-27 16:46:41'),(23,'prueba/#/#','GET','2015-11-27 17:59:34'),(24,'views','GET','2015-11-27 23:35:08'),(25,'views/#','GET','2015-11-27 23:35:25'),(26,'groups/#','GET','2015-11-28 00:16:04'),(27,'siad/budgets','GET','2015-11-30 15:54:39'),(28,'siad/pendign-requests','GET','2015-11-30 16:55:32'),(29,'siad/edit-request','GET','2015-11-30 17:03:20'),(30,'app-menu','GET','2015-12-01 16:21:33'),(31,'agenda','GET','2015-12-02 00:35:38'),(32,'budgets/#/#','GET','2015-12-02 23:37:47'),(33,'budget-month-branch','GET','2015-12-03 18:54:55'),(34,'months','GET','2015-12-03 19:00:44'),(35,'dw-enterprises/#/#','GET','2015-12-03 23:17:30'),(36,'folios','GET','2015-12-04 18:45:23'),(37,'folios/authorizations','GET','2015-12-04 23:36:28'),(38,'folios/authorizations','POST','2015-12-07 18:29:30'),(39,'folios/authorizations/#/authorize','POST','2015-12-07 19:12:56'),(40,'folios/authorizations/#/reject','POST','2015-12-07 19:12:56'),(41,'folios/authorizations/#/forward','POST','2015-12-07 19:15:44'),(42,'stock','GET','2015-12-09 16:29:07'),(43,'admon/budgets','GET','2015-12-09 23:14:35'),(44,'siad/budgets','GET','2015-12-09 23:17:56'),(45,'siad/stock','GET','2015-12-10 22:02:33'),(46,'request-type-product/#/#/#','GET','2015-12-11 18:38:47'),(47,'stock/#','GET','2015-12-11 19:27:46'),(48,'budget-month-branch/request','GET','2015-12-16 18:36:18'),(49,'providers','GET','2015-12-17 19:50:49'),(50,'providers-accounts/provider/#','GET','2015-12-21 18:25:13');
/*!40000 ALTER TABLE `C_TASKS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-21 18:41:21
