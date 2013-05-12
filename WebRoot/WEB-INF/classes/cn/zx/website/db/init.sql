DROP TABLE IF EXISTS user;

CREATE TABLE `user` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`email` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `blog` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`createDate` datetime NOT NULL,
	`content` varchar(500) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;