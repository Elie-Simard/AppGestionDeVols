-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 05, 2024 at 05:01 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vols`
--

CREATE DATABASE IF NOT EXISTS `vols`;
USE `vols`;
-- --------------------------------------------------------

--
-- Table structure for table `avion`
--

CREATE TABLE `avion` (
                         `avion_id` int(11) NOT NULL,
                         `type_avion` varchar(255) DEFAULT NULL,
                         `nb_place` int(11) DEFAULT NULL,
                         `categorie` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `avion`
--

INSERT INTO `avion` (`avion_id`, `type_avion`, `nb_place`, `categorie`) VALUES
                                                                            (1, 'Boeing 737', 180, 1),
                                                                            (2, 'Airbus A320', 160, 2),
                                                                            (3, 'Boeing 777', 300, 3),
                                                                            (4, 'Airbus A380', 500, 1),
                                                                            (5, 'Boeing 747', 400, 2),
                                                                            (6, 'AirBus', 100, 1),
                                                                            (7, 'Boeing', 150, 1),
                                                                            (9, 'Inconnu', 0, 3),
                                                                            (10, 'Inconnu', 0, 3),
                                                                            (33, 'Neon', 444, 1),
                                                                            (45, 'Boeing', 12, 1),
                                                                            (55, 'Airbus', 100, 1),
                                                                            (99, 'Inconnu', 0, 3),
                                                                            (144, 'JetSet', 190, 1),
                                                                            (441, 'Plasma', 441, 1),
                                                                            (2246, 'boeing', 12512, 1),
                                                                            (4141, 'Inconnu', 0, 3),
                                                                            (11263, 'Inconnu', 0, 3),
                                                                            (12361, 'BOEING', 12365, 1);

-- --------------------------------------------------------

--
-- Table structure for table `vol`
--

CREATE TABLE `vol` (
                       `vol_id` int(11) NOT NULL,
                       `destination` varchar(255) DEFAULT NULL,
                       `date_depart` varchar(25) DEFAULT NULL,
                       `avion_id` int(11) DEFAULT NULL,
                       `nb_res` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vol`
--

INSERT INTO `vol` (`vol_id`, `destination`, `date_depart`, `avion_id`, `nb_res`) VALUES
                                                                                     (1, 'Paris', '31/12/2024', 1, 5),
                                                                                     (2, 'New York', '16/03/2024', 2, 0),
                                                                                     (3, 'Tokyo', '17/03/2024', 3, 0),
                                                                                     (4, 'Londres', '18/03/2024', 4, 55),
                                                                                     (5, 'Berlin', '19/03/2024', 5, 0),
                                                                                     (6, 'Madrid', '20/03/2024', 1, 0),
                                                                                     (7, 'Rome', '21/03/2024', 2, 0),
                                                                                     (8, 'Dubai', '22/03/2024', 3, 0),
                                                                                     (9, 'Sydney', '23/03/2024', 4, 0),
                                                                                     (13, 'Milan', '10/10/2025', 7, 10),
                                                                                     (16, 'Sherbrooke', '10/10/2025', 441, 0),
                                                                                     (17, 'Amsterdam', '09/09/2025', 10, 0),
                                                                                     (18, 'Nice', '06/06/2024', 3, 0),
                                                                                     (19, 'Quebec', '10/10/2024', 5, 0),
                                                                                     (33, 'Saguenay', '20/10/2025', 5, 0),
                                                                                     (44, 'Munich', '01/01/2025', 1, 10),
                                                                                     (111, 'Trois-Rivière', '01/01/2025', 1, 0),
                                                                                     (151, 'Punta Cana', '01/01/2025', 45, 0),
                                                                                     (155, 'Toronto', '10/10/2024', 4, 0),
                                                                                     (156, 'Vancouver', '01/05/2024', 99, 0),
                                                                                     (412, 'Prague', '01/01/2025', 9, 0),
                                                                                     (444, 'Asbetos', '01/01/2025', 33, 0),
                                                                                     (542, 'Barcelone', '01/01/2025', 4, 0),
                                                                                     (1000, 'Paris', '01/01/2025', 1, 0),
                                                                                     (1112, 'alma', '01/01/2025', 1, 0),
                                                                                     (1231, 'Detroit', '01/01/2025', 1, 0),
                                                                                     (1235, 'Milan', '01/01/2025', 1, 0),
                                                                                     (1311, 'Toronto', '01/01/2025', 1, 0),
                                                                                     (1451, 'berlin', '10/10/2025', 2246, 0),
                                                                                     (5555, 'Congo', '01/01/2025', 4, 0),
                                                                                     (11321, 'Mascouche', '01/01/2025', 2, 0),
                                                                                     (12311, 'Gaspesie', '01/01/2025', 9, 0),
                                                                                     (12351, 'Milano', '01/01/2025', 144, 0),
                                                                                     (12361, 'Bahamas', '01/01/2025', 1, 0),
                                                                                     (1111111, 'Montreal', '01/01/2025', 11263, 0);

-- --------------------------------------------------------

--
-- Table structure for table `volBasPrix`
--

