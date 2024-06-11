package com.mimi.express.controller;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.user.User;
import com.mimi.core.express.service.UserService;
import com.mimi.core.message.MessageService;
import com.mimi.express.vo.SendMsgVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController extends SuperController<UserService, User> {

    @Autowired
    private MessageService messageService;

    @GetMapping("/findByMobileLast4")
    public R<List<User>> findByMobileLast4(String mobile){
        return R.success(superService.findByMobileLast4(mobile));
    }

    @PostMapping("/sendMsgToAllUser")
    public R<List<String>> sendMsgToAllUser(@RequestBody SendMsgVo sendMsgVo){
        return R.success(messageService.sendAllUser(sendMsgVo.getTemplateId(),sendMsgVo.getSendParam()));
    }
}
