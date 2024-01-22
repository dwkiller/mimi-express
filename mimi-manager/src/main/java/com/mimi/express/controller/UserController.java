package com.mimi.express.controller;

import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.user.User;
import com.mimi.core.express.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController extends SuperController<UserService, User> {


    @GetMapping("/findByMobileLast4")
    public R<List<User>> findByMobileLast4(String mobile){
        return R.success(superService.findByMobileLast4(mobile));
    }

}
