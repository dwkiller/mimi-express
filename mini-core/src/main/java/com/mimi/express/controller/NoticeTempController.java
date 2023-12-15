package com.mimi.express.controller;

import com.mimi.common.superpackage.controller.SuperController;
import com.mimi.express.entity.config.NoticeTemp;
import com.mimi.express.service.NoticeTempService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 消息模板配置管理
 * </p>
 *
 * @author 微信模板管理
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "消息模板配置管理")
@RestController
@RequestMapping("/notice")
public class NoticeTempController extends SuperController<NoticeTempService, NoticeTemp> {

}
