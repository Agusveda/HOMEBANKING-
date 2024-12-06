-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: tpint_grupo_6_lab4
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `DNI` int DEFAULT NULL,
  `CUIL` int DEFAULT NULL,
  `Nombre` varchar(60) DEFAULT NULL,
  `Apellido` varchar(60) DEFAULT NULL,
  `Sexo` varchar(45) DEFAULT NULL,
  `Nacionalidad` varchar(45) DEFAULT NULL,
  `FechaNacimiento` varchar(45) DEFAULT NULL,
  `Direccion` varchar(100) DEFAULT NULL,
  `Localidad` varchar(100) DEFAULT NULL,
  `Provincia` varchar(70) DEFAULT NULL,
  `CorreoElectronico` varchar(200) DEFAULT NULL,
  `Telefono` int DEFAULT NULL,
  `Activo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,12345678,203876521,'Juan','Pérez','Masculino','Argentina','1985-07-20','Av. Siempre Viva 123','La Plata','Buenos Aires','juan.perez@example.com',1122334455,_binary ''),(2,312414,12313,'Andres','Benitez','Masculino','Argentina','123123','ad','Mar del Plata','Buenos Aires','and@hotmail.com',123123,_binary '\0'),(3,561276,123123,'Marcelo','Butazoni','Masculino','Argentina','1231','asda','Villa Carlos Paz','Córdoba','asd@hotmail.com',12313,_binary ''),(4,121122,12313,'Isma','Yeta','Masculino','Argentina','12313','asd','Mar del Plata','Buenos Aires','asd@hotmail.com',123123,_binary ''),(5,123193,124124,'Ricki','zaza','Masculino','Brasil','12313','asd','Campinas','São Paulo','zzz@hotmail.com',123344,_binary '');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `IdCliente` int DEFAULT NULL,
  `TipoCuenta` int DEFAULT NULL,
  `FechaCreacion` date DEFAULT NULL,
  `NumeroCuenta` int DEFAULT NULL,
  `CBU` int DEFAULT NULL,
  `Saldo` float DEFAULT NULL,
  `Activo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `cuenta_ibfk_2` (`IdCliente`),
  CONSTRAINT `cuenta_ibfk_2` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (36,3,1,'2024-12-01',123456789,12345678,12020,_binary ''),(37,3,2,'2024-12-05',133520718,2345120,1431000,_binary '\0'),(38,3,2,'2024-12-06',133520719,1226194403,10000,_binary '');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuota`
--

DROP TABLE IF EXISTS `cuota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuota` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `IdPrestamo` int NOT NULL,
  `NumeroCuota` int NOT NULL,
  `Monto` float NOT NULL,
  `FechaPago` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `IdPrestamo` (`IdPrestamo`),
  CONSTRAINT `cuota_ibfk_1` FOREIGN KEY (`IdPrestamo`) REFERENCES `prestamo` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuota`
--

