CREATE DATABASE  IF NOT EXISTS `controlSystem` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `controlSystem`;
-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: controlSystem
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ASIGNACION_UNIDAD`
--

DROP TABLE IF EXISTS `ASIGNACION_UNIDAD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ASIGNACION_UNIDAD` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_fin` datetime DEFAULT NULL,
  `fecha_inicio` datetime DEFAULT NULL,
  `km_final` float DEFAULT NULL,
  `km_inicio` float DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ASIGNACION_UNIDAD`
--

LOCK TABLES `ASIGNACION_UNIDAD` WRITE;
/*!40000 ALTER TABLE `ASIGNACION_UNIDAD` DISABLE KEYS */;
/*!40000 ALTER TABLE `ASIGNACION_UNIDAD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CONTACTO_EMERGENCIA`
--

DROP TABLE IF EXISTS `CONTACTO_EMERGENCIA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONTACTO_EMERGENCIA` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ape_materno` varchar(255) DEFAULT NULL,
  `ape_paterno` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `parentesco` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CONTACTO_EMERGENCIA`
--

LOCK TABLES `CONTACTO_EMERGENCIA` WRITE;
/*!40000 ALTER TABLE `CONTACTO_EMERGENCIA` DISABLE KEYS */;
INSERT INTO `CONTACTO_EMERGENCIA` VALUES (1,'asdfdsf','sdf','asdf','Amigo','21212121'),(2,'afsd','sadf','sdf',' ',''),(3,'sosa','ortiz','rafaela','Madre','7541221');
/*!40000 ALTER TABLE `CONTACTO_EMERGENCIA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FLETE`
--

DROP TABLE IF EXISTS `FLETE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FLETE` (
  `id` int NOT NULL AUTO_INCREMENT,
  `concepto` varchar(255) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `lugar_salida` varchar(255) DEFAULT NULL,
  `recibe` varchar(255) DEFAULT NULL,
  `responsable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FLETE`
--

LOCK TABLES `FLETE` WRITE;
/*!40000 ALTER TABLE `FLETE` DISABLE KEYS */;
/*!40000 ALTER TABLE `FLETE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NOMINA`
--

DROP TABLE IF EXISTS `NOMINA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `NOMINA` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descuentos` float DEFAULT NULL,
  `dias_laborados` int DEFAULT NULL,
  `fecha_baja` datetime DEFAULT NULL,
  `fecha_inicio` datetime DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `periodo` varchar(255) DEFAULT NULL,
  `prestamos` float DEFAULT NULL,
  `sueldo_diario` float DEFAULT NULL,
  `sueldo_neto` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NOMINA`
--

