package org.boom.mall.auth.service;

import java.util.Arrays;

import org.boom.mall.auth.service.PasswordAuthTest.ResourceConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Password 模式测试
 *
 * @author kane
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallAuthApplication.class)
@ContextConfiguration(classes = ResourceConfiguration.class)
@WebAppConfiguration
class PasswordAuthTest {

    private static final String DOMAIN = "http://127.0.0.1:8080";
    private static final String ACCESS_TOKEN_URI = DOMAIN + "/oauth/token";

    @Configuration
    @EnableOAuth2Client
    static class ResourceConfiguration {

        @Bean
        public OAuth2RestTemplate passwordOAuthRestTemplate() {
            ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
            details.setId("mall/auth");
            details.setClientId("mall");
            details.setClientSecret("111111");
            details.setAccessTokenUri(ACCESS_TOKEN_URI);
            details.setScope(Arrays.asList("read", "write"));
            details.setUsername("kane");
            details.setPassword("pass1234");
            return new OAuth2RestTemplate(details);
        }

    }

    @Autowired
    private OAuth2RestTemplate passwordOAuthRestTemplate;

    @Test
    void testGetAccessToken() {
        OAuth2AccessToken accessToken = passwordOAuthRestTemplate.getAccessToken();
        String value = accessToken.getValue();
        System.out.println(value);
    }
}