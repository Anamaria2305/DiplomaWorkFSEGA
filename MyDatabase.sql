-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: bloodmanagement
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_ID` int NOT NULL,
  `hospital_ID` int NOT NULL,
  `app_date` datetime NOT NULL,
  `confirmed` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `appointments_ibfk_1` (`hospital_ID`),
  KEY `appointments_ibfk_2` (`user_ID`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`hospital_ID`) REFERENCES `hospitals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`user_ID`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (2,5,1,'2021-03-04 10:40:00',1),(16,13,1,'2021-03-06 10:20:00',1),(17,13,2,'2022-06-30 11:20:00',0),(28,5,1,'2021-09-27 10:00:00',1),(47,5,1,'2022-06-30 11:00:00',0),(50,17,2,'2022-06-24 12:20:00',1);
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bloodtests`
--

DROP TABLE IF EXISTS `bloodtests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bloodtests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `appointment_ID` int DEFAULT NULL,
  `sida` int DEFAULT NULL,
  `leucemie` int DEFAULT NULL,
  `hepatitab` int DEFAULT NULL,
  `hepatitac` int DEFAULT NULL,
  `trombocite` double DEFAULT NULL,
  `hemoglobina` double DEFAULT NULL,
  `colesterol` double DEFAULT NULL,
  `leucocite` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bloodtests_ibfk_1` (`appointment_ID`),
  CONSTRAINT `bloodtests_ibfk_1` FOREIGN KEY (`appointment_ID`) REFERENCES `appointments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodtests`
--

LOCK TABLES `bloodtests` WRITE;
/*!40000 ALTER TABLE `bloodtests` DISABLE KEYS */;
INSERT INTO `bloodtests` VALUES (1,2,0,1,1,0,1.2,1.3,4.6,2.3),(6,28,0,0,0,1,1,1,1,1),(9,50,0,0,0,0,1.4,1.2,0.7,2.3);
/*!40000 ALTER TABLE `bloodtests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bloodtype`
--

DROP TABLE IF EXISTS `bloodtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bloodtype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `blood` varchar(50) NOT NULL,
  `rh` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodtype`
--

LOCK TABLES `bloodtype` WRITE;
/*!40000 ALTER TABLE `bloodtype` DISABLE KEYS */;
INSERT INTO `bloodtype` VALUES (9,'A','pozitiv'),(10,'A','negativ'),(11,'B','pozitiv'),(12,'B','negativ'),(13,'AB','pozitiv'),(14,'AB','negativ'),(15,'O','pozitiv'),(16,'O','negativ');
/*!40000 ALTER TABLE `bloodtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospitals`
--

DROP TABLE IF EXISTS `hospitals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospitals` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phonenumber` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `phonenumber_UNIQUE` (`phonenumber`),
  UNIQUE KEY `address_UNIQUE` (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospitals`
--

LOCK TABLES `hospitals` WRITE;
/*!40000 ALTER TABLE `hospitals` DISABLE KEYS */;
INSERT INTO `hospitals` VALUES (1,'Regina Maria','Strada Victoriei nr 17','0721234567'),(2,'CTS','Strada Republicii nr 46','0751234567');
/*!40000 ALTER TABLE `hospitals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_ID` int DEFAULT NULL,
  `target_name` varchar(50) NOT NULL,
  `illness` varchar(50) DEFAULT NULL,
  `request_date` datetime NOT NULL,
  `confirmed` int NOT NULL,
  `blood_ID` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `requests_ibfk_1` (`blood_ID`),
  KEY `requests_ibfk_2` (`staff_ID`),
  CONSTRAINT `requests_ibfk_1` FOREIGN KEY (`blood_ID`) REFERENCES `bloodtype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `requests_ibfk_2` FOREIGN KEY (`staff_ID`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (7,2,'Pop Dana','accident rutier','2022-06-02 23:29:39',0,9),(8,8,'Tudor Roxana','transplant','2022-06-02 23:30:49',0,13),(9,2,'Man Daniel','leucemie','2022-06-02 23:31:17',1,14),(10,8,'Mon Tudor','accident rutier','2022-06-02 23:37:17',1,12);
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `phonenumber` varchar(50) NOT NULL,
  `hospital_ID` int DEFAULT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Username_UNIQUE` (`username`),
  UNIQUE KEY `PhoneNumber_UNIQUE` (`phonenumber`),
  KEY `staff_ibfk_2` (`hospital_ID`),
  CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`hospital_ID`) REFERENCES `hospitals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'Admin1','$2a$10$wFEflzNSpma3mjOCb9reQeIHjo3ZoKShryj4hPulLxzL7ACATd.k.','Pop Dan','0781234567',1,'admin'),(2,'MoldovanAna','$2a$10$pxkqqZYQzf5ns5ZTidHeqOC5swe6JwIeLGTOMlU2zdNg1qHPM58ba','Moldovan Ana','0771234567',1,'doctor'),(8,'AncaDraghici','$2a$10$R8rnwNqlqTkes8j6MoMJz.eBm1D6ZATpheUPFdBbTdp4SGT3wOQKq','Anca Draghici','0758654123',2,'doctor'),(9,'Admin2','$2a$10$x0ZITeUnuNXSj.b.Gg4dPuP1Mj.FlWMCnsbTGggQ/OXxJIg3e.te6','Pop Monica','0758654321',2,'admin');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stocks`
--

DROP TABLE IF EXISTS `stocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stocks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hospital_ID` int NOT NULL,
  `blood_ID` int NOT NULL,
  `quantity` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stocks_ibfk_1` (`blood_ID`),
  KEY `stocks_ibfk_2` (`hospital_ID`),
  CONSTRAINT `stocks_ibfk_1` FOREIGN KEY (`blood_ID`) REFERENCES `bloodtype` (`id`),
  CONSTRAINT `stocks_ibfk_2` FOREIGN KEY (`hospital_ID`) REFERENCES `hospitals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocks`
--

LOCK TABLES `stocks` WRITE;
/*!40000 ALTER TABLE `stocks` DISABLE KEYS */;
INSERT INTO `stocks` VALUES (1,1,9,6.9),(3,2,10,9.7),(4,1,10,6.5),(5,1,11,10.2),(6,1,12,15.3),(7,2,9,17.15),(8,1,13,12.6),(9,1,14,33),(10,1,15,45),(11,1,16,21),(12,2,11,16),(13,2,12,14),(14,2,13,13.5),(15,2,14,12),(16,2,15,10),(17,2,16,37.5);
/*!40000 ALTER TABLE `stocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(80) NOT NULL,
  `cnp` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `bloodID` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Email_UNIQUE` (`username`),
  UNIQUE KEY `CNP_UNIQUE` (`cnp`),
  UNIQUE KEY `Phone_UNIQUE` (`phone`),
  KEY `users_ibfk_1` (`bloodID`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`bloodID`) REFERENCES `bloodtype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'RaitaAnamaria','$2a$10$R8rnwNqlqTkes8j6MoMJz.eBm1D6ZATpheUPFdBbTdp4SGT3wOQKq','anamariaraita@gmail.com','6036','126',10),(13,'Sergiu Gaga','$2a$10$R8rnwNqlqTkes8j6MoMJz.eBm1D6ZATpheUPFdBbTdp4SGT3wOQKq','gagasergiu@yahoo.com','5000523125779','0770794203',9),(17,'Raita Gabriela','$2a$10$yGSHmrbQdNgSNQrOu7nIoenhy5y6wr9ul05ff6ny2m3PAs.TPVmYC','computer_anamaria@yahoo.com','2681009126206','0758654457',9);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-24 17:28:40
