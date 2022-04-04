CREATE SCHEMA `sprin_demo` ;

CREATE TABLE `sprin_demo`.`animals` (
                                        `id` INT NOT NULL AUTO_INCREMENT,
                                        `kind` VARCHAR(45) NOT NULL,
                                        `name` VARCHAR(45) NOT NULL,
                                        `age` VARCHAR(45) NOT NULL,
                                        `owner_id` VARCHAR(45) NOT NULL,
                                        PRIMARY KEY (`id`));

INSERT INTO `sprin_demo`.`animals` (`kind`, `name`, `age`, `owner_id`) VALUES ('cat', 'Mursik', '2', '1');
INSERT INTO `sprin_demo`.`animals` (`kind`, `name`, `age`, `owner_id`) VALUES ('dog', 'Reks', '3', '2');
INSERT INTO `sprin_demo`.`animals` (`kind`, `name`, `age`, `owner_id`) VALUES ('Fish', 'Myl', '1', '1');
