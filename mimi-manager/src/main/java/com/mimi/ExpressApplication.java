package com.mimi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Slf4j
@SpringBootApplication
@EnableOpenApi
public class ExpressApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpressApplication.class,args);
        log.info("项目启动成功...");
    }
}
