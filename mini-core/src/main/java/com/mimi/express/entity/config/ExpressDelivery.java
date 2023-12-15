package com.mimi.express.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.common.superpackage.base.BaseEntity;
import com.mimi.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 快递公司
 * @Author RuKai
 * @Date 2023/11/9 15:22
 **/
@Data
@TableName(value = "t_express_delivery")
public class ExpressDelivery extends TenantEntity implements Serializable {


	/**
	 * 快递公司名称
	 */
	@Schema(name = "快递公司名称")
	private String name;

	/**
	 * 快递公司地址
	 */
	@Schema(name = "快递公司地址")
	private String address;

	/**
	 * 备注
	 */
	@Schema(name = "备注")
	private String note;
}
