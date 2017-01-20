-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 20, 2017 at 09:15 AM
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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf32;

--
-- Dumping data for table `traffic_violation`
--

INSERT INTO `traffic_violation` (`id`, `location`, `date_time`, `day`, `photo_id`, `type`, `description`) VALUES
(1, 'Farmgate', '2017-01-07 07:09:39', 'Saturday', 1, 'Signal Violation', ' '),
(2, 'Mirpur 10', '2017-01-04 14:30:00', 'Wednesday', 2, 'Vehicle in Footpath', 'foul motorbikewala'),
(7, 'Md. pur', '2017-01-03 01:45:00', 'Tuesday', 5, 'Wrong Parking', ' '),
(8, 'Md. pur', '2017-01-03 01:45:00', 'Tuesday', 5, 'Wrong Lane', 'Secretary''s Car in the wrong lane'),
(9, 'Mirpur 10', '2017-01-04 15:08:00', 'Wednesday', 4, 'Signal Violation', 'A motorbike with ID gha-123456 violated red signal'),
(10, 'Mirpur 2', '2016-12-31 15:17:00', 'Saturday', 4, 'Signal Violation', 'Leguna violated signal'),
(11, 'Badda', '2017-01-02 12:06:00', 'Monday', 4, 'Signal Violation', 'A brown fox run over the lazy dog'),
(12, 'Rampura', '2017-01-10 13:24:00', 'Tuesday', 4, 'Signal Violation', 'A motorbike with ID gha-123457 violated red signal'),
(13, 'Farmgate', '2017-01-03 12:32:00', 'Tuesday', 4, 'Wrong Parking', 'A car with ID gha-123456 parked in wrong place'),
(14, 'Polashi', '2017-01-06 17:25:00', 'Friday', 4, 'Signal Violation', 'A motorbike with ID gha-123458 violated red signal'),
(15, 'TSC', '2016-12-31 23:36:00', 'Saturday', 0, 'Drunk Driver', 'Drunk student driving motorcycle'),
(16, 'Dhanmondi 27', '2017-01-05 15:43:00', 'Thursday', 0, 'Signal Violation', 'Dhaka Indira Porbohon Leguna did not obey signal'),
(17, 'Bangla Motor', '2017-01-11 15:43:00', 'Wednesday', 0, 'Vehicle in Footpath', ' '),
(18, 'Karwan Bazar', '2017-01-07 15:43:00', 'Saturday', 0, 'Street Crossing', 'A passer by crossed street with risk under foot overbridge'),
(19, 'Azimpur', '2017-01-08 15:43:00', 'Sunday', 0, 'Signal Violation', 'A bus with ID gha-123456 violated red signal'),
(20, 'Polashi', '2017-01-08 17:43:00', 'Sunday', 0, 'Wrong Lane', 'MP''s car in wrong lane'),
(21, 'Motijheel', '2017-01-08 15:43:00', 'Sunday', 0, 'Street Crossing', ' '),
(22, 'Polashi', '2017-01-07 15:43:00', 'Saturday', 0, 'Signal Violation', 'A rickshaw with ID gha-123456 violated yellow signal'),
(23, 'Nilkhet', '2017-01-07 15:43:00', 'Friday', 0, 'Signal Violation', 'Leguna violated signal'),
(24, 'Science Lab', '2017-01-11 15:43:00', 'Tuesday', 0, 'Over Speed', 'Leguna drove in high speed'),
(25, 'Polashi', '2017-01-02 17:51:00', 'Sunday', 0, 'Signal Violation', 'sadasdad'),
(26, 'Polashi', '2017-01-03 17:51:00', 'Monday', 0, 'Wrong Parking', 'Bus Parked in wrong place and thus created traffic jam'),
(28, 'Polashi', '2017-01-03 17:54:00', 'Tuesday', 0, 'Wrong Lane', 'DU''s double decker bus in wrong lane'),
(29, 'Polashi', '2017-01-17 17:54:00', 'Tuesday', 0, 'Vehicle in Footpath', ' '),
(30, 'Polashi', '2017-01-10 17:33:00', 'Tuesday', 0, 'Signal Violation', 'A motorbike with ID gha-123456 violated red signal'),
(31, 'Asad Gate', '2017-01-03 17:54:00', 'Tuesday', 0, 'Wrong Lane', 'Rakin''s car in wrong lane'),
(32, 'Shantinogor', '2017-01-05 17:54:00', 'Thursday', 0, 'Over Speed', 'Sabbir drove car in high speed'),
(33, 'Polashi', '2017-01-13 15:00:00', 'Friday', 0, 'Over Speed', 'A motorcycle over speed'),
(34, 'Polashi', '2017-01-03 15:02:00', 'Tuesday', 0, 'Signal Violation', ' '),
(35, 'Rampura', '2017-01-03 03:02:00', 'Tuesday', 0, 'Signal Violation', 'Not maintaing signal'),
(36, 'Polashi', '2017-01-06 03:02:00', 'Friday', 0, 'Street Crossing', ' '),
(48, 'Farmgate', '2017-01-17 04:39:00', 'Tuesday', 0, 'Drunk Driver', 'Jami Sami');

-- --------------------------------------------------------

--
-- Table structure for table `violation_type`
--

CREATE TABLE IF NOT EXISTS `violation_type` (
`id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `violation_type`
--

INSERT INTO `violation_type` (`id`, `name`) VALUES
(1, 'Signal Violation'),
(2, 'Over Speed'),
(3, 'Vehicle in Footpath'),
(4, 'Drunk Driver'),
(5, 'Wrong Lane'),
(6, 'Wrong Parking'),
(7, 'Street Crossing'),
(8, 'Over Speed');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `traffic_violation`
--
ALTER TABLE `traffic_violation`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `violation_type`
--
ALTER TABLE `violation_type`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `traffic_violation`
--
ALTER TABLE `traffic_violation`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT for table `violation_type`
--
ALTER TABLE `violation_type`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
