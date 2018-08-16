-- 食品下单表新增实际下单量
ALTER TABLE `pls_order_food` ADD COLUMN `actual_number` DECIMAL(10,2) DEFAULT '0.00' NULL COMMENT '实际配送量（以斤进行计算）' AFTER `number`,    CHANGE `number` `number` DECIMAL(10,2) DEFAULT '0.00' NULL  COMMENT '数量（以斤进行计算）';

