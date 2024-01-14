package com.mimi.core.express.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description 学校对象
 * @Author RuKai
 * @Date 2023/11/9 15:45
 **/
@Data
@TableName(value = "t_school")
public class School extends BaseEntity implements Serializable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	@Schema(name = "学校名称")
	private String name;

	@Schema(name = "学校地址")
	private String address;

	@Schema(name = "备注")
	private String note;

	@Schema(name = "管理员名称")
	@TableField(exist = false)
	@NotBlank(message = "管理员名称不能为空")
	private String adminName;

	@Schema(name = "管理员密码")
	@TableField(exist = false)
	@NotBlank(message = "管理员密码不能为空")
	private String adminPassword;

	@Schema(name = "经度")
	private Double longitude;

	@Schema(name = "纬度")
	private Double latitude;

}
