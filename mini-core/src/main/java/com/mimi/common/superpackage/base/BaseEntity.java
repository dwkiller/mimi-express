package com.mimi.common.superpackage.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 抽象实体
 *
 * @author lengleng
 * @date 2021/8/9
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {


	/**
	 * 区域id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;



	/**
	 * 创建者
	 */
	@Schema(description = "创建人")
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新者
	 */
	@Schema(description = "更新人")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Schema(description = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

}
