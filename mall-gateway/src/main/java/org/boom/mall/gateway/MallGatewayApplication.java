package org.boom.mall.gateway;

import java.util.List;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * application
 *
 * @author kane
 */
@SpringBootApplication
public class MallGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGatewayApplication.class, args);
    }

    @Controller
    @RequestMapping("discovery")
    public static class DiscoveryController {

        @NacosInjected
        private NamingService namingService;

        @RequestMapping(value = "/get", method = GET)
        @ResponseBody
        public List<Instance> get(@RequestParam String serviceName) throws NacosException {
            return namingService.getAllInstances(serviceName);
        }
    }
}