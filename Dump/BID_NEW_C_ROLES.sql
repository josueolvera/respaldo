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
-- Table structure for table `C_ROLES`
--

DROP TABLE IF EXISTS `C_ROLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `C_ROLES` (
  `ID_ROLE` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_ROLE`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `C_ROLES`
--

LOCK TABLES `C_ROLES` WRITE;
/*!40000 ALTER TABLE `C_ROLES` DISABLE KEYS */;
INSERT INTO `C_ROLES` VALUES (1,'GERENTE DE DISTRIBUCION'),(2,'SUBGERENTE'),(3,'GERENTE REGIONAL'),(4,'GERENTE DE SUCURSAL'),(5,'SUPERVISOR'),(6,'AUXILIAR'),(7,'PROMOTOR'),(8,'LIMPIEZA'),(9,'INVITADO'),(10,'DIRECTOR GENERAL'),(11,'SUBDIRECTOR'),(12,'ASISTENTE'),(13,'PROJECT MANAGEMENT'),(14,'GERENTE DE PROYECTOS'),(15,'DISEÑADOR'),(16,'GERENTE DE ADMINISTRACION'),(17,'COORDINADOR DE AUXILIARES ADMINISTRATIVOS'),(18,'TESORERIA Y COORDINADOR DE FLUJO DE INFORMACION'),(19,'AUXILIAR ADMINISTRATIVO'),(20,'CAPTURISTA'),(21,'RECEPCIONISTA'),(22,'MENSAJERIA'),(23,'INTENDENCIA'),(24,'GERENTE  DE RECURSOS HUMANOS'),(25,'COORDINADOR DE NOMINAS'),(26,'EJECUTIVO DE NOMINAS'),(27,'EJECUTIVO RECLUTAMIENTO'),(28,'EJECUTIVO RECLUTAMIENTO FORANEO'),(29,'GERENTE DE SISTEMAS'),(30,'LIDER DE PROYECTO'),(31,'DESARROLLADOR JAVA JR'),(32,'DESARROLLADOR DE BASE DE DATOS JR'),(33,'SOPORTE TECNICO'),(34,'GERENTE DE DISTRIBUCION'),(35,'COORDINADOR DE APERTURAS'),(36,'COORDINADOR DE MESA DE CONTROL'),(37,'GERENTE DE AUDITORIA'),(38,'EJECUTIVO DE AUDITORIA'),(39,'GERENTE JURIDICO'),(40,'ABOGADO'),(41,'GERENTE DE FINANZAS'),(42,'CONSULTOR DE MERCADO'),(43,'INTELIGENCIA DE MERCADO'),(44,'GERENTE DE OPERACIONES'),(45,'GESTOR DE DATOS'),(46,'EJECUTIVO DE CUENTA');
/*!40000 ALTER TABLE `C_ROLES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-29 12:38:10
