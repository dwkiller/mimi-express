package com.mimi.express.controller;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.config.NoticeTemp;
import com.mimi.core.express.service.MsgVariableService;
import com.mimi.core.express.service.NoticeTempService;
import com.mimi.core.express.type.MsgSendPoint;
import com.mimi.core.message.vo.SendPointVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    private MsgVariableService msgVariableService;

    @GetMapping("getAllSendPoint")
    public R<List<SendPointVo>> getAllSendPoint(){
        List<SendPointVo> rs = new ArrayList<>();
        for(MsgSendPoint msgSendPoint:MsgSendPoint.values()) {
            SendPointVo sendPointVo = new SendPointVo();
            sendPointVo.setCode(msgSendPoint.getCode());
            sendPointVo.setName(msgSendPoint.getName()+"("+msgSendPoint.getSender()+")");
            sendPointVo.setSender(msgSendPoint.getSender());
            rs.add(sendPointVo);
        }
        return R.success(rs);
    }

    @GetMapping("findByPoint")
    public R<NoticeTemp> findByPoint(String point){
        NoticeTemp noticeTemp = superService.findByPoint(point);
        if(noticeTemp!=null){
            noticeTemp.setVariableList(msgVariableService.findByTemplateId(noticeTemp.getTemplateId()));
        }
        return R.success(noticeTemp);
    }

}
