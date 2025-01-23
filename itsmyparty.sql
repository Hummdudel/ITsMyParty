-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Erstellungszeit: 23. Jan 2025 um 11:21
-- Server-Version: 10.4.28-MariaDB
-- PHP-Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `itsmyparty`
--
CREATE DATABASE IF NOT EXISTS `itsmyparty` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `itsmyparty`;

DELIMITER $$
--
-- Prozeduren
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `Check_ArtistExists` (IN `p_name` VARCHAR(255) CHARSET utf8)   BEGIN
SELECT * FROM artist WHERE name = p_name;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Check_TrackExists` (IN `p_name` VARCHAR(255) CHARSET utf8)   BEGIN
SELECT * FROM track WHERE name = p_name;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Create_Artist` (IN `p_name` VARCHAR(255) CHARSET utf8)   BEGIN
INSERT INTO artist (name) VALUES (p_name);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Create_Playlist` (IN `p_name` VARCHAR(255) CHARSET utf8)   BEGIN
INSERT INTO playlist (name) VALUES (p_name);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Create_Playlist_Entry` (IN `p_track_no` INT, IN `p_playlist_no` INT)   BEGIN
INSERT INTO list_details (track_no, playlist_no, priority) VALUES (p_track_no, p_playlist_no, 0);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Create_Track` (IN `p_name` VARCHAR(255) CHARSET utf8, IN `p_artist` VARCHAR(255) CHARSET utf8, IN `p_duration` VARCHAR(255) CHARSET utf8, IN `p_seconds` INT UNSIGNED)   BEGIN
INSERT INTO track (name, artist_no, duration, seconds) VALUES (p_name, (SELECT id FROM artist WHERE name =  p_artist), p_duration, p_seconds);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Delete_Playlist` (IN `p_playlist_no` INT)   BEGIN
DELETE FROM list_details WHERE playlist_no = p_playlist_no;
DELETE FROM playlist WHERE id = p_playlist_no;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Delete_Playlist_Entry` (IN `p_track_no` INT, IN `p_playlist_no` INT)   BEGIN
DELETE FROM list_details WHERE track_no = p_track_no AND playlist_no = p_playlist_no;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Read_All_Playlists` ()   BEGIN
SELECT * FROM playlist;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Read_Playlist` (IN `p_name` VARCHAR(255) CHARSET utf8)   BEGIN
SELECT * FROM playlist WHERE name = p_name;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Read_Playlist_Tracks` (IN `p_playlist_no` INT)   BEGIN
SELECT track.name as track, artist.name as artist, track.duration as duration, priority FROM list_details INNER JOIN track ON track.id = track_no INNER JOIN artist ON artist.id = track.artist_no WHERE playlist_no = p_playlist_no ORDER BY 4 DESC, 1, 2;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Read_Track_ID` (IN `p_name` VARCHAR(255) CHARSET utf8)   BEGIN
SELECT id FROM track WHERE name = p_name;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Update_Priority` (IN `p_priority` INT, IN `p_track_no` INT, IN `p_playlist_no` INT)   BEGIN
UPDATE list_details SET priority = p_priority WHERE track_no = p_track_no AND playlist_no = p_playlist_no;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `artist`
--

CREATE TABLE `artist` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `artist`
--

INSERT INTO `artist` (`id`, `name`) VALUES
(1, 'Tina Turner'),
(2, 'Helge Schneider'),
(3, 'Elton John'),
(4, 'Visions of Atlantis'),
(5, 'Live'),
(6, 'Stevie Wonder'),
(7, 'Earth, Wind & Fire'),
(8, 'Bomfunk MC’s'),
(9, 'Imagine Dragons'),
(10, 'Dua Lipa'),
(11, 'Wir sind Helden'),
(12, 'Tocotronic'),
(13, 'Torfrock'),
(14, 'Coldplay'),
(15, 'Billy Joel'),
(16, 'Kissin\' Dynamite'),
(17, 'AC/DC'),
(18, 'Iron Maiden'),
(19, 'Metallica'),
(20, 'Europe'),
(21, 'REO Speedwagon'),
(22, 'New Model Army'),
(23, 'Huey Lewis and the News'),
(24, 'Electric Light Orchestra'),
(25, 'Creedence Clearwater Revival'),
(26, 'Saxon'),
(27, 'Aerosmith'),
(28, 'Billy Idol'),
(29, 'The Doors'),
(30, 'Jethro Tull'),
(31, 'The Cure'),
(32, 'Depeche Mode'),
(33, 'Green Day'),
(34, 'The Jinxs');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `list_details`
--

