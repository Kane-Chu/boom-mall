package org.boom.mall.auth.service.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试首页controller
 *
 * @author kane
 */
@RestController
public class IndexController {

    @NacosValue(value = "${greet:hi}", autoRefreshed = true)
    private String greet;

    @GetMapping("/")
    public String index() {
        return greet;
    }
}