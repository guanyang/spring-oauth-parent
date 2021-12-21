package org.gy.framework.oauth.server.config;

import org.gy.framework.oauth.common.enums.AuthorityEnum;
import org.gy.framework.oauth.common.enums.RoleEnum;
import org.gy.framework.oauth.server.handler.MyLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/index.html", "/login", "/static/**").permitAll()
            .anyRequest().authenticated()
            .and().formLogin().loginPage("/index.html").loginProcessingUrl("/login")
//            .successHandler(new MyAuthenticationSuccessHandler())
//            .failureHandler(new MyAuthenticationFailureHandler())
            .and().logout().logoutSuccessHandler(new MyLogoutSuccessHandler())
//            .and().exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler())
            .and().csrf().disable();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // 配置用户名密码，这里采用内存方式，生产环境需要从数据库获取
        UserDetails adminUser = User.withUsername("admin").password(passwordEncoder().encode("123"))
            .roles(RoleEnum.ADMIN.getRole())
            .authorities(AuthorityEnum.ADMIN.getAuthority()).build();
        UserDetails user = User.withUsername("user").password(passwordEncoder().encode("123"))
            .roles(RoleEnum.USER.getRole())
            .authorities(AuthorityEnum.USER.getAuthority()).build();
        return new InMemoryUserDetailsManager(adminUser, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
