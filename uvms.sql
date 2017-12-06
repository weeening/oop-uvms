-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 24, 2017 at 11:52 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uvms`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `bookingID` int(11) NOT NULL,
  `bookingDate` date NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `venueID` varchar(10) DEFAULT NULL,
  `userID` varchar(20) DEFAULT NULL,
  `transactDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`bookingID`, `bookingDate`, `startTime`, `endTime`, `venueID`, `userID`, `transactDate`) VALUES
(1, '2017-09-16', '12:00:00', '13:00:00', 'v003', '1111123456', '2017-09-16'),
(2, '2017-09-20', '09:00:00', '18:00:00', 'v001', 'L002', '2017-09-22'),
(3, '2017-09-21', '20:00:00', '21:00:00', 'v003', 'L001', '2017-09-23'),
(4, '2017-09-25', '12:00:00', '14:00:00', 'v002', 'L001', '2017-09-23'),
(5, '2017-09-29', '11:00:00', '13:00:00', 'v002', 'L003', '2017-09-23'),
(6, '2017-09-26', '12:00:00', '14:00:00', 'v001', 'L003', '2017-09-24'),
(7, '2017-09-26', '13:00:00', '14:00:00', 'v003', 'L003', '2017-09-24');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` varchar(20) NOT NULL,
  `userPass` varchar(50) NOT NULL,
  `userName` varchar(150) DEFAULT NULL,
  `contactNo` varchar(20) DEFAULT NULL,
  `userStatus` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `userPass`, `userName`, `contactNo`, `userStatus`) VALUES
('1111123456', 'qwerty123', 'Tan Xiao Ming', '0123948594', 'Student'),
('1234567890', 'abcde123', 'Abu Bin Ali', '0147394852', 'Student'),
('L001', 'asdfg123', 'Ben Lim', '0129999999', 'Lecturer'),
('L002', 'qwertyuiop123', 'Jeremy Lin', '0128888888', 'Lecturer'),
('L003', 'password', 'Tan Ah Kaw', '082398394', 'Lecturer');

-- --------------------------------------------------------

--
-- Table structure for table `venue`
--

CREATE TABLE `venue` (
  `venueID` varchar(10) NOT NULL,
  `building` varchar(150) NOT NULL,
  `room` varchar(150) NOT NULL,
  `capacity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `venue`
--

INSERT INTO `venue` (`venueID`, `building`, `room`, `capacity`) VALUES
('v001', 'CLC', 'MSMX 0001 - Large Lecture Hall', 250),
('v002', 'CLC', 'MSMX 2002 - Small Lecture Hall', 60),
('v003', 'FIST', 'MNCR 1004 - Networking Lab', 40);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`bookingID`),
  ADD KEY `venueID` (`venueID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `venue`
--
ALTER TABLE `venue`
  ADD PRIMARY KEY (`venueID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `bookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`venueID`) REFERENCES `venue` (`venueID`),
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
