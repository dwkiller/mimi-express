package com.mimi.core.express.entity.receive;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description TODO
 * @Author RuKai
 * @Date 2023/11/14 10:10
 **/
@Data
@TableName(value = "t_insurance")
public class Insurance extends TenantEntity {

	@Schema(name = "价格")
	private Double price;

	@Schema(name = "备注")
	private String note;
}
