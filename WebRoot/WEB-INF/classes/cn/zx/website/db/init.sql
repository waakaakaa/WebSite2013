DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS blog;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS weather;
CREATE TABLE `weather` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `humidity` tinyint(4) DEFAULT NULL,
  `temperature` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS weather_hz;
CREATE TABLE `weather_hz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `humidity` tinyint(4) DEFAULT NULL,
  `temperature` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `createDate` (`createDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS zju88_work_thread;
CREATE TABLE `zju88_work_thread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `href` varchar(100) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `collected` tinyint(1) NOT NULL DEFAULT 0,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `href` (`href`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS thesis_head;
CREATE TABLE `thesis_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classification` varchar(5) DEFAULT NULL,
  `serialnumber` varchar(5) DEFAULT NULL,
  `PersonalID` varchar(8) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `titletl` varchar(20) DEFAULT NULL,
  `Etitle` varchar(50) DEFAULT NULL,
  `Etitletl` varchar(50) DEFAULT NULL,
  `author` varchar(3) DEFAULT NULL,
  `degree` varchar(2) DEFAULT NULL,
  `supervisor` varchar(3) DEFAULT NULL,
  `major` varchar(10) DEFAULT NULL,
  `researchdm` varchar(10) DEFAULT NULL,
  `institute` varchar(10) DEFAULT NULL,
  `submitdate` varchar(11) DEFAULT NULL,
  `defenddate` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS thesis_body;
DROP TABLE IF EXISTS thesis_img;
DROP TABLE IF EXISTS thesis_ref;