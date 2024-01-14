package com.mimi.core.express.entity.config;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 区域对象
 * @Author RuKai
 * @Date 2023/11/9 14:16
 **/
@Data
@TableName(value = "t_area")
public class Area extends TenantEntity implements Serializable {


	/**
	 * 区域名称
	 */
	@Schema(name = "区域名称")
	private String name;

	/**
	 * 区域地址
	 */
	@Schema(name = "区域地址")
	private String address;

	/**
	 * 备注
	 */
	@Schema(name = "备注")
	private String note;

}
