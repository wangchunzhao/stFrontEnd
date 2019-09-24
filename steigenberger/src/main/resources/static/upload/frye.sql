-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bohemian
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bohemian` ;

-- -----------------------------------------------------
-- Schema bohemian
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bohemian` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;
-- -----------------------------------------------------
-- Schema kost
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `kost` ;

-- -----------------------------------------------------
-- Schema kost
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kost` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;
USE `bohemian` ;

-- -----------------------------------------------------
-- Table `bohemian`.`b_users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_users` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_users` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_identity` VARCHAR(45) NOT NULL COMMENT 'the user id of domain in loccal LDAP',
  `user_mail` VARCHAR(45) NOT NULL,
  `name` TEXT NULL,
  `isActive` TINYINT(1) ZEROFILL NOT NULL COMMENT 'boolean type, mean if user is able to use system',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `idb_Users_UNIQUE` ON `bohemian`.`b_users` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `user_mail_UNIQUE` ON `bohemian`.`b_users` (`user_mail` ASC) VISIBLE;

CREATE UNIQUE INDEX `user_identity_UNIQUE` ON `bohemian`.`b_users` (`user_identity` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_roles` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_roles` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` TEXT NOT NULL,
  `isActive` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`b_roles` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_application_of_rolechange`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_application_of_rolechange` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_application_of_rolechange` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `creator` TEXT NOT NULL COMMENT 'domain id of create user',
  `create_time` DATETIME NOT NULL COMMENT 'create time of the application',
  `approver_required` TEXT NOT NULL COMMENT 'the domain id of approver ',
  `approver_fact` TEXT NULL DEFAULT NULL,
  `approval_time` DATETIME NULL DEFAULT NULL,
  `b_users_id` INT(10) UNSIGNED NOT NULL,
  `isActive` TINYINT(1) NOT NULL DEFAULT 1,
  `attached_code` CHAR(32) NULL,
  `b_roles_id` INT ZEROFILL UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_b_applicaation_of_rolechange_b_users1`
    FOREIGN KEY (`b_users_id`)
    REFERENCES `bohemian`.`b_users` (`id`),
  CONSTRAINT `fk_b_application_of_rolechange_b_roles1`
    FOREIGN KEY (`b_roles_id`)
    REFERENCES `bohemian`.`b_roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`b_application_of_rolechange` (`id` ASC) VISIBLE;

CREATE INDEX `fk_b_applicaation_of_rolechange_b_users1_idx` ON `bohemian`.`b_application_of_rolechange` (`b_users_id` ASC) VISIBLE;

CREATE INDEX `fk_b_application_of_rolechange_b_roles1_idx` ON `bohemian`.`b_application_of_rolechange` (`b_roles_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_notify_infor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_notify_infor` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_notify_infor` (
  `id` BIGINT(20) UNSIGNED NOT NULL,
  `hasSend` TINYINT(1) UNSIGNED ZEROFILL NOT NULL,
  `msg_to` TEXT NOT NULL,
  `msg_from` TEXT NOT NULL,
  `message` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`b_notify_infor` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_operations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_operations` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_operations` (
  `id` CHAR(32) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`b_operations` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_operation2role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_operation2role` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_operation2role` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `opt_time` DATETIME NOT NULL,
  `isActive` TINYINT(1) NOT NULL,
  `b_operations_id` CHAR(4) NOT NULL,
  `b_roles_id` INT ZEROFILL UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_b_operation2role_b_operations1`
    FOREIGN KEY (`b_operations_id`)
    REFERENCES `bohemian`.`b_operations` (`id`),
  CONSTRAINT `fk_b_operation2role_b_roles1`
    FOREIGN KEY (`b_roles_id`)
    REFERENCES `bohemian`.`b_roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`b_operation2role` (`id` ASC) VISIBLE;

CREATE INDEX `fk_b_operation2role_b_operations1_idx` ON `bohemian`.`b_operation2role` (`b_operations_id` ASC) VISIBLE;

CREATE INDEX `fk_b_operation2role_b_roles1_idx` ON `bohemian`.`b_operation2role` (`b_roles_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_settings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_settings` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_settings` (
  `id` CHAR(32) NOT NULL,
  `s_value` TEXT NOT NULL,
  `enable_date` DATE NOT NULL,
  `comment` TEXT NULL DEFAULT NULL,
  `operater` TEXT NOT NULL,
  `opt_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`b_settings` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_order_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_order_type` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_order_type` (
  `id` CHAR(4) NOT NULL,
  `name` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_order_type` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_sales_office`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_sales_office` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_sales_office` (
  `code` CHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_sales_office` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_sales_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_sales_group` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_sales_group` (
  `code` CHAR(3) NOT NULL,
  `name` TEXT NOT NULL,
  `sap_sales_office_code` CHAR(4) NOT NULL,
  PRIMARY KEY (`code`),
  CONSTRAINT `fk_sap_sales_group_sap_sales_office1`
    FOREIGN KEY (`sap_sales_office_code`)
    REFERENCES `bohemian`.`sap_sales_office` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_sales_group` (`code` ASC) VISIBLE;

CREATE INDEX `fk_sap_sales_group_sap_sales_office1_idx` ON `bohemian`.`sap_sales_group` (`sap_sales_office_code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_update_his`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_update_his` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_update_his` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `interface_code` CHAR(4) NOT NULL,
  `update_date` DATE NOT NULL,
  `update_time` TIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_update_his` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_orders` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_orders` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sequence_number` CHAR(12) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `creator` VARCHAR(128) NOT NULL,
  `contract_number` CHAR(10) NULL DEFAULT NULL,
  `order_type` CHAR(4) NOT NULL,
  `origal_code` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_orders` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `number_UNIQUE` ON `kost`.`k_orders` (`sequence_number` ASC) VISIBLE;

CREATE UNIQUE INDEX `order_type_UNIQUE` ON `kost`.`k_orders` (`order_type` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_order_version`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_order_version` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_order_version` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `version` CHAR(2) NOT NULL,
  `status` TINYINT(2) NOT NULL,
  `last_opt_time` DATETIME NOT NULL,
  `k_orders_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_order_version_k_orders1`
    FOREIGN KEY (`k_orders_id`)
    REFERENCES `kost`.`k_orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_order_version` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `version_UNIQUE` ON `kost`.`k_order_version` (`version` ASC) VISIBLE;

CREATE INDEX `fk_k_order_version_k_orders1_idx` ON `kost`.`k_order_version` (`k_orders_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`k_form`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`k_form` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`k_form` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `seq_num` TINYINT NOT NULL,
  `create_time` DATETIME NOT NULL,
  `k_order_version_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_form_k_order_version1`
    FOREIGN KEY (`k_order_version_id`)
    REFERENCES `kost`.`k_order_version` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`k_form` (`id` ASC) VISIBLE;

CREATE INDEX `fk_k_form_k_order_version1_idx` ON `bohemian`.`k_form` (`k_order_version_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_materials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_materials` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_materials` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `k_form_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_materials_k_form1`
    FOREIGN KEY (`k_form_id`)
    REFERENCES `bohemian`.`k_form` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_materials` (`id` ASC) VISIBLE;

CREATE INDEX `fk_k_materials_k_form1_idx` ON `kost`.`k_materials` (`k_form_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`k_configurable`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`k_configurable` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`k_configurable` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `config_code` VARCHAR(45) NOT NULL,
  `k_materials_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_configurable_k_materials1`
    FOREIGN KEY (`k_materials_id`)
    REFERENCES `kost`.`k_materials` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`k_configurable` (`id` ASC) VISIBLE;

CREATE INDEX `fk_k_configurable_k_materials1_idx` ON `bohemian`.`k_configurable` (`k_materials_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`k_order_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`k_order_info` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`k_order_info` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`k_order_info` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`k_order_version`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`k_order_version` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`k_order_version` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `k_order_info_id` INT UNSIGNED NOT NULL,
  `k_order_version_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_order_version_k_order_info1`
    FOREIGN KEY (`k_order_info_id`)
    REFERENCES `bohemian`.`k_order_info` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_k_order_version_k_order_version1`
    FOREIGN KEY (`k_order_version_id`)
    REFERENCES `kost`.`k_order_version` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`k_order_version` (`id` ASC) VISIBLE;

CREATE INDEX `fk_k_order_version_k_order_info1_idx` ON `bohemian`.`k_order_version` (`k_order_info_id` ASC) VISIBLE;

CREATE INDEX `fk_k_order_version_k_order_version1_idx` ON `bohemian`.`k_order_version` (`k_order_version_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_customer_class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_customer_class` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_customer_class` (
  `code` CHAR(2) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_customer_class` (`code` ASC) INVISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_material_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_material_type` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_material_type` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `number_UNIQUE` ON `bohemian`.`sap_material_type` (`number` ASC) VISIBLE;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_material_type` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_unit_of_measurement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_unit_of_measurement` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_unit_of_measurement` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(3) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_unit_of_measurement` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_unit_of_measurement` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_materials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_materials` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_materials` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(18) NOT NULL,
  `Description` TEXT NULL,
  `is_configurable` TINYINT(1) NOT NULL,
  `moving_average_price` DECIMAL(13,2) NOT NULL,
  `transfer_price` DECIMAL(13,2) NOT NULL,
  `marketing_price` DECIMAL(13,2) NOT NULL,
  `opt_time` DATETIME NOT NULL,
  `clazz_code` VARCHAR(4) NULL,
  `sap_material_type_id` INT UNSIGNED NOT NULL,
  `sap_unit_of_measurement_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_sap_fert_materials_sap_material_type1`
    FOREIGN KEY (`sap_material_type_id`)
    REFERENCES `bohemian`.`sap_material_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sap_fert_materials_sap_unit_of_measurement1`
    FOREIGN KEY (`sap_unit_of_measurement_id`)
    REFERENCES `bohemian`.`sap_unit_of_measurement` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_materials` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_materials` (`number` ASC) VISIBLE;

CREATE INDEX `fk_sap_fert_materials_sap_material_type1_idx` ON `bohemian`.`sap_materials` (`sap_material_type_id` ASC) VISIBLE;

CREATE INDEX `fk_sap_fert_materials_sap_unit_of_measurement1_idx` ON `bohemian`.`sap_materials` (`sap_unit_of_measurement_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_clazz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_clazz` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_clazz` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(18) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_clazz` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `number_UNIQUE` ON `bohemian`.`sap_clazz` (`number` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_characteristic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_characteristic` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_characteristic` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(30) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_characteristic` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `number_UNIQUE` ON `bohemian`.`sap_characteristic` (`number` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_characteristic_value`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_characteristic_value` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_characteristic_value` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `description` TEXT NULL,
  `sap_characteristic_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_sap_characteristic_value_sap_characteristic1`
    FOREIGN KEY (`sap_characteristic_id`)
    REFERENCES `bohemian`.`sap_characteristic` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_characteristic_value` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `name_UNIQUE` ON `bohemian`.`sap_characteristic_value` (`name` ASC) VISIBLE;

CREATE INDEX `fk_sap_characteristic_value_sap_characteristic1_idx` ON `bohemian`.`sap_characteristic_value` (`sap_characteristic_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_clazz_and_chart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_clazz_and_chart` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_clazz_and_chart` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `sap_clazz_id` INT UNSIGNED NOT NULL,
  `sap_characteristic_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_sap_clazz_and_chart_sap_clazz1`
    FOREIGN KEY (`sap_clazz_id`)
    REFERENCES `bohemian`.`sap_clazz` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sap_clazz_and_chart_sap_characteristic1`
    FOREIGN KEY (`sap_characteristic_id`)
    REFERENCES `bohemian`.`sap_characteristic` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_clazz_and_chart` (`id` ASC) VISIBLE;

CREATE INDEX `fk_sap_clazz_and_chart_sap_clazz1_idx` ON `bohemian`.`sap_clazz_and_chart` (`sap_clazz_id` ASC) VISIBLE;

CREATE INDEX `fk_sap_clazz_and_chart_sap_characteristic1_idx` ON `bohemian`.`sap_clazz_and_chart` (`sap_characteristic_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_account_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_account_group` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_account_group` (
  `code` CHAR(4) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_account_group` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_industry_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_industry_code` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_industry_code` (
  `code` CHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_industry_code` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_customer` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_customer` (
  `code` VARCHAR(16) NOT NULL,
  `change_date` DATE NOT NULL,
  `name` TEXT NOT NULL,
  `address` TEXT NULL,
  `sap_account_group_id` INT UNSIGNED NOT NULL,
  `sap_customer_class_code` CHAR(2) NOT NULL,
  `sap_account_group_code` CHAR(4) NOT NULL,
  `sap_customer_level_code` CHAR(1) NOT NULL,
  PRIMARY KEY (`code`),
  CONSTRAINT `fk_sap_customer_sap_customer_class1`
    FOREIGN KEY (`sap_customer_class_code`)
    REFERENCES `bohemian`.`sap_customer_class` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sap_customer_sap_account_group1`
    FOREIGN KEY (`sap_account_group_code`)
    REFERENCES `bohemian`.`sap_account_group` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sap_customer_sap_customer_level1`
    FOREIGN KEY (`sap_customer_level_code`)
    REFERENCES `bohemian`.`sap_industry_code` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `number_UNIQUE` ON `bohemian`.`sap_customer` (`code` ASC) VISIBLE;

CREATE INDEX `fk_sap_customer_sap_customer_class1_idx` ON `bohemian`.`sap_customer` (`sap_customer_class_code` ASC) VISIBLE;

CREATE INDEX `fk_sap_customer_sap_account_group1_idx` ON `bohemian`.`sap_customer` (`sap_account_group_code` ASC) VISIBLE;

CREATE INDEX `fk_sap_customer_sap_customer_level1_idx` ON `bohemian`.`sap_customer` (`sap_customer_level_code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_currency`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_currency` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_currency` (
  `code` CHAR(3) NOT NULL,
  `name` TEXT NOT NULL,
  `rate` FLOAT NOT NULL,
  `effective_date` DATE NULL,
  `is_reserved` TINYINT(1) NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_currency` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_sales_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_sales_type` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_sales_type` (
  `code` CHAR(2) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idsap_sales_type_UNIQUE` ON `bohemian`.`sap_sales_type` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_shipping_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_shipping_type` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_shipping_type` (
  `code` CHAR(2) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_shipping_type` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_industry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_industry` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_industry` (
  `code` VARCHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_industry` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_industry_and_customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_industry_and_customer` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_industry_and_customer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `sap_industry_code` VARCHAR(4) NOT NULL,
  `sap_customer_code` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_sap_industry_and_customer_sap_industry1`
    FOREIGN KEY (`sap_industry_code`)
    REFERENCES `bohemian`.`sap_industry` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sap_industry_and_customer_sap_customer1`
    FOREIGN KEY (`sap_customer_code`)
    REFERENCES `bohemian`.`sap_customer` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_industry_and_customer` (`id` ASC) VISIBLE;

CREATE INDEX `fk_sap_industry_and_customer_sap_industry1_idx` ON `bohemian`.`sap_industry_and_customer` (`sap_industry_code` ASC) VISIBLE;

CREATE INDEX `fk_sap_industry_and_customer_sap_customer1_idx` ON `bohemian`.`sap_industry_and_customer` (`sap_customer_code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_item_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_item_category` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_item_category` (
  `code` CHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  `is_reserved` TINYINT(1) ZEROFILL NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_item_category` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_item_category_plan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_item_category_plan` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_item_category_plan` (
  `code` CHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  `is_reserved` TINYINT(1) NOT NULL,
  `sap_item_category_code` CHAR(4) NOT NULL,
  PRIMARY KEY (`code`),
  CONSTRAINT `fk_sap_item_category_plan_sap_item_category1`
    FOREIGN KEY (`sap_item_category_code`)
    REFERENCES `bohemian`.`sap_item_category` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_item_category_plan` (`code` ASC) VISIBLE;

CREATE INDEX `fk_sap_item_category_plan_sap_item_category1_idx` ON `bohemian`.`sap_item_category_plan` (`sap_item_category_code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_price_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_price_type` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_price_type` (
  `code` CHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_price_type` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_materials_price`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_materials_price` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_materials_price` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(13,2) NOT NULL,
  `sap_materials_id` INT UNSIGNED NOT NULL,
  `sap_price_type_code` CHAR(4) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_sap_materials_price_sap_materials1`
    FOREIGN KEY (`sap_materials_id`)
    REFERENCES `bohemian`.`sap_materials` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sap_materials_price_sap_price_type1`
    FOREIGN KEY (`sap_price_type_code`)
    REFERENCES `bohemian`.`sap_price_type` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `bohemian`.`sap_materials_price` (`id` ASC) VISIBLE;

CREATE INDEX `fk_sap_materials_price_sap_materials1_idx` ON `bohemian`.`sap_materials_price` (`sap_materials_id` ASC) VISIBLE;

CREATE INDEX `fk_sap_materials_price_sap_price_type1_idx` ON `bohemian`.`sap_materials_price` (`sap_price_type_code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`sap_incoterns`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`sap_incoterns` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`sap_incoterns` (
  `code` CHAR(3) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`sap_incoterns` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_province`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_province` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_province` (
  `code` VARCHAR(6) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`b_province` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_city` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_city` (
  `code` VARCHAR(6) NOT NULL,
  `name` TEXT NOT NULL,
  `b_province_code` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`code`),
  CONSTRAINT `fk_b_city_b_province1`
    FOREIGN KEY (`b_province_code`)
    REFERENCES `bohemian`.`b_province` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`b_city` (`code` ASC) VISIBLE;

CREATE INDEX `fk_b_city_b_province1_idx` ON `bohemian`.`b_city` (`b_province_code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `bohemian`.`b_area`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bohemian`.`b_area` ;

CREATE TABLE IF NOT EXISTS `bohemian`.`b_area` (
  `code` VARCHAR(6) NOT NULL,
  `name` TEXT NOT NULL,
  `price` DECIMAL(13,2) NOT NULL,
  `b_city_code` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`code`),
  CONSTRAINT `fk_b_area_b_city1`
    FOREIGN KEY (`b_city_code`)
    REFERENCES `bohemian`.`b_city` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `bohemian`.`b_area` (`code` ASC) VISIBLE;

CREATE INDEX `fk_b_area_b_city1_idx` ON `bohemian`.`b_area` (`b_city_code` ASC) VISIBLE;

USE `kost` ;

-- -----------------------------------------------------
-- Table `kost`.`k_acceptance_approch`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_acceptance_approch` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_acceptance_approch` (
  `id` CHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_acceptance_approch` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_b2c`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_b2c` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_b2c` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `opterator` VARCHAR(128) NOT NULL,
  `opt_time` DATETIME NOT NULL,
  `statius` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_b2c` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_receive_confirm`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_receive_confirm` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_receive_confirm` (
  `id` CHAR(4) NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_receive_confirm` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_contract`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_contract` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_contract` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sequence_number` CHAR(12) NOT NULL,
  `PartyA_code` CHAR(10) NOT NULL,
  `PartyA_name` TEXT NOT NULL,
  `partyA_mail` TEXT NULL DEFAULT NULL,
  `amount_on_contract` DECIMAL(10,2) NOT NULL,
  `delivery_days_after_prepay` SMALLINT(6) NULL DEFAULT NULL,
  `client_name` TEXT NOT NULL,
  `install_location` TEXT NOT NULL,
  `quality_stand` VARCHAR(45) NULL DEFAULT NULL,
  `k_receive_confirm_id` CHAR(4) NULL DEFAULT NULL,
  `k_acceptance_approch_id` CHAR(4) NULL DEFAULT NULL,
  `settlement` TEXT NULL DEFAULT NULL,
  `paryA_address` TEXT NULL DEFAULT NULL,
  `invoice_address` TEXT NULL DEFAULT NULL,
  `broker` TEXT NULL DEFAULT NULL,
  `invoice_receiver` TEXT NULL DEFAULT NULL,
  `invoice_tel` TEXT NULL DEFAULT NULL,
  `invoice_post_code` CHAR(6) NULL DEFAULT NULL,
  `company_tel` TEXT NULL DEFAULT NULL,
  `bank_name` TEXT NULL DEFAULT NULL,
  `account_number` TEXT NULL DEFAULT NULL,
  `k_orders_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_contract_k_acceptance_approch1`
    FOREIGN KEY (`k_acceptance_approch_id`)
    REFERENCES `kost`.`k_acceptance_approch` (`id`),
  CONSTRAINT `fk_k_contract_k_receive_confirm1`
    FOREIGN KEY (`k_receive_confirm_id`)
    REFERENCES `kost`.`k_receive_confirm` (`id`),
  CONSTRAINT `fk_k_contract_k_orders1`
    FOREIGN KEY (`k_orders_id`)
    REFERENCES `kost`.`k_orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_contract` (`id` ASC) VISIBLE;

CREATE INDEX `fk_k_contract_k_receive_confirm1_idx` ON `kost`.`k_contract` (`k_receive_confirm_id` ASC) VISIBLE;

CREATE INDEX `fk_k_contract_k_acceptance_approch1_idx` ON `kost`.`k_contract` (`k_acceptance_approch_id` ASC) VISIBLE;

CREATE INDEX `fk_k_contract_k_orders1_idx` ON `kost`.`k_contract` (`k_orders_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_contact` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_contact` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `linkman` TEXT NOT NULL,
  `tel_number` TEXT NOT NULL,
  `id_number` VARCHAR(18) NULL DEFAULT NULL,
  `k_contract_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_contact_k_contract1`
    FOREIGN KEY (`k_contract_id`)
    REFERENCES `kost`.`k_contract` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_contact` (`id` ASC) VISIBLE;

CREATE INDEX `fk_k_contact_k_contract1_idx` ON `kost`.`k_contact` (`k_contract_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_engining`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_engining` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_engining` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `operator` VARCHAR(128) NOT NULL,
  `opt_time` DATETIME NOT NULL,
  `k_order_version_id` INT(10) UNSIGNED NOT NULL,
  `status` TINYINT(2) NOT NULL,
  `cost` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_engining_k_order_version1`
    FOREIGN KEY (`k_order_version_id`)
    REFERENCES `kost`.`k_order_version` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_engining` (`id` ASC) VISIBLE;

CREATE INDEX `fk_k_engining_k_order_version1_idx` ON `kost`.`k_engining` (`k_order_version_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_product_b2c`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_product_b2c` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_product_b2c` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `k_productions_id` INT(10) UNSIGNED NOT NULL,
  `k_b2c_id` INT(10) UNSIGNED NOT NULL,
  `cost` DECIMAL(10,2) NOT NULL,
  `comment` TEXT NULL DEFAULT NULL,
  `k_order_version_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_table1_k_b2c1`
    FOREIGN KEY (`k_b2c_id`)
    REFERENCES `kost`.`k_b2c` (`id`),
  CONSTRAINT `fk_k_product_b2c_k_order_version1`
    FOREIGN KEY (`k_order_version_id`)
    REFERENCES `kost`.`k_order_version` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_product_b2c` (`id` ASC) VISIBLE;

CREATE INDEX `fk_table1_k_b2c1_idx` ON `kost`.`k_product_b2c` (`k_b2c_id` ASC) VISIBLE;

CREATE INDEX `fk_k_product_b2c_k_order_version1_idx` ON `kost`.`k_product_b2c` (`k_order_version_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `kost`.`k_speical_order_application`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kost`.`k_speical_order_application` ;

CREATE TABLE IF NOT EXISTS `kost`.`k_speical_order_application` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `applyer` VARCHAR(128) NOT NULL,
  `approver` VARCHAR(128) NULL DEFAULT NULL,
  `apply_time` DATETIME NOT NULL,
  `approval_time` DATETIME NULL DEFAULT NULL,
  `k_order_version_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_k_speical_order_application_k_order_version1`
    FOREIGN KEY (`k_order_version_id`)
    REFERENCES `kost`.`k_order_version` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE UNIQUE INDEX `id_UNIQUE` ON `kost`.`k_speical_order_application` (`id` ASC) VISIBLE;

CREATE INDEX `fk_k_speical_order_application_k_order_version1_idx` ON `kost`.`k_speical_order_application` (`k_order_version_id` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_order_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_order_type` (`id`, `name`) VALUES ('Z001', 'dealer');
INSERT INTO `bohemian`.`sap_order_type` (`id`, `name`) VALUES ('Z011', 'poc');
INSERT INTO `bohemian`.`sap_order_type` (`id`, `name`) VALUES ('Z003', 'bulk on stock');

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_customer_class`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_customer_class` (`code`, `name`) VALUES ('01', 'key account');
INSERT INTO `bohemian`.`sap_customer_class` (`code`, `name`) VALUES ('02', 'dealer');

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_material_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_material_type` (`id`, `number`, `name`) VALUES (1, 'FERT', '成品');
INSERT INTO `bohemian`.`sap_material_type` (`id`, `number`, `name`) VALUES (2, 'HALB', '半成品');
INSERT INTO `bohemian`.`sap_material_type` (`id`, `number`, `name`) VALUES (3, 'ROH', '原材料');

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_unit_of_measurement`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (1, 'BOT', '瓶');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (2, 'EA', '个/卷/件');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (3, 'G', '克');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (4, 'KG', '公斤');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (5, 'TO', '吨');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (6, 'CM', '厘米');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (7, 'M', '米');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (8, 'KM', '千米');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (9, 'M2', '平方米');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (10, 'ML', '毫升');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (11, 'L', '升');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (12, 'M3', '立方米');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (13, 'ZA', '个/卷/件  小数点');
INSERT INTO `bohemian`.`sap_unit_of_measurement` (`id`, `code`, `name`) VALUES (14, 'Z1', '套');

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_account_group`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_account_group` (`code`, `description`) VALUES ('Z001', '开立集团内国内客户');
INSERT INTO `bohemian`.`sap_account_group` (`code`, `description`) VALUES ('Z002', '集团国外客户');
INSERT INTO `bohemian`.`sap_account_group` (`code`, `description`) VALUES ('Z003', '开立集团国内国外客户');
INSERT INTO `bohemian`.`sap_account_group` (`code`, `description`) VALUES ('Z004', '集团外国外客户');
INSERT INTO `bohemian`.`sap_account_group` (`code`, `description`) VALUES ('Z005', '海尔集团内国内客户');

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_industry_code`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_industry_code` (`code`, `name`) VALUES ('0001', 'MNC');
INSERT INTO `bohemian`.`sap_industry_code` (`code`, `name`) VALUES ('0002', 'Local top 100-国内连锁百强');
INSERT INTO `bohemian`.`sap_industry_code` (`code`, `name`) VALUES ('0003', 'Delaer(代理商)');
INSERT INTO `bohemian`.`sap_industry_code` (`code`, `name`) VALUES ('0004', 'Local others(本地其它)');
INSERT INTO `bohemian`.`sap_industry_code` (`code`, `name`) VALUES ('0005', 'Cold room(冷库)');
INSERT INTO `bohemian`.`sap_industry_code` (`code`, `name`) VALUES ('0006', 'Export(出口)');
INSERT INTO `bohemian`.`sap_industry_code` (`code`, `name`) VALUES ('0007', '便利店');

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_currency`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_currency` (`code`, `name`, `rate`, `effective_date`, `is_reserved`) VALUES ('CNY', '人民币', 1, NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_sales_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_sales_type` (`code`, `name`) VALUES ('10', '国内');
INSERT INTO `bohemian`.`sap_sales_type` (`code`, `name`) VALUES ('20', '出口');
INSERT INTO `bohemian`.`sap_sales_type` (`code`, `name`) VALUES ('30', '冷库');

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_shipping_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_shipping_type` (`code`, `name`) VALUES ('01', '非自提');
INSERT INTO `bohemian`.`sap_shipping_type` (`code`, `name`) VALUES ('05', '自提');

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_item_category`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_item_category` (`code`, `name`, `is_reserved`) VALUES ('R001', ' 替代', 1);
INSERT INTO `bohemian`.`sap_item_category` (`code`, `name`, `is_reserved`) VALUES ('S001', '标准', 0);
INSERT INTO `bohemian`.`sap_item_category` (`code`, `name`, `is_reserved`) VALUES ('S002', '退货', 0);
INSERT INTO `bohemian`.`sap_item_category` (`code`, `name`, `is_reserved`) VALUES ('S003', '免费', 0);
INSERT INTO `bohemian`.`sap_item_category` (`code`, `name`, `is_reserved`) VALUES ('S004', '取消', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `bohemian`.`sap_item_category_plan`
-- -----------------------------------------------------
START TRANSACTION;
USE `bohemian`;
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('R001', '退货', 1, 'R001');
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('S001', '物料需求计划', 0, 'S001');
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('S002', '调发', 0, 'S001');
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('S003', '消化', 0, 'S001');
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('T001', '退货', 0, 'S002');
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('M001', '物料需求计划', 0, 'S003');
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('M002', '调发', 0, 'S003');
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('M003', '消化', 0, 'S003');
INSERT INTO `bohemian`.`sap_item_category_plan` (`code`, `name`, `is_reserved`, `sap_item_category_code`) VALUES ('C001', '取消', 0, 'S004');

COMMIT;

