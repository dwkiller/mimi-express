package com.mimi.express.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.common.superpackage.base.BaseEntity;
import com.mimi.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description 消息模板
 * @Author RuKai
 * @Date 2023/11/9 15:40
 **/

@Data
@TableName(value = "t_notice_temp")
public class NoticeTemp extends TenantEntity implements Serializable {


	/**
	 * 模板id
	 */
	@Schema(name = "模板id")
	@NotBlank(message = "模板id不能为空")
	private String templateId;

	/**
	 * 配置路径url
	 */
	@Schema(name = "配置路径url")
	private String url;

	@Schema(name = "发送点")
	private String sendPoint;
}
