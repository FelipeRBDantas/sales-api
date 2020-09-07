CREATE TABLE IF NOT EXISTS `categories` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    `description` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;