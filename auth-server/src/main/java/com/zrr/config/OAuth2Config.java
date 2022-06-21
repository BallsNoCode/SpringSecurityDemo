package com.zrr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

/**
 * @author APPDE
 * @date 2022/6/20 1:24
 * @Version 1.0
 **/
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String CLIENT_ID = "cms";
    private static final String SECRET_CHAR_SEQUENCE = "{noop}secret";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";
    private static final String USER = "user";
    private static final String ALL = "all";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 30 * 60;
    private static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 30 * 60;
    // 密码模式授权模式
    private static final String GRANT_TYPE_PASSWORD = "password";
    //授权码模式
    private static final String AUTHORIZATION_CODE = "authorization_code";
    //refresh token模式
    private static final String REFRESH_TOKEN = "refresh_token";
    //简化授权模式
    private static final String IMPLICIT = "implicit";
    //指定哪些资源是需要授权验证的
    private static final String RESOURCE_ID = "resource_id";

    /**
     * @param security:
     * @Description: OAuth2端点的权限配置
     * @Author: zhangrunrong
     * @Date: 2022/6/20 1:28
     * @return: void
     **/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 配置/oauth/token_key 匿名访问：permitAll() 认证访问：isAuthenticated()
        security
                .tokenKeyAccess("permitAll()")
                //开启令牌验证端点oauth/check_token
                .checkTokenAccess("permitAll()")
                //允许表单验证
                .allowFormAuthenticationForClients();
        super.configure(security);
    }

    /**
     * @param endpoints:
     * @Description: token令牌存储方式
     * @Author: zhangrunrong
     * @Date: 2022/6/20 1:38
     * @return: void
     **/
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(memoryTokenStore());
    }

    /**
     * @param clients:
     * @Description: 配置客户端信息
     * @Author: zhangrunrong
     * @Date: 2022/6/20 1:29
     * @return: void
     **/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用内存存储
        clients
                .inMemory()
                //标记客户端ID
                .withClient(CLIENT_ID)
                //客户端秘钥
                .secret(SECRET_CHAR_SEQUENCE)
                //自动授权
                .autoApprove(true)
                //授权后重定向
                .redirectUris("http://127.0.0.1:8084/cms/login")
                //授权范围
                .scopes(ALL)
                //访问时效
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                //令牌刷新时效
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS)
                //授权模式
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD,AUTHORIZATION_CODE);
    }

    @Bean
    public TokenStore memoryTokenStore() {
        return new InMemoryTokenStore();
    }
}
