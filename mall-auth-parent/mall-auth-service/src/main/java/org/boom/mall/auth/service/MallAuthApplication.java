package org.boom.mall.auth.service;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * mall-auth 启动类
 *
 * @author kane
 */
@SpringBootApplication
@NacosPropertySource(dataId = "auth", autoRefreshed = true)
public class MallAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallAuthApplication.class, args);
    }
}