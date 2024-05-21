CREATE DATABASE IF NOT EXISTS `$DB_SCHEMA` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `$DB_SCHEMA`;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sr_article_bookmark`
--

DROP TABLE IF EXISTS `sr_article_bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_article_bookmark` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `article_url` varchar(2048) NOT NULL,
  `article_title` varchar(128) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sr_article_bookmark_user_id_index` (`user_id`),
  CONSTRAINT `sr_article_bookmark_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `sr_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_blog`
--

DROP TABLE IF EXISTS `sr_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_blog` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `author_id` bigint unsigned NOT NULL,
  `blog_title` varchar(128) NOT NULL,
  `blog_description` varchar(1024) NOT NULL,
  `creation_date` datetime NOT NULL,
  `last_edit_date` datetime NOT NULL,
  `last_editor_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sr_blog_author_id_foreign` (`author_id`),
  KEY `sr_blog_last_editor_id_foreign` (`last_editor_id`),
  CONSTRAINT `sr_blog_author_id_foreign` FOREIGN KEY (`author_id`) REFERENCES `sr_user` (`id`),
  CONSTRAINT `sr_blog_last_editor_id_foreign` FOREIGN KEY (`last_editor_id`) REFERENCES `sr_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_jwt_refresh_token`
--

DROP TABLE IF EXISTS `sr_jwt_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_jwt_refresh_token` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `token` varchar(255) NOT NULL,
  `expiration_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sr_jwt_refresh_token_user_id_foreign` (`user_id`),
  CONSTRAINT `sr_jwt_refresh_token_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `sr_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_post`
--

DROP TABLE IF EXISTS `sr_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_post` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `blog_id` bigint unsigned NOT NULL,
  `post_status` enum('POST_STATUS_DELETED','POST_STATUS_PUBLISHED','POST_STATUS_APPROVED','POST_STATUS_DENIED','POST_STATUS_SUBMITTED') NOT NULL,
  `creation_date` datetime NOT NULL,
  `post_title` varchar(128) NOT NULL,
  `post_description` varchar(1024) NOT NULL,
  `post_like_count` bigint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `sr_post_blog_id_index` (`blog_id`),
  KEY `sr_post_post_like_count_index` (`post_like_count`),
  CONSTRAINT `sr_post_blog_id_foreign` FOREIGN KEY (`blog_id`) REFERENCES `sr_blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_post_categories`
--

DROP TABLE IF EXISTS `sr_post_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_post_categories` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint unsigned NOT NULL,
  `post_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sr_post_categories_category_id_foreign` (`category_id`),
  KEY `sr_post_categories_post_id_foreign` (`post_id`),
  CONSTRAINT `sr_post_categories_category_id_foreign` FOREIGN KEY (`category_id`) REFERENCES `sr_post_category` (`id`),
  CONSTRAINT `sr_post_categories_post_id_foreign` FOREIGN KEY (`post_id`) REFERENCES `sr_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_post_category`
--

DROP TABLE IF EXISTS `sr_post_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_post_category` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `category_name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sr_post_category_category_name_unique` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_post_like`
--

DROP TABLE IF EXISTS `sr_post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_post_like` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `post_id` bigint unsigned NOT NULL,
  `user_id` bigint unsigned NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sr_post_like_post_id_index` (`post_id`),
  KEY `sr_post_like_user_id_foreign` (`user_id`),
  CONSTRAINT `sr_post_like_post_id_foreign` FOREIGN KEY (`post_id`) REFERENCES `sr_post` (`id`),
  CONSTRAINT `sr_post_like_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `sr_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_post_meta`
--

DROP TABLE IF EXISTS `sr_post_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_post_meta` (
  `post_id` bigint unsigned NOT NULL,
  `post_body` text,
  `deletion_date` datetime DEFAULT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_editor_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  CONSTRAINT `sr_post_meta_post_id_foreign` FOREIGN KEY (`post_id`) REFERENCES `sr_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_user`
--

DROP TABLE IF EXISTS `sr_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(24) NOT NULL,
  `user_email` varchar(320) NOT NULL,
  `password` varchar(64) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sr_user_username_unique` (`username`),
  UNIQUE KEY `sr_user_user_email_unique` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sr_user_role`
--

DROP TABLE IF EXISTS `sr_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_name` enum('ROLE_USER','ROLE_MODERATOR','ROLE_ADMIN') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sr_user_role`
--

LOCK TABLES `sr_user_role` WRITE;
/*!40000 ALTER TABLE `sr_user_role` DISABLE KEYS */;
INSERT INTO `sr_user_role` (`id`, `role_name`) VALUES (1,'ROLE_USER'),(2,'ROLE_MODERATOR'),(3,'ROLE_ADMIN');
/*!40000 ALTER TABLE `sr_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sr_user_roles`
--

DROP TABLE IF EXISTS `sr_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_user_roles` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `role_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sr_user_roles_role_id_foreign` (`role_id`),
  KEY `sr_user_roles_user_id_foreign` (`user_id`),
  CONSTRAINT `sr_user_roles_role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `sr_user_role` (`id`),
  CONSTRAINT `sr_user_roles_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `sr_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
