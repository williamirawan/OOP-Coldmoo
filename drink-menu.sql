-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2022 at 04:47 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `drink-menu`
--

-- --------------------------------------------------------

--
-- Table structure for table `processed drink`
--

CREATE TABLE `processed drink` (
  `DrinkId` char(5) NOT NULL,
  `DrinkName` varchar(100) NOT NULL,
  `DrinkPrice` int(11) NOT NULL,
  `ExpiredDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `processed drink`
--

INSERT INTO `processed drink` (`DrinkId`, `DrinkName`, `DrinkPrice`, `ExpiredDate`) VALUES
('DF001', 'Orange Juice', 10000, '2022-06-30'),
('DF002', 'Black Coffee', 30000, '2022-06-29'),
('DF003', 'Coca-cola', 7000, '2022-07-20'),
('DF004', 'Sprite', 9000, '2022-07-20'),
('DF005', 'Nut Milks', 20000, '2022-06-29'),
('DF006', 'Protein Shakes', 25000, '2022-06-29');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `TransactionId` char(5) NOT NULL,
  `DrinkId` char(5) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`TransactionId`, `DrinkId`, `Quantity`) VALUES
('TR937', 'DF001', 3),
('TR521', 'UF002', 3),
('TR091', 'DF002', 2);

-- --------------------------------------------------------

--
-- Table structure for table `unprocessed drink`
--

CREATE TABLE `unprocessed drink` (
  `DrinkId` char(5) NOT NULL,
  `DrinkName` varchar(100) NOT NULL,
  `DrinkPrice` int(11) NOT NULL,
  `DrinkWeight` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `unprocessed drink`
--

INSERT INTO `unprocessed drink` (`DrinkId`, `DrinkName`, `DrinkPrice`, `DrinkWeight`) VALUES
('UF001', 'Green Iced Tea', 30000, 200),
('UF002', 'Mineral Water', 10000, 100),
('UF003', 'Herb Infusions', 21000, 250),
('UF004', 'Fruit Infusions', 21000, 180),
('UF005', 'Mint Lemonade', 15000, 100),
('UF006', 'Coconut Water', 12000, 320);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
