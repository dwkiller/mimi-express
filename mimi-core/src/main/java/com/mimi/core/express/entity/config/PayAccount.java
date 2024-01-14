package com.mimi.core.express.entity.config;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mimi.core.common.superpackage.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 微信支付账号对象
 * @Author RuKai
 * @Date 2023/11/9 15:45
 **/
@Data
@TableName(value = "t_pay_account")
public class PayAccount extends TenantEntity implements Serializable {

	@Schema(name = "支付账号名称")
	private String name;

	@Schema(name = "应用id")
	private String appId;

	@Schema(name = "商户id")
	private String mchId;

	@Schema(name = "应用key")
	private String appKey;

	@Schema(name = "密钥文件")
	private String file;
}
