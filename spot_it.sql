-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 12, 2017 at 04:00 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `spot_it`
--

-- --------------------------------------------------------

--
-- Table structure for table `traffic_violation`
--

CREATE TABLE IF NOT EXISTS `traffic_violation` (
`id` int(11) NOT NULL,
  `location` varchar(30) CHARACTER SET utf8 NOT NULL,
  `date_time` datetime NOT NULL,
  `day` varchar(15) CHARACTER SET utf8 NOT NULL,
  `photo_id` int(11) NOT NULL,
  `type` varchar(50) CHARACTER SET utf8 NOT NULL,
  `description` varchar(150) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf32;

--
-- Dumping data for table `traffic_violation`
--

INSERT INTO `traffic_violation` (`id`, `location`, `date_time`, `day`, `photo_id`, `type`, `description`) VALUES
(1, 'farmgate', '2017-01-07 07:09:39', '', 1, 'signal break', NULL),
(2, 'mirpur', '2017-01-04 14:30:00', '', 2, 'motorcycle in footpath', 'foul motorbikewala'),
(7, 'md. pur', '2017-01-03 01:45:00', '', 5, 'vugichugi', 'another vugichugiuu'),
(8, 'md. pur', '2017-01-03 01:45:00', '', 5, 'vugichugi', 'another vugichugiuu'),
(9, 'ffa', '2017-01-04 15:08:00', '', 4, 'Signal Break', 'fasfa'),
(10, 'hfhdhh', '2016-12-31 15:17:00', '', 4, 'Signal Break', 'lllllll'),
(11, 'gdgsdgsd', '2017-01-02 12:06:00', '', 4, 'Signal Break', 'dagfagasga'),
(12, 'fffsa', '2017-01-10 13:24:00', '', 4, 'Signal Break', 'fsaffsa'),
(13, 'fsfaf', '2017-01-03 12:32:00', '', 4, 'Signal Break', 'fsagrhshtht'),
(14, 'Polashi', '2017-01-06 17:25:00', 'null', 4, 'Signal Break', 'gggg'),
(15, 'Polashi', '2017-01-06 14:36:00', 'Friday', 0, 'Signal Break', 'llll'),
(16, 'Polashi', '2017-01-05 15:43:00', 'Thursday', 0, 'Signal Break', 'sadasdad'),
(17, 'Polashi', '2017-01-11 15:43:00', 'Wednesday', 0, 'Signal Break', 'sadasdad'),
(18, 'Polashi', '2017-01-07 15:43:00', 'Saturday', 0, 'Signal Break', 'sadasdad'),
(19, 'Polashi', '2017-01-08 15:43:00', 'Sunday', 0, 'Signal Break', 'sadasdad'),
(20, 'Polashi', '2017-01-08 15:43:00', 'Sunday', 0, 'Signal Break', 'sadasdad'),
(21, 'Polashi', '2017-01-08 15:43:00', 'Sunday', 0, 'Signal Break', 'sadasdad'),
(22, 'Polashi', '2017-01-07 15:43:00', 'Saturday', 0, 'Signal Break', 'sadasdad'),
(23, 'Polashi', '2017-01-07 15:43:00', 'Friday', 0, 'Signal Break', 'sadasdad'),
(24, 'Polashi', '2017-01-11 15:43:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(25, 'Polashi', '2017-01-02 17:51:00', 'Sunday', 0, 'Signal Break', 'sadasdad'),
(26, 'Polashi', '2017-01-03 17:51:00', 'Monday', 0, 'Signal Break', 'sadasdad'),
(27, 'Polashi', '2017-01-03 17:54:00', 'Monday', 0, 'Signal Break', 'sadasdad'),
(28, 'Polashi', '2017-01-03 17:54:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(29, 'Polashi', '2017-01-03 17:54:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(30, 'Polashi', '2017-01-03 17:54:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(31, 'Polashi', '2017-01-03 17:54:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(32, 'Polashi', '2017-01-03 17:54:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(33, 'Polashi', '2017-01-03 15:00:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(34, 'Polashi', '2017-01-03 15:02:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(35, 'Polashi', '2017-01-03 03:02:00', 'Tuesday', 0, 'Signal Break', 'sadasdad'),
(36, 'Polashi', '2017-01-06 03:02:00', 'Friday', 0, 'Signal Break', 'sadasdad'),
(37, 'g', '2017-01-12 03:24:00', 'Thursday', 0, 'Signal Break', 'g'),
(38, 'g', '2017-01-12 03:24:00', 'Thursday', 0, 'Signal Break', 'g');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `traffic_violation`
--
ALTER TABLE `traffic_violation`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `traffic_violation`
--
ALTER TABLE `traffic_violation`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=39;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
