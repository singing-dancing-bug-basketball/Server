CREATE DATABASE  IF NOT EXISTS `online_exam_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `online_exam_system`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: online_exam_system
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `question` (
  `question_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stem` varchar(100) NOT NULL,
  `selection_id` int(10) unsigned NOT NULL,
  `content` varchar(100) NOT NULL,
  PRIMARY KEY (`question_id`),
  KEY `question_fk` (`selection_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (2,'老年人一天吃几只鸡蛋才合适:',1,'A:2只@caixukun@B:1~2只@caixukun@C:1只@caixukun@'),(3,'柠檬汁有哪些营养含量:',1,'A:维生素A和维生素C@caixukun@B:维生素B1和维生素C@caixukun@C:维生素C@caixukun@D:维生素B6'),(4,'酒中含有酒精，饮酒过多或经常饮酒，会造成酒精中毒，使身体受损，那么，饮酒对人体的哪些器官最为有害:',3,'A:眼睛@caixukun@B:皮肤@caixukun@C:心脏@caixukun@D:肺'),(5,'ces',1,'a@caixukun@b@caixukun@c@caixukun@d'),(6,'ces',1,'a@caixukun@b@caixukun@c@caixukun@d');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_ownership`
--

DROP TABLE IF EXISTS `question_ownership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `question_ownership` (
  `test_paper_id` int(10) unsigned NOT NULL,
  `question_id` int(10) unsigned NOT NULL,
  `score` int(10) unsigned NOT NULL COMMENT 'score per question',
  PRIMARY KEY (`test_paper_id`,`question_id`),
  KEY `question_ownership_fk_1` (`question_id`),
  CONSTRAINT `question_ownership_fk` FOREIGN KEY (`test_paper_id`) REFERENCES `test_paper` (`test_paper_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_ownership_fk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_ownership`
--

LOCK TABLES `question_ownership` WRITE;
/*!40000 ALTER TABLE `question_ownership` DISABLE KEYS */;
INSERT INTO `question_ownership` VALUES (1,2,25),(1,3,25),(1,4,25),(13,2,25),(13,3,25);
/*!40000 ALTER TABLE `question_ownership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `record` (
  `student_id` varchar(100) NOT NULL,
  `test_id` int(10) unsigned NOT NULL,
  `question_id` int(10) unsigned NOT NULL,
  `selection_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`student_id`,`test_id`,`question_id`,`selection_id`),
  KEY `record_fk_3` (`selection_id`),
  KEY `record_fk` (`question_id`),
  KEY `record_fk_1` (`test_id`),
  CONSTRAINT `record_fk` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `record_fk_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `record_fk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES ('1160300506',1,2,1),('1160300506',1,3,1),('1160300506',1,4,1),('1160300527',1,3,1),('1160300527',1,2,3),('1160300527',1,4,3);
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student` (
  `student_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('1160300506','高猛','1160300506'),('1160300525','陈进龙','1160300525'),('1160300527','肖松','1160300527');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `test` (
  `test_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `test_paper_id` int(10) unsigned NOT NULL,
  `title` varchar(100) NOT NULL COMMENT 'only student can see',
  `start_time` datetime NOT NULL COMMENT 'test start time',
  `end_time` datetime NOT NULL COMMENT 'test end time',
  PRIMARY KEY (`test_id`),
  KEY `test_fk` (`test_paper_id`),
  CONSTRAINT `test_fk` FOREIGN KEY (`test_paper_id`) REFERENCES `test_paper` (`test_paper_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,1,'学业水平测试','2019-05-01 00:00:00','2019-06-01 00:00:00'),(2,6,'2019-03-01 00:00:00','2019-02-28 16:00:00','2019-03-31 16:00:00'),(3,7,'概率论','2019-02-28 16:00:00','2019-03-31 16:00:00');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_paper`
--

DROP TABLE IF EXISTS `test_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `test_paper` (
  `test_paper_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `duration` int(10) unsigned NOT NULL COMMENT 'minute',
  PRIMARY KEY (`test_paper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_paper`
--

LOCK TABLES `test_paper` WRITE;
/*!40000 ALTER TABLE `test_paper` DISABLE KEYS */;
INSERT INTO `test_paper` VALUES (1,'学业水平测试',120),(6,'期末测试',120),(7,'概率论',120),(8,'微积分',120),(9,'电路',120),(10,'形式与政策',120),(11,'数据分析',120),(12,'信息安全',120),(13,'测试',50);
/*!40000 ALTER TABLE `test_paper` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-12  1:16:01