LOCK TABLES `NOMINA` WRITE;
/*!40000 ALTER TABLE `NOMINA` DISABLE KEYS */;
/*!40000 ALTER TABLE `NOMINA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OPERADOR`
--

DROP TABLE IF EXISTS `OPERADOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OPERADOR` (
  `operador_id` int NOT NULL,
  `alergias` varchar(255) DEFAULT NULL,
  `ape_materno` varchar(255) DEFAULT NULL,
  `ape_paterno` varchar(255) DEFAULT NULL,
  `calle` varchar(255) DEFAULT NULL,
  `ciudad` varchar(255) DEFAULT NULL,
  `colonia` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `file` longblob,
  `nombre` varchar(255) DEFAULT NULL,
  `num` varchar(255) DEFAULT NULL,
  `puesto` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `telefono2` varchar(255) DEFAULT NULL,
  `typeblood` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`operador_id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OPERADOR`
--

LOCK TABLES `OPERADOR` WRITE;
/*!40000 ALTER TABLE `OPERADOR` DISABLE KEYS */;
INSERT INTO `OPERADOR` VALUES (2,'','maria','asd','sadf','','','',NULL,'maria','','44545','454545','','A-'),(51,'das','sosa','torres','fernado','','545','',NULL,'maria','','maquinaria pesado','57555555','75544','AB+');
/*!40000 ALTER TABLE `OPERADOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OPERADOR_EMERGENCIA`
--

DROP TABLE IF EXISTS `OPERADOR_EMERGENCIA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OPERADOR_EMERGENCIA` (
  `operador_id` int NOT NULL,
  `contactoEmergencia_id` int NOT NULL,
  PRIMARY KEY (`operador_id`,`contactoEmergencia_id`),
  KEY `FK_OPERADOR_EMERGENCIA_contactoEmergencia_id` (`contactoEmergencia_id`),
  CONSTRAINT `FK_OPERADOR_EMERGENCIA_contactoEmergencia_id` FOREIGN KEY (`contactoEmergencia_id`) REFERENCES `CONTACTO_EMERGENCIA` (`id`),
  CONSTRAINT `FK_OPERADOR_EMERGENCIA_operador_id` FOREIGN KEY (`operador_id`) REFERENCES `OPERADOR` (`operador_id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OPERADOR_EMERGENCIA`
--

LOCK TABLES `OPERADOR_EMERGENCIA` WRITE;
/*!40000 ALTER TABLE `OPERADOR_EMERGENCIA` DISABLE KEYS */;
INSERT INTO `OPERADOR_EMERGENCIA` VALUES (2,2),(51,3);
/*!40000 ALTER TABLE `OPERADOR_EMERGENCIA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RECARGA_COMBUSTIBLE`
--

DROP TABLE IF EXISTS `RECARGA_COMBUSTIBLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RECARGA_COMBUSTIBLE` (
  `id` int NOT NULL AUTO_INCREMENT,
  `litros` float DEFAULT NULL,
  `monto` varchar(255) DEFAULT NULL,
  `odometro_actual` float DEFAULT NULL,
  `precioxlitro` float DEFAULT NULL,
  `tipo_combustible` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RECARGA_COMBUSTIBLE`
--

LOCK TABLES `RECARGA_COMBUSTIBLE` WRITE;
/*!40000 ALTER TABLE `RECARGA_COMBUSTIBLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RECARGA_COMBUSTIBLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE`
--

DROP TABLE IF EXISTS `SEQUENCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE`
--

LOCK TABLES `SEQUENCE` WRITE;
/*!40000 ALTER TABLE `SEQUENCE` DISABLE KEYS */;
INSERT INTO `SEQUENCE` VALUES ('SEQ_GEN',100);
/*!40000 ALTER TABLE `SEQUENCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SERVICIO`
--

DROP TABLE IF EXISTS `SERVICIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SERVICIO` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad` float DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `empresa` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `importe` float DEFAULT NULL,
  `kilometraje` varchar(255) DEFAULT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL,
  `observaciones` longtext,
  `precio` float DEFAULT NULL,
  `proximo_servicio` date DEFAULT NULL,
  `vehiculo_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_SERVICIO_vehiculo_id` (`vehiculo_id`),
  CONSTRAINT `FK_SERVICIO_vehiculo_id` FOREIGN KEY (`vehiculo_id`) REFERENCES `VEHICULO` (`vehiculo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SERVICIO`
--

LOCK TABLES `SERVICIO` WRITE;
/*!40000 ALTER TABLE `SERVICIO` DISABLE KEYS */;
INSERT INTO `SERVICIO` VALUES (5,1,'sad','asd','2023-07-21',12121,'288880.0','TRANSFERENCIA/DEPOSITO','asd',12121,NULL,5);
/*!40000 ALTER TABLE `SERVICIO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ape_materno` varchar(255) DEFAULT NULL,
  `ape_paterno` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` longtext,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_nick` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=2;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (1,NULL,NULL,'admin','maria','d033e22ae348aeb5660fc2140aec35850c4da997','Admin');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VEHICULO`
--

DROP TABLE IF EXISTS `VEHICULO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `VEHICULO` (
  `vehiculo_id` int NOT NULL AUTO_INCREMENT,
  `descripcion` longtext,
  `km_actual` float DEFAULT NULL,
  `marca` varchar(255) DEFAULT NULL,
  `modelo` varchar(255) DEFAULT NULL,
  `num_serie` varchar(255) DEFAULT NULL,
  `tipo_combustible` varchar(255) DEFAULT NULL,
  `capacidad` float DEFAULT NULL,
  `fin_renta` varchar(15) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`vehiculo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VEHICULO`
--

LOCK TABLES `VEHICULO` WRITE;
/*!40000 ALTER TABLE `VEHICULO` DISABLE KEYS */;
INSERT INTO `VEHICULO` VALUES (4,'por ti',18000,'asd','asd','asd','Premium',4545,'2014-07-31','Contratado','Excabadora'),(5,'\nNADA POR QUE NO SE QUE PONER',222,'CAT','CAT-100MN','SD565654411','Diesel',150.51,'2023-07-25','Contratado','Camion de'),(6,'COLOR VERDE Y NARANJA',15000,'CAT','2023','20014FA2FFF','Diesel',15000,'2023-07-18','Contratado','Excabadora'),(7,'',125000,'cat','2018-mkoda','4554dsdf','Diesel',323,NULL,'Contratado','Excabadora');
/*!40000 ALTER TABLE `VEHICULO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WORKPLACE`
--

DROP TABLE IF EXISTS `WORKPLACE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WORKPLACE` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clave_trabajo` varchar(255) DEFAULT NULL,
  `nombre_trabajo` varchar(255) DEFAULT NULL,
  `periodo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WORKPLACE`
--

LOCK TABLES `WORKPLACE` WRITE;
/*!40000 ALTER TABLE `WORKPLACE` DISABLE KEYS */;
/*!40000 ALTER TABLE `WORKPLACE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-10 10:03:52
