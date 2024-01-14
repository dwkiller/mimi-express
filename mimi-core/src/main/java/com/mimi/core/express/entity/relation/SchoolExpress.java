package com.mimi.core.express.entity.relation;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 学校快递公司关联对象
 * @Author RuKai
 * @Date 2023/11/9 15:45
 **/
@Data
@TableName(value = "r_school_express")
public class SchoolExpress implements Serializable {

	@Schema(name = "学校id")
	private String schoolId;

	@Schema(name = "快递公司id")
	private String expressId;
}
