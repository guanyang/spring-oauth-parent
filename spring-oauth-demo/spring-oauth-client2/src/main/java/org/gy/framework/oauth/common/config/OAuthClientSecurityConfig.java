package org.gy.framework.oauth.common.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
@Configuration
@EnableOAuth2Sso
public class OAuthClientSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/index", "/login**").permitAll()
            .anyRequest().authenticated();
    }
}
