package com.mimi.core.express.entity.receive;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/14 10:07
 **/
@Data
@TableName(value = "t_pricing")
public class Pricing extends TenantEntity {

	@Schema(name = "重量")
	private Double weight;

	@Schema(name = "价格")
	private BigDecimal price;

	@Schema(name = "折扣")
	private BigDecimal discount;

	@Schema(name = "备注")
	private String note;
}
