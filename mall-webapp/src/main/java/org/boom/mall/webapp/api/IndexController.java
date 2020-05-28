package org.boom.mall.webapp.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author kane
 */
@RestController
public class IndexController {
    @GetMapping
    public String index(){
        return "BOOM!";
    }
}