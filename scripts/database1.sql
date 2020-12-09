CREATE TABLE `database1`.`tb_customer` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `create_date` timestamp NOT NULL,
  `last_update` timestamp NULL,
  PRIMARY KEY (`customer_id`)
);

CREATE TABLE `database1`.`tb_company` (
  `company_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `create_date` timestamp NOT NULL,
  `last_update` timestamp NULL,
  PRIMARY KEY (`company_id`)
);

CREATE TABLE `database1`.`tb_company_customer` (
  `company_id` BIGINT NOT NULL,
  `customer_id` BIGINT NOT NULL,
  PRIMARY KEY (`company_id`, `customer_id`),
  INDEX `fk_tb_comp_cust_customer_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_tb_comp_cust_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `database1`.`tb_company` (`company_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_comp_cust_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `database1`.`tb_customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
