package com.mimi.core.express.entity.config;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 微信公众号对象
 * @Author RuKai
 * @Date 2023/11/9 15:45
 **/
@Data
@TableName(value = "t_public_account")
public class PublicAccount extends TenantEntity implements Serializable {

	@Schema(name = "公众号名称")
	private String name;

	@Schema(name = "应用id")
	private String appId;

	@Schema(name = "应用秘钥")
	private String appSecret;

	@Schema(name = "公众号文件")
	private String file;
}
