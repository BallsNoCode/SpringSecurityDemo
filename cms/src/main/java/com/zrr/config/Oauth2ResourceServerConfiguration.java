package com.zrr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * @author zhangrunrong
 * @date 2022/6/20 2:15
 * @Version 1.0
 **/
@Configuration
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String CHECK_TOKEN_URL = "http://localhost:8888/oauth/check_token";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(CHECK_TOKEN_URL);
        tokenServices.setClientId("cms");
        tokenServices.setClientSecret("secret");
        resources.tokenServices(tokenServices);
    }
}
