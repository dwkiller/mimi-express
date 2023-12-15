package com.mimi.express.entity.receive;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
	private Double price;

	@Schema(name = "备注")
	private String note;
}
