package org.boom.mall.auth.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试首页controller
 *
 * @author kane
 */
@RestController
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}