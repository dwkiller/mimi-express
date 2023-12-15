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
 * @Description 楼栋表
 * @Author RuKai
 * @Date 2023/11/9 14:43
 **/
@Data
@TableName(value = "t_building")
public class Building extends TenantEntity implements Serializable {


	/**
	 * 楼栋名称
	 */
	@Schema(name = "楼栋名称")
	private String name;

	/**
	 * 楼栋地址
	 */
	@Schema(name = "楼栋地址")
	private String address;

	/**
	 * 备注
	 */
	@Schema(name = "备注")
	private String note;

	/**
	 * 区域id
	 *
	 */
	@Schema(name = "区域id")
	private String areaId;


}
