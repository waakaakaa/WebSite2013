DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS blog;
DROP TABLE IF EXISTS weather;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `weather` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `humidity` tinyint(4) DEFAULT NULL,
  `temperature` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8