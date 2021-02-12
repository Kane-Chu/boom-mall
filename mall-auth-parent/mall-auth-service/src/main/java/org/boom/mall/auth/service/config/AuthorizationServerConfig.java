package org.boom.mall.auth.service.config;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.boom.mall.auth.api.bean.AuthUser;
import org.boom.mall.auth.api.consts.SecurityConstants;
import org.boom.mall.redis.config.RedisTemplateConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import static org.boom.mall.auth.api.consts.ResourceConstants.*;
import static org.boom.mall.auth.service.consts.CachingConstant.REDIS_OAUTH_TOKEN_PREFIX;

/**
 * 授权服务配置
 *
 * @author kane
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
@Import(RedisTemplateConfig.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * 连接此认证服务的client
     *
     * @param clients clients
     * @throws Exception Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // TODO 改为 jdbc 的
        clients.inMemory()
                .withClient("mall")
                .resourceIds(MALL_ACCOUNT_ID, MALL_GOODS_ID, MALL_ORDER_ID)
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .secret("{noop}111111")
                // 授权成功后登陆
                .redirectUris("https://www.baidu.com")
                // 认证通过后自动授权 下发token 而不需要用户再点击确认下
                .autoApprove(true);
    }

    /**
     * 允许客户端form提交 获取access key不需认证 验证 access token不需认证
     *
     * @param security security
     * @throws Exception Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 默认只允许 POST
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false);
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(REDIS_OAUTH_TOKEN_PREFIX);
        return tokenStore;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        // 向JWT Token中加入额外的信息
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(4);
            AuthUser authUser = (AuthUser) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put(SecurityConstants.JWT_USER_ID_KEY, authUser.getUserId());
            additionalInfo.put(SecurityConstants.JWT_USER_NAME_KEY, authUser.getUsername());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}