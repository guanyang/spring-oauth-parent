package org.gy.framework.oauth.common.config;

import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
@Configuration
public class OAuthClientSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver;

    @Resource
    private OAuth2AccessTokenResponseClient oAuth2AccessTokenResponseClient;

    @Resource
    private OAuth2UserService oAuth2UserService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and().logout().logoutSuccessUrl("/").permitAll()
            .and().oauth2Login()
//            .successHandler((request, response, authentication) -> {
//                response.sendRedirect("/");
//            })
            .authorizationEndpoint().authorizationRequestResolver(oAuth2AuthorizationRequestResolver)
            .and().tokenEndpoint().accessTokenResponseClient(oAuth2AccessTokenResponseClient)
            .and().userInfoEndpoint().userService(oAuth2UserService);
    }
}
