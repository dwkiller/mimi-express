package com.mimi.express.controller;


import cn.hutool.core.io.FileUtil;
import com.mimi.common.R;
import com.mimi.common.superpackage.controller.SuperController;
import com.mimi.express.entity.config.PayAccount;
import com.mimi.express.service.PayAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * <p>
 * 支付账号管理
 * </p>
 *
 * @author 茹凯
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "支付账号管理")
@RestController
@RequestMapping("/payAccount")
public class PayAccountController extends SuperController<PayAccountService, PayAccount> {

    @Value("${kd.pay.fileroot}")
    private String fileRoot;

    @PostMapping("/upload")
    public R<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String oname = file.getOriginalFilename();
        oname = UUID.randomUUID().toString()+oname.substring(oname.lastIndexOf("."));
        String path = fileRoot+ File.separator+oname;
        FileUtil.mkdir(fileRoot);
        File filePath = new File(path);
        file.transferTo(filePath);
        return R.success(oname);
    }


}
