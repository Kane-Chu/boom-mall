package org.boom.mall.webapp.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权接口
 *
 * @author kane
 */
@RestController
@RequestMapping("/api")
public class AuthController {
    @RequestMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password) {

    }
}