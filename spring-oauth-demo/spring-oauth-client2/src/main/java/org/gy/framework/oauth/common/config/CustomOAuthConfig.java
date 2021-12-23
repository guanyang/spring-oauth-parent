package org.gy.framework.oauth.common.config;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.gy.framework.oauth.common.config.wechat.WechatOAuth2Customizer;
import org.gy.framework.oauth.common.config.wechat.WechatOAuth2Customizer.WechatMapOAuth2AccessTokenResponseConverter;
import org.gy.framework.oauth.common.config.wechat.WechatOAuth2Customizer.WechatOAuth2RequestEntityConverter;
import org.gy.framework.oauth.common.config.wechat.WechatOAuth2Customizer.WechatOAuth2UserRequestEntityConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
@Configuration
public class CustomOAuthConfig {

    @Bean
    public OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver(
        ClientRegistrationRepository clientRegistrationRepository) {
        DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(
            clientRegistrationRepository,
            OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);
        resolver.setAuthorizationRequestCustomizer(WechatOAuth2Customizer::customize);
        return resolver;
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> oAuth2AccessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient responseClient = new DefaultAuthorizationCodeTokenResponseClient();
        WechatOAuth2RequestEntityConverter requestEntityConverter = new WechatOAuth2RequestEntityConverter();
        responseClient.setRequestEntityConverter(requestEntityConverter);

        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        // 微信返回的content-type 是 text-plain
        tokenResponseHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
            MediaType.TEXT_PLAIN, new MediaType("application", "*+json")));
        // 兼容微信解析
        tokenResponseHttpMessageConverter
            .setAccessTokenResponseConverter(new WechatMapOAuth2AccessTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(
            Arrays.asList(new FormHttpMessageConverter(),
                tokenResponseHttpMessageConverter
            ));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        responseClient.setRestOperations(restTemplate);
        return responseClient;
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        oAuth2UserService.setRequestEntityConverter(new WechatOAuth2UserRequestEntityConverter());

        // 微信返回的content-type 是 text-plain，进行兼容处理
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
            MediaType.TEXT_PLAIN, new MediaType("application", "*+json")));

        RestTemplate restTemplate = new RestTemplate(
            Arrays.asList(new StringHttpMessageConverter(StandardCharsets.UTF_8), jackson2HttpMessageConverter));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        oAuth2UserService.setRestOperations(restTemplate);
        return oAuth2UserService;
    }

}
