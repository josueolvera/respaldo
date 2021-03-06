-- MySQL dump 10.16  Distrib 10.1.13-MariaDB, for Linux (x86_64)
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
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='' */;
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
  KEY `FKmmapkc2jrvcgin7nrhffti30y` (`ID_AREA`),
  KEY `FKk0a3cag117i94v248o445cwnl` (`ID_BUDGET_CATEGORY`),
  KEY `FK7eck3o5s9s50dujrtx9iy4l40` (`ID_GROUP`),
  CONSTRAINT `FK7eck3o5s9s50dujrtx9iy4l40` FOREIGN KEY (`ID_GROUP`) REFERENCES `C_GROUPS` (`ID_GROUP`),
  CONSTRAINT `FK9jyil8tev8jp9ndx8cx2m04mj` FOREIGN KEY (`ID_BUDGET_SUBCATEGORY`) REFERENCES `C_BUDGET_SUBCATEGORIES` (`ID_BUDGET_SUBCATEGORY`),
  CONSTRAINT `FKk0a3cag117i94v248o445cwnl` FOREIGN KEY (`ID_BUDGET_CATEGORY`) REFERENCES `C_BUDGET_CATEGORIES` (`ID_BUDGET_CATEGORY`),
  CONSTRAINT `FKmmapkc2jrvcgin7nrhffti30y` FOREIGN KEY (`ID_AREA`) REFERENCES `C_AREAS` (`ID_AREA`),
  CONSTRAINT `FK_BUDGETS1` FOREIGN KEY (`ID_BUDGET_SUBCATEGORY`) REFERENCES `C_BUDGET_SUBCATEGORIES` (`ID_BUDGET_SUBCATEGORY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGETS4` FOREIGN KEY (`ID_BUDGET_CATEGORY`) REFERENCES `C_BUDGET_CATEGORIES` (`ID_BUDGET_CATEGORY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGETS5` FOREIGN KEY (`ID_GROUP`) REFERENCES `C_GROUPS` (`ID_GROUP`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BUDGETS7` FOREIGN KEY (`ID_AREA`) REFERENCES `C_AREAS` (`ID_AREA`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=226 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-20 18:57:04
