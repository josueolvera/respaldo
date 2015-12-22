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
-- Table structure for table `C_BRANCHS`
--

DROP TABLE IF EXISTS `C_BRANCHS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `C_BRANCHS` (
  `ID_BRANCH` int(11) NOT NULL AUTO_INCREMENT,
  `BRANCH_NAME` varchar(50) DEFAULT NULL,
  `BRANCH_SHORT` varchar(30) NOT NULL,
  `LOCATION` varchar(50) DEFAULT NULL,
  `ADDRESS` varchar(400) DEFAULT NULL,
  `UPLOADED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LOW_DATE` timestamp NULL DEFAULT NULL,
  `STATUS` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_BRANCH`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `C_BRANCHS`
--

LOCK TABLES `C_BRANCHS` WRITE;
/*!40000 ALTER TABLE `C_BRANCHS` DISABLE KEYS */;
INSERT INTO `C_BRANCHS` VALUES (1,'CULIACAN, SIN – CULIACAN SUR','CULIACAN','CULIACAN, SIN – CULIACAN SUR',NULL,'2015-11-17 19:53:58',NULL,1),(2,'NOGALES, SON - NOGALES','NOGALES','NOGALES, SON',NULL,'2015-11-17 19:53:58',NULL,1),(3,'GUAYMAS, SON - GUAYMAS','GUAYMAS','GUAYMAS, SON',NULL,'2015-11-17 19:53:58',NULL,1),(4,'TIJUANA, BCN - TIJUANA ORDAZ','TIJUANA','TIJUANA, BCN',NULL,'2015-11-17 19:53:58',NULL,1),(5,'OBREGÓN, SON - OBREGÓN','OBREGON','OBREGÓN, SON',NULL,'2015-11-17 19:53:58',NULL,1),(6,'HERMOSILLO, SON - HERMOSILLO','HERMOSILLO','HERMOSILLO, SON',NULL,'2015-11-17 19:53:58',NULL,1),(7,'LA PAZ, BCS - LA PAZ','LA PAZ','LA PAZ, BCS',NULL,'2015-11-17 19:53:58',NULL,1),(8,'LOS MOCHIS, SIN – LOS MOCHIS ZARAGOZA','LOS MOCHIS','LOS MOCHIS, SIN – LOS MOCHIS ZARAGOZA',NULL,'2015-11-17 19:53:58',NULL,1),(9,'MAZATLAN, SIN – MAZATLAN BENITO','MAZATLAN','MAZATLAN, SIN – MAZATLAN BENITO',NULL,'2015-11-17 19:53:58',NULL,1),(10,'MEXICALI, BCN – CASTELLANO','MEXICALI','MEXICALI, BCN – CASTELLANO',NULL,'2015-11-17 19:53:59',NULL,1),(11,'CANCÚN, QROO - MIGUEL HIDALGO','CANCUN','CANCÚN, QROO',NULL,'2015-11-17 19:53:59',NULL,1),(12,'TUXTLA GUTIÉRREZ, CHS - PONIENTE','TUXTLA','TUXTLA GUTIÉRREZ, CHS',NULL,'2015-11-17 19:53:59',NULL,1),(13,'CD. DEL CARMEN, CAMP - BUROCRATAS SCCP','SCCP CD CARMEN','CD. DEL CARMEN, CAMP',NULL,'2015-11-17 19:53:59',NULL,1),(14,'VILLA HERMOSA, TAB - MAGALLANES SCCP','SCCP VILLAHERMOSA','VILLA HERMOSA, TAB',NULL,'2015-11-17 19:53:59',NULL,1),(15,'CHETUMAL, QROO - CHETUMAL','CHETUMAL','CHETUMAL, QROO',NULL,'2015-11-17 19:53:59',NULL,1),(16,'VILLA HERMOSA, TAB - VILLA HERMOSA','VILLAHERMOSA','VILLA HERMOSA, TAB',NULL,'2015-11-17 19:53:59',NULL,1),(17,'COATZACOALCOS, VER - COATZACOALCOS SCCP','SCCP COATZACOALCOS','COATZACOALCOS, VER',NULL,'2015-11-17 19:54:01',NULL,1),(18,'TABASCO, TAB – CARDENAS','TABASCO','TABASCO, TAB – CARDENAS',NULL,'2015-11-17 19:54:01',NULL,1),(19,'TAPACHULA, CHS - TAPACHULA','TAPACHULA','TAPACHULA, CHS',NULL,'2015-11-17 19:54:01',NULL,1),(20,'TEXCOCO, EDOMEX - TEXCOCO JUAREZ','TEXCOCO','TEXCOCO, EDOMEX',NULL,'2015-11-17 19:54:01',NULL,1),(21,'TEZIUTLAN, PUE - TEZIUTLAN CENTRO','TEZIUTLAN','TEZIUTLAN, PUE',NULL,'2015-11-17 19:54:01',NULL,1),(22,'CD. DE MEXICO, D.F. - ANAHUAC SCCP','SCCP DF','CD. DE MEXICO, D.F.',NULL,'2015-11-17 19:54:01',NULL,1),(23,'GUADALAJARA, JAL - GUADALAJARA CENTRO','GUADALAJARA','GUADALAJARA, JAL',NULL,'2015-11-17 19:54:01',NULL,1),(24,'TLAXCALA, TLA – TLAXCALA CENTRO','TLAXCALA','TLAXCALA, TLA – TLAXCALA CENTRO',NULL,'2015-11-17 19:54:01',NULL,1),(25,'CD. DE MÉXICO, D.F. – MIGUEL HIDALGO','MODULO HXTI','CD. DE MÉXICO, D.F. – MIGUEL HIDALGO',NULL,'2015-11-17 19:54:01',NULL,1),(26,'PUEBLA, PUE – PUEBLA CENTRO','PUEBLA','PUEBLA, PUE – PUEBLA CENTRO',NULL,'2015-11-17 19:54:01',NULL,1),(27,'SALAMANCA, GTO - SALAMANCA SCCP','SCCP SALAMANCA','SALAMANCA, GTO',NULL,'2015-11-17 19:54:01',NULL,1),(28,'CD MADERO, TAM - CD. MADERO SCCP','SCCP MADERO','CD MADERO, TAM',NULL,'2015-11-17 19:54:01',NULL,1),(29,'POZA RICA, VER - NORTE SCCP','SCCP POZA RICA','POZA RICA, VER',NULL,'2015-11-17 19:54:01',NULL,1),(30,'VERACRUZ, VER - VERACRUZ SCCP','SCCP VERACRUZ','VERACRUZ, VER',NULL,'2015-11-17 19:54:01',NULL,1),(31,'CD VICTORIA, TAM - CD VICTORIA CENTRO','CIUDAD VICTORIA','CD VICTORIA, TAM',NULL,'2015-11-17 19:54:01',NULL,1),(32,'REYNOSA, TAM - REYNOSA SCCP','SCCP REYNOSA','REYNOSA, TAM',NULL,'2015-11-17 19:54:01',NULL,1),(33,'INSURGENTES NORTE, DF – CI INSURGENTES N','CI INSURGENTES','INSURGENTES NORTE, DF – CI INSURGENTES N',NULL,'2015-11-17 19:54:01',NULL,1),(34,'QUERETARO, QRO - CI QUERETARO','CI QUERETARO','QUERETARO, QRO',NULL,'2015-11-17 19:54:01',NULL,1),(35,'IZTACALCO, DF – CI IZTACALCO','CI IZTACALCO','IZTACALCO, DF – CI IZTACALCO',NULL,'2015-11-17 19:54:01',NULL,1),(36,'TLALPAN, DF- CI LAS TORRES','CI LAS TORRES','TLALPAN, DF',NULL,'2015-11-17 19:54:01',NULL,1),(37,'CUAUHTEMOC, DF - CI DF CUAUHTEMOC','CI CUAUHTEMOC','CUAUHTEMOC, DF',NULL,'2015-11-17 19:54:02',NULL,1),(38,'TLALNEPANTLA , EDOMEX – CI TLALNEPANTLA','CI TLALNEPANTLA','TLALNEPANTLA , EDOMEX – CI TLALNEPANTLA',NULL,'2015-11-17 19:54:02',NULL,1),(39,'CUATITLAN, EDOMEX - CI CUATITLAN','CI CUAUTITLAN','CUATITLAN, EDOMEX',NULL,'2015-11-17 19:54:02',NULL,1),(40,'PACHUCA, HGO - CI PACHUCA','CI PACHUCA','PACHUCA, HGO',NULL,'2015-11-17 19:54:02',NULL,1),(41,'TOLUCA , EDOMEX – CI TOLUCA','CI TOLUCA','TOLUCA , EDOMEX – CI TOLUCA',NULL,'2015-11-17 19:54:02',NULL,1),(42,'ECATEPEC, EDOMEX – CI ECATEPEC','CI ECATEPEC','ECATEPEC, EDOMEX – CI ECATEPEC',NULL,'2015-11-17 19:54:02',NULL,1),(43,'PUEBLA, PUE – CI PUEBLA','CI PUEBLA','PUEBLA, PUE – CI PUEBLA',NULL,'2015-11-17 19:54:02',NULL,1),(44,'ACAPULCO, GRO - CI ACAPULCO','CI ACAPULCO','ACAPULCO, GRO',NULL,'2015-11-17 19:54:02',NULL,1),(45,'CAMPECHE, CAM - CI CAMPECHE','CI CAMPECHE','CAMPECHE, CAM',NULL,'2015-11-17 19:54:02',NULL,1),(46,'CD. DEL CARMEN, CAMP - CI CD. DEL CARMEN','CI CD CARMEN','CD. DEL CARMEN, CAMP',NULL,'2015-11-17 19:54:02',NULL,1),(47,'CORDOBA, VER - CI CORDOBA','CI CORDOBA','CORDOBA, VER',NULL,'2015-11-17 19:54:02',NULL,1),(48,'COATZACOALCOS, VER – CI COATZACOALCOS','CI COATZACOALCOS','COATZACOALCOS, VER – CI COATZACOALCOS',NULL,'2015-11-17 19:54:02',NULL,1),(49,'CUERNAVACA, MOR - CI CUERNAVACA','CI CUERNAVACA','CUERNAVACA, MOR',NULL,'2015-11-17 19:54:02',NULL,1),(50,'VERACRUZ,  VER – CI VERACRUZ','CI VERACRUZ','VERACRUZ,  VER – CI VERACRUZ',NULL,'2015-11-17 19:54:03',NULL,1),(51,'VILLA HERMOSA, TAB - CI VILLA HERMOSA','CI VILLAHERMOSA','VILLA HERMOSA, TAB',NULL,'2015-11-17 19:54:03',NULL,1),(52,'XALAPA, VER - CI XALAPA','CI XALAPA','XALAPA, VER',NULL,'2015-11-17 19:54:03',NULL,1),(53,'REYNOSA, TAM - CI REYNOSA','CI REYNOSA','REYNOSA, TAM',NULL,'2015-11-17 19:54:03',NULL,1),(54,'SALTILLO, COAH - CI SALTILLO','CI SALTILLO','SALTILLO, COAH',NULL,'2015-11-17 19:54:03',NULL,1),(55,'SAN LUIS POTOSI, SLP - CI SAN LUIS POTOS','CI SAN LUIS','SAN LUIS POTOSI, SLP',NULL,'2015-11-17 19:54:03',NULL,1),(56,'MONCLOVA, COAH - CI MONCLOVA','CI MONCLOVA','MONCLOVA, COAH',NULL,'2015-11-17 19:54:03',NULL,1),(57,'MADERO, TAM - CI MADERO','CI MADERO','MADERO, TAM',NULL,'2015-11-17 19:54:03',NULL,1),(58,'MONTERREY, NL - CI MONTERREY','CI MONTERREY','MONTERREY, NL',NULL,'2015-11-17 19:54:03',NULL,1),(59,'NUEVO LEÓN, NL - CI NUEVO LEÓN','CI SAN NICOLAS','NUEVO LEÓN, NL',NULL,'2015-11-17 19:54:03',NULL,1),(60,'TORREON, COAH - CI TORREON','CI TORREON','TORREON, COAH',NULL,'2015-11-17 19:54:03',NULL,1),(61,'ZACATECAS, ZAC - CI ZACATECAS','CI ZACATECAS','ZACATECAS, ZAC',NULL,'2015-11-17 19:54:03',NULL,1),(62,'SIN SAP','CI TLAQUEPAQUE','SIN SAP',NULL,'2015-11-17 19:54:03',NULL,1),(63,'SIN SAPP','CI URUAPAN','SIN SAPP',NULL,'2015-11-17 19:54:03',NULL,1),(64,'CELAYA, GTO – CI CELAYA','CI CELAYA','CELAYA, GTO – CI CELAYA',NULL,'2015-11-17 19:54:03',NULL,1),(65,'MORELIA, MICH - CI MORELIA','CI MORELIA','MORELIA, MICH',NULL,'2015-11-17 19:54:03',NULL,1),(66,'TEPIC, NAY - CI TEPIC','CI TEPIC','TEPIC, NAY',NULL,'2015-11-17 19:54:03',NULL,1),(67,'COLIMA, COL - CI COLIMA','CI COLIMA','COLIMA, COL',NULL,'2015-11-17 19:54:03',NULL,1),(68,'GUADALAJARA, JAL – CI GUADALAJARA','CI GUADALAJARA','GUADALAJARA, JAL – CI GUADALAJARA',NULL,'2015-11-17 19:54:03',NULL,1),(69,'GUANAJUATO, GTO – CI GUANAJUATO','CI GUANAJUATO','GUANAJUATO, GTO – CI GUANAJUATO',NULL,'2015-11-17 19:54:03',NULL,1),(70,'ZAPOPAN, JAL – CI ZAPOPAN','CI ZAPOPAN','ZAPOPAN, JAL – CI ZAPOPAN',NULL,'2015-11-17 19:54:03',NULL,1),(71,'IRAPUATO, GTO – CI IRAPUATO','CI IRAPUATO','IRAPUATO, GTO – CI IRAPUATO',NULL,'2015-11-17 19:54:03',NULL,1),(72,'AGUASCALIENTES, AGS - CI AGUASCALIENTES','CI AGUASCALIENTES','AGUASCALIENTES, AGS',NULL,'2015-11-17 19:54:03',NULL,1),(73,'MAZATLAN, SIN - CI MAZATLAN','CI MAZATLAN','MAZATLAN, SIN',NULL,'2015-11-17 19:54:03',NULL,1),(74,'CD JUAREZ, CHIH – CI CD. JUAREZ','CI CD JUAREZ','CD JUAREZ, CHIH – CI CD. JUAREZ',NULL,'2015-11-17 19:54:03',NULL,1),(75,'CHIHUAHUA, CHI - CI CHIHUAHUA','CI CHIHUAHUA','CHIHUAHUA, CHI',NULL,'2015-11-17 19:54:03',NULL,1),(76,'CULIACAN, SIN - CI CULIACAN','CI CULIACAN','CULIACAN, SIN',NULL,'2015-11-17 19:54:03',NULL,1),(77,'DURANGO, DGO - CI DURANGO','CI DURANGO','DURANGO, DGO',NULL,'2015-11-17 19:54:03',NULL,1),(78,'GOMEZ PALACIO, DGO - CI GOMEZ PALACIO','CI GOMEZ PALACIO','GOMEZ PALACIO, DGO',NULL,'2015-11-17 19:54:03',NULL,1),(79,'CORPORATIVO','CORPORATIVO','CORPORATIVO- TORRE VIRREYES',NULL,'2015-12-04 18:05:55',NULL,1),(80,'SIERRA MOJADA','SIERRA MOJADA','SIERRA MOJADA',NULL,'2015-12-11 18:04:24',NULL,1);
/*!40000 ALTER TABLE `C_BRANCHS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-21 18:41:22
