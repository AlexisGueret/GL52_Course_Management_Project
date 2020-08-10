-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mer. 17 juin 2020 à 21:38
-- Version du serveur :  5.7.21
-- Version de PHP :  5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `gl52_project`
--

-- --------------------------------------------------------

--
-- Structure de la table `choice`
--

DROP TABLE IF EXISTS `choice`;
CREATE TABLE IF NOT EXISTS `choice` (
  `NUM_EXAM` bigint(8) NOT NULL,
  `NUM_QUESTION` bigint(10) NOT NULL,
  `NUM_CHOICE` bigint(10) NOT NULL,
  `STATEMENT` varchar(128) NOT NULL,
  `IS_CORRECT` tinyint(1) NOT NULL,
  PRIMARY KEY (`NUM_EXAM`,`NUM_QUESTION`,`NUM_CHOICE`),
  KEY `I_FK_CHOICE_QUESTION` (`NUM_EXAM`,`NUM_QUESTION`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `choice`
--

INSERT INTO `choice` (`NUM_EXAM`, `NUM_QUESTION`, `NUM_CHOICE`, `STATEMENT`, `IS_CORRECT`) VALUES
(1, 1, 1, 'Oui', 0),
(1, 1, 2, 'Non', 1),
(1, 2, 1, 'Un truc', 0),
(1, 2, 2, 'Un objet', 1),
(1, 2, 3, 'Un machin', 0),
(1, 3, 1, '2', 0),
(1, 3, 2, '3', 0),
(1, 3, 3, '4', 1),
(1, 3, 4, '8/2', 1),
(2, 1, 1, '1', 0),
(2, 1, 2, '2', 1);

-- --------------------------------------------------------

--
-- Structure de la table `exam`
--

DROP TABLE IF EXISTS `exam`;
CREATE TABLE IF NOT EXISTS `exam` (
  `NUM_EXAM` bigint(8) NOT NULL,
  `CODE` varchar(6) NOT NULL,
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  `TITLE` varchar(128) NOT NULL,
  `IS_VISIBLE` tinyint(1) NOT NULL,
  `TIME_LIMITE` bigint(10) NOT NULL,
  PRIMARY KEY (`NUM_EXAM`),
  KEY `I_FK_EXAM_TEACHER` (`LAST_NAME`,`FIRST_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `exam`
--

INSERT INTO `exam` (`NUM_EXAM`, `CODE`, `LAST_NAME`, `FIRST_NAME`, `TITLE`, `IS_VISIBLE`, `TIME_LIMITE`) VALUES
(1, 'GL52', 'martin', 'loic', 'Exam diagramme de classe', 1, 10000),
(2, 'GL52', 'martin', 'loic', 'Exam test time limite (5s)', 1, 5);

-- --------------------------------------------------------

--
-- Structure de la table `forum`
--

DROP TABLE IF EXISTS `forum`;
CREATE TABLE IF NOT EXISTS `forum` (
  `NUM_FORUM` int(11) NOT NULL,
  `CODE_UV` varchar(11) NOT NULL,
  PRIMARY KEY (`NUM_FORUM`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `forum`
--

INSERT INTO `forum` (`NUM_FORUM`, `CODE_UV`) VALUES
(1, 'SQ40'),
(5, 'AG51'),
(10, 'LE03'),
(15, 'GL52'),
(2, 'SQ401'),
(16, 'GL521');

-- --------------------------------------------------------

--
-- Structure de la table `is_part_of`
--

DROP TABLE IF EXISTS `is_part_of`;
CREATE TABLE IF NOT EXISTS `is_part_of` (
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  `CODE` varchar(6) NOT NULL,
  `NUM_PROJECT` bigint(10) NOT NULL,
  PRIMARY KEY (`LAST_NAME`,`FIRST_NAME`,`CODE`,`NUM_PROJECT`),
  KEY `I_FK_IS_PART_OF_STUDENT` (`LAST_NAME`,`FIRST_NAME`),
  KEY `I_FK_IS_PART_OF_PROJECT` (`CODE`,`NUM_PROJECT`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `is_part_of`
--

INSERT INTO `is_part_of` (`LAST_NAME`, `FIRST_NAME`, `CODE`, `NUM_PROJECT`) VALUES
('Audran', 'Thomas', 'AG51', 2),
('Audran', 'Thomas', 'GL52', 1),
('Birling', 'Lucas', 'GL52', 1),
('Gueret', 'Alexis', 'GL52', 1),
('Martin', 'Loic', 'AG51', 1),
('Martin', 'Loic', 'GL52', 1),
('Martin', 'Loic', 'SQ40', 1);

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  `NUM_TOPIC` int(8) NOT NULL,
  `NUM_MESSAGE` int(10) NOT NULL,
  `CONTENT` varchar(255) NOT NULL,
  `SENDING_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `NUM_FORUM` int(11) NOT NULL,
  PRIMARY KEY (`NUM_TOPIC`,`NUM_MESSAGE`,`NUM_FORUM`),
  KEY `I_FK_MESSAGE_TOPIC` (`NUM_TOPIC`),
  KEY `I_FK_MESSAGE_PERSON` (`LAST_NAME`,`FIRST_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `message`
--

INSERT INTO `message` (`LAST_NAME`, `FIRST_NAME`, `NUM_TOPIC`, `NUM_MESSAGE`, `CONTENT`, `SENDING_DATE`, `NUM_FORUM`) VALUES
('audran', 'thomas', 1, 3, 'message', '2020-06-13 14:17:44', 2),
('audran', 'thomas', 1, 2, 'message2topic1ag51', '2020-06-13 14:17:17', 2),
('audran', 'thomas', 1, 0, 'message1ag51topic1', '2020-06-13 14:04:42', 2),
('audran', 'thomas', 2, 3, 'd', '2020-06-12 19:03:56', 4),
('audran', 'thomas', 2, 0, 'a', '2020-06-12 19:03:49', 4),
('audran', 'thomas', 2, 1, 'b', '2020-06-12 19:03:51', 4),
('audran', 'thomas', 2, 2, 'c', '2020-06-12 19:03:52', 4),
('audran', 'thomas', 3, 1, 'd', '2020-06-13 14:18:02', 4),
('Audran', 'Thomas', 1, 1, 'hello there', '2020-06-13 14:33:18', 4),
('Audran', 'Thomas', 4, 1, 'hello', '2020-06-13 14:33:42', 4),
('Audran', 'Thomas', 1, 1, 'test', '2020-06-14 16:00:45', 16);

-- --------------------------------------------------------

--
-- Structure de la table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`LAST_NAME`,`FIRST_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
  `CODE` varchar(6) NOT NULL,
  `NUM_PROJECT` bigint(10) NOT NULL,
  `MARK` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`CODE`,`NUM_PROJECT`),
  KEY `I_FK_PROJECT_UV` (`CODE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `project`
--

INSERT INTO `project` (`CODE`, `NUM_PROJECT`, `MARK`) VALUES
('GL52', 1, '15.00'),
('AG51', 1, NULL),
('AG51', 2, NULL),
('AG51', 3, NULL),
('AG51', 4, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `question`
--

DROP TABLE IF EXISTS `question`;
CREATE TABLE IF NOT EXISTS `question` (
  `NUM_EXAM` bigint(8) NOT NULL,
  `NUM_QUESTION` bigint(10) NOT NULL,
  `STATEMENT` varchar(128) NOT NULL,
  `VALUE` decimal(4,2) NOT NULL,
  PRIMARY KEY (`NUM_EXAM`,`NUM_QUESTION`),
  KEY `I_FK_QUESTION_EXAM` (`NUM_EXAM`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `question`
--

INSERT INTO `question` (`NUM_EXAM`, `NUM_QUESTION`, `STATEMENT`, `VALUE`) VALUES
(1, 1, 'Y a t il des acteurs dans un diagramme de classe ?', '1.00'),
(1, 2, 'Qu\'est ce qu\'une instanciation de classe ?', '2.00'),
(1, 3, 'Qu\'est ce que vaut 2*2 ?', '2.00'),
(2, 1, '1 + 1 =', '1.00');

-- --------------------------------------------------------

--
-- Structure de la table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`LAST_NAME`,`FIRST_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `student`
--

INSERT INTO `student` (`LAST_NAME`, `FIRST_NAME`) VALUES
('Audran', 'Thomas'),
('Berling', 'Lucas'),
('Gueret', 'Alexis'),
('Martin', 'Loic');

-- --------------------------------------------------------

--
-- Structure de la table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE IF NOT EXISTS `teacher` (
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`LAST_NAME`,`FIRST_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `topic`
--

DROP TABLE IF EXISTS `topic`;
CREATE TABLE IF NOT EXISTS `topic` (
  `NUM_TOPIC` int(8) NOT NULL AUTO_INCREMENT,
  `NUM_FORUM` int(10) NOT NULL,
  `CODE_UV` varchar(6) DEFAULT NULL,
  `TITLE` varchar(128) NOT NULL,
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `AUTHOR_LAST_NAME` varchar(11) NOT NULL,
  `AUTHOR_FIRST_NAME` varchar(11) NOT NULL,
  PRIMARY KEY (`NUM_TOPIC`,`NUM_FORUM`),
  KEY `I_FK_TOPIC_PROJECT` (`NUM_FORUM`),
  KEY `I_FK_TOPIC_UV` (`CODE_UV`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `topic`
--

INSERT INTO `topic` (`NUM_TOPIC`, `NUM_FORUM`, `CODE_UV`, `TITLE`, `CREATION_DATE`, `AUTHOR_LAST_NAME`, `AUTHOR_FIRST_NAME`) VALUES
(2, 4, 'GL52', 'a', '2020-06-12 19:03:41', 'audran', 'thomas'),
(1, 3, 'LE03', 'LE03TOPIC1', '2020-06-12 19:01:29', 'audran', 'thomas'),
(1, 4, 'GL52', 'GL52topic1', '2020-06-12 19:01:07', 'audran', 'thomas'),
(1, 2, 'AG51', 'AG51topic1', '2020-06-12 19:00:50', 'audran', 'thomas'),
(3, 4, 'GL52', 'c', '2020-06-12 19:04:01', 'audran', 'thomas'),
(4, 4, 'GL52', 'Test Alexis', '2020-06-13 14:33:25', 'Audran', 'Thomas'),
(1, 16, 'GL521', 'Discussion Projet', '2020-06-14 16:00:29', 'Audran', 'Thomas');

-- --------------------------------------------------------

--
-- Structure de la table `to_be_in_charge_of`
--

DROP TABLE IF EXISTS `to_be_in_charge_of`;
CREATE TABLE IF NOT EXISTS `to_be_in_charge_of` (
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  `CODE` varchar(6) NOT NULL,
  PRIMARY KEY (`LAST_NAME`,`FIRST_NAME`,`CODE`),
  KEY `I_FK_TO_BE_IN_CHARGE_OF_TEACHER` (`LAST_NAME`,`FIRST_NAME`),
  KEY `I_FK_TO_BE_IN_CHARGE_OF_UV` (`CODE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `to_follow`
--

DROP TABLE IF EXISTS `to_follow`;
CREATE TABLE IF NOT EXISTS `to_follow` (
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  `CODE` varchar(6) NOT NULL,
  `GRADE` decimal(2,2) DEFAULT NULL,
  PRIMARY KEY (`LAST_NAME`,`FIRST_NAME`,`CODE`),
  KEY `I_FK_TO_FOLLOW_STUDENT` (`LAST_NAME`,`FIRST_NAME`),
  KEY `I_FK_TO_FOLLOW_UV` (`CODE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `to_follow`
--

INSERT INTO `to_follow` (`LAST_NAME`, `FIRST_NAME`, `CODE`, `GRADE`) VALUES
('Audran', 'Thomas', 'SQ40', NULL),
('Audran', 'Thomas', 'AG51', NULL),
('Audran', 'Thomas', 'LE03', NULL),
('Audran', 'Thomas', 'GL52', NULL),
('Martin', 'Loic', 'GL52', NULL),
('Gueret', 'Alexis', 'GL52', NULL),
('Birling', 'Lucas', 'GL52', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `to_make_a_choice`
--

DROP TABLE IF EXISTS `to_make_a_choice`;
CREATE TABLE IF NOT EXISTS `to_make_a_choice` (
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  `NUM_EXAM` bigint(8) NOT NULL,
  `NUM_QUESTION` bigint(10) NOT NULL,
  `NUM_CHOICE` bigint(10) NOT NULL,
  PRIMARY KEY (`LAST_NAME`,`FIRST_NAME`,`NUM_EXAM`,`NUM_QUESTION`,`NUM_CHOICE`),
  KEY `I_FK_TO_MAKE_A_CHOICE_STUDENT` (`LAST_NAME`,`FIRST_NAME`),
  KEY `I_FK_TO_MAKE_A_CHOICE_CHOICE` (`NUM_EXAM`,`NUM_QUESTION`,`NUM_CHOICE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `to_pass`
--

DROP TABLE IF EXISTS `to_pass`;
CREATE TABLE IF NOT EXISTS `to_pass` (
  `NUM_EXAM` bigint(8) NOT NULL,
  `LAST_NAME` varchar(30) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  `MARK` decimal(4,2) DEFAULT NULL,
  `STARTING_HOUR` datetime NOT NULL,
  `ENDING_HOUR` datetime DEFAULT NULL,
  PRIMARY KEY (`NUM_EXAM`,`LAST_NAME`,`FIRST_NAME`),
  KEY `I_FK_TO_PASS_EXAM` (`NUM_EXAM`),
  KEY `I_FK_TO_PASS_STUDENT` (`LAST_NAME`,`FIRST_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `uv`
--

DROP TABLE IF EXISTS `uv`;
CREATE TABLE IF NOT EXISTS `uv` (
  `CODE` varchar(6) NOT NULL,
  `TITLE` varchar(300) NOT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `uv`
--

INSERT INTO `uv` (`CODE`, `TITLE`) VALUES
('SQ40', 'Mathématiques-Statistiques'),
('AG51', 'advanced algoritmy'),
('LE03', 'ENGLISH Level 3 option Linguaskill'),
('GL52', 'Acquérir les compétences sur les méthodologies, les langages et les environnements de développement de logiciels : cycle de vie et méthodes agiles (SCRUM), approches fonctionnelles, méthodes d\'analyse et de conception orientées objet, spécifications formelles.');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