CREATE TABLE `volBasPrix` (
                              `vol_id` int(11) NOT NULL,
                              `repas_xtra` tinyint(1) DEFAULT NULL,
                              `choix_siege_xtra` tinyint(1) DEFAULT NULL,
                              `divertissement_xtra` tinyint(1) DEFAULT NULL,
                              `ecouteur_xtra` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `volBasPrix`
--

INSERT INTO `volBasPrix` (`vol_id`, `repas_xtra`, `choix_siege_xtra`, `divertissement_xtra`, `ecouteur_xtra`) VALUES
                                                                                                                  (1, 1, 0, 1, 0),
                                                                                                                  (2, 0, 1, 0, 1),
                                                                                                                  (13, 1, 1, 0, 1),
                                                                                                                  (16, 0, 1, 1, 0),
                                                                                                                  (33, 1, 0, 1, 0),
                                                                                                                  (444, 1, 0, 1, 0),
                                                                                                                  (1235, 0, 1, 1, 0),
                                                                                                                  (1451, 0, 0, 0, 1),
                                                                                                                  (12351, 0, 1, 0, 1),
                                                                                                                  (12361, 1, 1, 0, 0),
                                                                                                                  (1111111, 1, 0, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `volCharter`
--

CREATE TABLE `volCharter` (
                              `vol_id` int(11) NOT NULL,
                              `repas_luxe_xtra` tinyint(1) DEFAULT NULL,
                              `siege_luxe_xtra` tinyint(1) DEFAULT NULL,
                              `wifi_xtra` tinyint(1) DEFAULT NULL,
                              `salon_vip_xtra` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `volCharter`
--

INSERT INTO `volCharter` (`vol_id`, `repas_luxe_xtra`, `siege_luxe_xtra`, `wifi_xtra`, `salon_vip_xtra`) VALUES
                                                                                                             (3, 1, 1, 0, 1),
                                                                                                             (4, 0, 1, 1, 0),
                                                                                                             (44, 1, 0, 1, 0),
                                                                                                             (155, 1, 1, 0, 1),
                                                                                                             (412, 0, 0, 0, 1),
                                                                                                             (542, 1, 1, 1, 0),
                                                                                                             (1311, 1, 0, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `volPrive`
--

CREATE TABLE `volPrive` (
                            `vol_id` int(11) NOT NULL,
                            `repas_luxe` tinyint(1) DEFAULT NULL,
                            `choix_siege_luxe` tinyint(1) DEFAULT NULL,
                            `wifi` tinyint(1) DEFAULT NULL,
                            `salon_vip` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `volPrive`
--

INSERT INTO `volPrive` (`vol_id`, `repas_luxe`, `choix_siege_luxe`, `wifi`, `salon_vip`) VALUES
                                                                                             (5, 1, 1, 1, 1),
                                                                                             (6, 0, 1, 0, 1),
                                                                                             (151, 1, 0, 0, 0),
                                                                                             (156, 1, 0, 0, 0),
                                                                                             (12311, 0, 0, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `volRegulier`
--

CREATE TABLE `volRegulier` (
                               `vol_id` int(11) NOT NULL,
                               `repas_inclus` tinyint(1) DEFAULT NULL,
                               `choix_siege_inclus` tinyint(1) DEFAULT NULL,
                               `espace_xtra` tinyint(1) DEFAULT NULL,
                               `bagage_soute_xtra` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `volRegulier`
--

INSERT INTO `volRegulier` (`vol_id`, `repas_inclus`, `choix_siege_inclus`, `espace_xtra`, `bagage_soute_xtra`) VALUES
                                                                                                                   (7, 1, 0, 1, 1),
                                                                                                                   (8, 0, 1, 0, 1),
                                                                                                                   (9, 1, 1, 1, 0),
                                                                                                                   (18, 1, 0, 0, 0),
                                                                                                                   (1112, 1, 1, 0, 0),
                                                                                                                   (5555, 1, 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `avion`
--
ALTER TABLE `avion`
    ADD PRIMARY KEY (`avion_id`);

--
-- Indexes for table `vol`
--
ALTER TABLE `vol`
    ADD PRIMARY KEY (`vol_id`),
    ADD KEY `avion_id` (`avion_id`);

--
-- Indexes for table `volBasPrix`
--
ALTER TABLE `volBasPrix`
    ADD PRIMARY KEY (`vol_id`);

--
-- Indexes for table `volCharter`
--
ALTER TABLE `volCharter`
    ADD PRIMARY KEY (`vol_id`);

--
-- Indexes for table `volPrive`
--
ALTER TABLE `volPrive`
    ADD PRIMARY KEY (`vol_id`);

--
-- Indexes for table `volRegulier`
--
ALTER TABLE `volRegulier`
    ADD PRIMARY KEY (`vol_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `vol`
--
ALTER TABLE `vol`
    ADD CONSTRAINT `vol_ibfk_1` FOREIGN KEY (`avion_id`) REFERENCES `avion` (`avion_id`);

--
-- Constraints for table `volBasPrix`
--
ALTER TABLE `volBasPrix`
    ADD CONSTRAINT `volbasprix_ibfk_1` FOREIGN KEY (`vol_id`) REFERENCES `vol` (`vol_id`) ON DELETE CASCADE;

--
-- Constraints for table `volCharter`
--
ALTER TABLE `volCharter`
    ADD CONSTRAINT `volcharter_ibfk_1` FOREIGN KEY (`vol_id`) REFERENCES `vol` (`vol_id`) ON DELETE CASCADE;

--
-- Constraints for table `volPrive`
--
ALTER TABLE `volPrive`
    ADD CONSTRAINT `volprive_ibfk_1` FOREIGN KEY (`vol_id`) REFERENCES `vol` (`vol_id`) ON DELETE CASCADE;

--
-- Constraints for table `volRegulier`
--
ALTER TABLE `volRegulier`
    ADD CONSTRAINT `volregulier_ibfk_1` FOREIGN KEY (`vol_id`) REFERENCES `vol` (`vol_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