CREATE TABLE `list_details` (
  `track_no` int(11) NOT NULL,
  `playlist_no` int(11) NOT NULL,
  `priority` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `list_details`
--

INSERT INTO `list_details` (`track_no`, `playlist_no`, `priority`) VALUES
(1, 1, 0),
(3, 1, 0),
(4, 2, 0),
(5, 2, 0),
(6, 2, 0),
(7, 2, 0),
(8, 2, 0),
(9, 2, 0),
(2, 1, 100),
(16, 2, 0),
(17, 2, 0),
(18, 6, 0),
(19, 6, 0),
(20, 6, 0),
(21, 6, 0),
(22, 6, 0),
(23, 6, 0),
(3, 6, 100),
(24, 6, 0),
(25, 6, 0),
(26, 6, 0),
(27, 6, 0),
(28, 6, 0),
(29, 6, 0),
(5, 6, 0),
(30, 6, 0),
(31, 6, 0),
(32, 6, 0),
(33, 6, 0),
(34, 6, 0),
(35, 6, 0),
(36, 6, 0),
(37, 6, 0),
(38, 6, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `playlist`
--

CREATE TABLE `playlist` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `playlist`
--

INSERT INTO `playlist` (`id`, `name`) VALUES
(1, 'Testliste'),
(2, 'Partyliste 2025'),
(6, 'Lars’ Rockliste');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `track`
--

CREATE TABLE `track` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `artist_no` int(11) NOT NULL,
  `duration` varchar(255) NOT NULL,
  `seconds` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `track`
--

INSERT INTO `track` (`id`, `name`, `artist_no`, `duration`, `seconds`) VALUES
(1, 'Addicted to Love (live at Camden Palace)', 1, '05:22', 322),
(2, 'Käsebrot', 2, '03:16', 196),
(3, 'Saturday Night’s Alright (for Fighting)', 3, '04:39', 279),
(4, 'Melancholy Angel', 4, '03:55', 235),
(5, 'Lightning Crashes', 5, '05:26', 326),
(6, 'For Once In My Life', 6, '02:48', 168),
(7, 'September', 7, '03:37', 217),
(8, 'Freestyler (album version)', 8, '05:50', 350),
(9, 'Crocodile Rock', 3, '03:56', 236),
(10, 'Bad Liar', 9, '04:20', 260),
(11, 'Houdini (London Sessions)', 10, '03:30', 210),
(12, 'Müssen nur wollen', 11, '03:35', 215),
(13, 'Die Welt kann mich nicht mehr verstehen', 12, '01:38', 98),
(14, 'Rock \'n\' Roll Ruine', 13, '03:38', 218),
(15, 'Beinhart', 13, '03:17', 197),
(16, 'The Scientist', 14, '05:11', 311),
(17, 'You’re My Home', 15, '03:14', 194),
(18, 'Cadillac Maniac', 16, '03:58', 238),
(19, 'Burnin’ Alive', 17, '05:50', 350),
(20, 'Run to the Hills', 18, '03:54', 234),
(21, 'Sad but True', 19, '05:25', 325),
(22, 'Rock the Night', 20, '04:40', 280),
(23, 'Don\'t Let Him Go', 21, '03:47', 227),
(24, 'Addicted to Love (live)', 1, '05:22', 322),
(25, 'Stormclouds', 22, '04:11', 251),
(26, 'The Heart of Rock & Roll', 23, '05:50', 350),
(27, 'Confusion', 24, '03:42', 222),
(28, 'Fortunate Son', 25, '02:20', 140),
(29, 'Solid Ball of Rock', 26, '04:40', 280),
(30, 'Janie’s Got a Gun', 27, '04:32', 272),
(31, 'Blonde Over Blue', 15, '04:57', 297),
(32, 'White Wedding, Part 1', 28, '04:12', 252),
(33, 'When the Music’s Over', 29, '10:57', 657),
(34, 'Cross‐Eyed Mary', 30, '04:12', 252),
(35, 'A Forest', 31, '05:52', 352),
(36, 'Never Let Me Down Again', 32, '04:47', 287),
(37, 'Wake Me Up When September Ends', 33, '04:46', 286),
(38, 'Generation', 34, '02:50', 170);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `artist`
--
ALTER TABLE `artist`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `list_details`
--
ALTER TABLE `list_details`
  ADD KEY `list_details_fk0` (`track_no`),
  ADD KEY `list_details_fk1` (`playlist_no`);

--
-- Indizes für die Tabelle `playlist`
--
ALTER TABLE `playlist`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indizes für die Tabelle `track`
--
ALTER TABLE `track`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `track_fk0` (`artist_no`) USING BTREE;

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `artist`
--
ALTER TABLE `artist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT für Tabelle `playlist`
--
ALTER TABLE `playlist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT für Tabelle `track`
--
ALTER TABLE `track`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `list_details`
--
ALTER TABLE `list_details`
  ADD CONSTRAINT `list_details_fk0` FOREIGN KEY (`track_no`) REFERENCES `track` (`id`),
  ADD CONSTRAINT `list_details_fk1` FOREIGN KEY (`playlist_no`) REFERENCES `playlist` (`id`);

--
-- Constraints der Tabelle `track`
--
ALTER TABLE `track`
  ADD CONSTRAINT `track_fk0` FOREIGN KEY (`artist_no`) REFERENCES `artist` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
