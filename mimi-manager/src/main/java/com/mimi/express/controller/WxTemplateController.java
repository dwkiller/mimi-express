package com.mimi.express.controller;

import com.mimi.common.R;
import com.mimi.common.exception.MimiException;
import com.mimi.common.util.UserInfoUtil;
import com.mimi.express.entity.config.PublicAccount;
import com.mimi.express.service.PublicAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


/**
 * <p>
 * 微信模板管理
 * </p>
 *
 * @author 微信模板管理
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "微信模板管理")
@RestController
@RequestMapping("/wxTemplate")
public class WxTemplateController {

    @Autowired
    private PublicAccountService publicAccountService;

    @Autowired
    private UserInfoUtil userInfoUtil;

    /**
     * 获取所有的消息模板
     */
    @Operation(summary = "获取所有的消息模板")
    @GetMapping("/getAllMsgTemplate")
    public R<List<WxMpTemplate>> getAllMsgTemplate(
            //@RequestParam(name = "schoolId") String schoolId
    ) throws Exception {
        String schoolId = userInfoUtil.getSchoolId();
        PublicAccount bySchoolId = publicAccountService.getBySchoolId(schoolId);
        if (null == bySchoolId){
           throw new MimiException("该学校未配置公众号");
        }
        String appId = bySchoolId.getAppId();
        String secret = bySchoolId.getAppSecret();
        // TODO 此处需要根据当前登录用户来匹配配置信息的逻辑
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();
//        wxStorage.setAppId("wx89a0fa9b59731bed");
//        wxStorage.setSecret("2aafba733dec682593304d0a11d3cf84");
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        try {
            return R.success(wxMpService.getTemplateMsgService().getAllPrivateTemplate());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
