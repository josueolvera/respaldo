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
-- Table structure for table `C_REQUESTS_CATEGORIES`
--

DROP TABLE IF EXISTS `C_REQUESTS_CATEGORIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `C_REQUESTS_CATEGORIES` (
  `ID_REQUEST_CATEGORY` int(11) NOT NULL AUTO_INCREMENT,
  `ID_VIEW` int(11) DEFAULT NULL,
  `CATEGORY` varchar(100) DEFAULT NULL,
  `PERIODIC` int(11) DEFAULT '0',
  `ID_ACCESS_LEVEL` int(11) DEFAULT '1',
  `INFORMATION` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID_REQUEST_CATEGORY`),
  KEY `fk_C_REQUEST_CATEGORIES_C_VIEWS_idx` (`ID_VIEW`),
  CONSTRAINT `FKh3xa347wf85wf0kp0mwx8lu9x` FOREIGN KEY (`ID_VIEW`) REFERENCES `C_VIEWS` (`ID_VIEW`),
  CONSTRAINT `fk_C_REQUEST_CATEGORIES_C_VIEWS` FOREIGN KEY (`ID_VIEW`) REFERENCES `C_VIEWS` (`ID_VIEW`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `C_REQUESTS_CATEGORIES`
--

LOCK TABLES `C_REQUESTS_CATEGORIES` WRITE;
/*!40000 ALTER TABLE `C_REQUESTS_CATEGORIES` DISABLE KEYS */;
INSERT INTO `C_REQUESTS_CATEGORIES` VALUES (1,6,'Cotizable',0,1,'<p>En esta categoria tenemos las siguientes solicitudes:</p><p>Solicitudes de Compra con Cotizaciones.</p><p>Solicitud de Mantenimiento con Cotizaciones</p>'),(2,7,'Directa',0,1,'<p>En esta categoria tenemos las siguientes solicitudes:</p><p>Solicitudes de Compra sin Cotizaciones.</p><p>Solicitud de Mantenimiento sin Cotizaciones</p>'),(3,8,'Periodica',1,1,'<p>En esta categoria tenemos las siguientes solicitudes:</p><p>Solicitudes de Servicio-Agua.</p><p>Solicitudes de Servicio-Luz.</p><p>Solicitudes de Telefonia Fija.</p><p>Solicitudes de Telefonia Movil.</p><p>Solicitudes de Internet.</p><p>Solicitudes de Seguridad-Alarmas</p>');
/*!40000 ALTER TABLE `C_REQUESTS_CATEGORIES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-09 17:43:38
