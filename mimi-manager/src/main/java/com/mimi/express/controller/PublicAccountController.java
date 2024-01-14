package com.mimi.express.controller;


import cn.hutool.core.io.FileUtil;
import com.mimi.core.common.R;
import com.mimi.core.common.superpackage.controller.SuperController;
import com.mimi.core.express.entity.config.PublicAccount;
import com.mimi.core.express.service.PublicAccountService;
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


/**
 * <p>
 * 公众号管理
 * </p>
 *
 * @author 茹凯
 * @since 2023/10/31
 */
@Slf4j
@Tag(name = "公众号管理")
@RestController
@RequestMapping("/publicAccount")
public class PublicAccountController extends SuperController<PublicAccountService, PublicAccount> {

    @Value("${kd.public.fileroot}")
    private String fileRoot;

    @PostMapping("/upload")
    public R<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String path = fileRoot+ File.separator+file.getOriginalFilename();
        FileUtil.mkdir(fileRoot);
        File filePath = new File(path);
        file.transferTo(filePath);
        return R.success(file.getOriginalFilename());
    }
}