LOCK TABLES `cuota` WRITE;
/*!40000 ALTER TABLE `cuota` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localidades`
--

DROP TABLE IF EXISTS `localidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `localidades` (
  `IDLocalidad` int NOT NULL AUTO_INCREMENT,
  `Localiadad` varchar(50) NOT NULL,
  `IDProvincia` int DEFAULT NULL,
  PRIMARY KEY (`IDLocalidad`),
  KEY `IDProvincia` (`IDProvincia`),
  CONSTRAINT `localidades_ibfk_1` FOREIGN KEY (`IDProvincia`) REFERENCES `provincias` (`IDProvincia`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localidades`
--

LOCK TABLES `localidades` WRITE;
/*!40000 ALTER TABLE `localidades` DISABLE KEYS */;
INSERT INTO `localidades` VALUES (1,'La Plata',1),(2,'Mar del Plata',1),(3,'Villa Carlos Paz',2),(4,'Campinas',3);
/*!40000 ALTER TABLE `localidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `TipoMovimiento` int NOT NULL,
  `FechaMovimiento` datetime NOT NULL,
  `Importe` float NOT NULL,
  `IdCuenta` int NOT NULL,
  `Detalle` varchar(100) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento`
--

LOCK TABLES `movimiento` WRITE;
/*!40000 ALTER TABLE `movimiento` DISABLE KEYS */;
INSERT INTO `movimiento` VALUES (4,4,'2024-11-27 00:00:00',1200,21,'prueba'),(5,4,'2024-11-27 00:00:00',2500,23,'prueba detalle'),(6,4,'2024-12-02 00:00:00',2000,29,'le envio 2000'),(7,4,'2024-12-02 00:00:00',-2000,32,'le envio 2000');
/*!40000 ALTER TABLE `movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nacionalidades`
--

DROP TABLE IF EXISTS `nacionalidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nacionalidades` (
  `IdNacionalidad` int NOT NULL AUTO_INCREMENT,
  `Pais` varchar(100) NOT NULL,
  PRIMARY KEY (`IdNacionalidad`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nacionalidades`
--

LOCK TABLES `nacionalidades` WRITE;
/*!40000 ALTER TABLE `nacionalidades` DISABLE KEYS */;
INSERT INTO `nacionalidades` VALUES (1,'Argentina'),(2,'Brasil'),(3,'Chile'),(4,'Uruguay'),(5,'Paraguay');
/*!40000 ALTER TABLE `nacionalidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamo` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `IdCliente` int NOT NULL,
  `IdCuenta` int NOT NULL,
  `ImportePedidoCliente` float NOT NULL,
  `FechaAlta` datetime NOT NULL,
  `PlazoPago` int NOT NULL,
  `ImportePagarXmes` float NOT NULL,
  `CantidadCuotas` int NOT NULL,
  `confirmacion` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `IdCliente` (`IdCliente`),
  CONSTRAINT `prestamo_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
INSERT INTO `prestamo` VALUES (2,3,1,100000,'2024-12-01 14:10:45',12,16666.7,6,0),(3,3,37,150000,'2024-12-01 14:18:26',12,25000,6,0),(4,3,44,850000,'2024-12-02 09:36:00',12,35416.7,24,0),(5,3,36,5000,'2024-12-02 10:03:49',12,416.67,12,0),(6,3,22,850000,'2024-12-02 11:40:32',12,141667,6,0),(7,3,11,630000,'2024-12-02 11:52:36',12,105000,6,1),(8,3,13,439999,'2024-12-02 11:58:46',12,73333.2,6,1),(9,3,37,990000,'2024-12-02 13:42:24',12,41250,24,1),(10,3,25,125000,'2024-12-05 10:21:04',12,20833.3,6,1),(11,3,37,540000,'2024-12-05 17:50:30',12,45000,12,1),(12,3,37,890000,'2024-12-05 19:17:42',12,148333,6,1);
/*!40000 ALTER TABLE `prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provincias`
--

DROP TABLE IF EXISTS `provincias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provincias` (
  `IDProvincia` int NOT NULL AUTO_INCREMENT,
  `IdNacionalidad` int DEFAULT NULL,
  `Provincia` varchar(50) NOT NULL,
  PRIMARY KEY (`IDProvincia`),
  KEY `IdNacionalidad` (`IdNacionalidad`),
  CONSTRAINT `provincias_ibfk_1` FOREIGN KEY (`IdNacionalidad`) REFERENCES `nacionalidades` (`IdNacionalidad`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provincias`
--

LOCK TABLES `provincias` WRITE;
/*!40000 ALTER TABLE `provincias` DISABLE KEYS */;
INSERT INTO `provincias` VALUES (1,1,'Buenos Aires'),(2,1,'Córdoba'),(3,2,'São Paulo');
/*!40000 ALTER TABLE `provincias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `NombreUsuario` varchar(45) DEFAULT NULL,
  `Contraseña` varchar(45) DEFAULT NULL,
  `TipoUsario` bit(1) DEFAULT NULL,
  `IdCliente` int DEFAULT NULL,
  `Activo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `usuario_ibfk_1` (`IdCliente`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'juanperez','password123',_binary '\0',1,_binary ''),(2,'Andres','1996',_binary '',2,_binary ''),(3,'Marcelo','1234',_binary '\0',3,_binary ''),(4,'Isma','1234',_binary '\0',4,_binary ''),(5,'Ricky','1617',_binary '',5,_binary '');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-06 11:13:59
