package org.gy.framework.oauth.common.config.wechat;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
public class WechatOAuth2Customizer {

    private static final String WECHAT_ID = "wechat";

    private static final String WECHAT_APP_ID = "appid";

    private static final String WECHAT_SECRET = "secret";

    private static final String WECHAT_OPENID = "openid";

    private static final String WECHAT_LANG = "lang";

    private static final String WECHAT_DEFAULT_LANG = "zh_CN";

    /**
     * Customize.
     *
     * @param builder the builder
     */
    public static void customize(OAuth2AuthorizationRequest.Builder builder) {
        String regId = (String) builder.build().getAttributes().get(OAuth2ParameterNames.REGISTRATION_ID);
        if (WECHAT_ID.equals(regId)) {
            builder.authorizationRequestUri(WechatOAuth2Customizer::customize);
        }
    }

    public static class WechatOAuth2UserRequestEntityConverter extends OAuth2UserRequestEntityConverter {

        @Override
        public RequestEntity<?> convert(OAuth2UserRequest userRequest) {
            ClientRegistration clientRegistration = userRequest.getClientRegistration();
            if (WECHAT_ID.equals(clientRegistration.getRegistrationId())) {
                HttpHeaders httpHeaders = getDefaultRequestHeaders();
                String userInfoUri = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri();
                LinkedMultiValueMap<String, String> queryParams = buildOAuth2UserQueryParams(userRequest);
                URI uri = UriComponentsBuilder.fromUriString(userInfoUri).queryParams(queryParams).build().toUri();
                return RequestEntity.get(uri).headers(httpHeaders).build();
            }
            return super.convert(userRequest);
        }
    }


    public static class WechatMapOAuth2AccessTokenResponseConverter implements
        Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

        private final Converter<Map<String, Object>, OAuth2AccessTokenResponse> delegate = new DefaultMapOAuth2AccessTokenResponseConverter();

        @Override
        public OAuth2AccessTokenResponse convert(Map<String, Object> tokenResponseParameters) {
            //微信不返回tokenType，进行兼容处理，添加默认值
            if (tokenResponseParameters.get(OAuth2ParameterNames.TOKEN_TYPE) == null) {
                tokenResponseParameters.put(OAuth2ParameterNames.TOKEN_TYPE, TokenType.BEARER.getValue());
            }
            return this.delegate.convert(tokenResponseParameters);
        }
    }

    public static class WechatOAuth2RequestEntityConverter extends OAuth2AuthorizationCodeGrantRequestEntityConverter {

        @Override
        protected MultiValueMap<String, String> createParameters(
            OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
            MultiValueMap<String, String> parameters = super.createParameters(authorizationCodeGrantRequest);
            ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
            if (WECHAT_ID.equals(clientRegistration.getRegistrationId())) {
                parameters.remove(OAuth2ParameterNames.CLIENT_ID);
                parameters.remove(OAuth2ParameterNames.CLIENT_SECRET);
                //appid
                parameters.add(WECHAT_APP_ID, clientRegistration.getClientId());
                //secret
                parameters.add(WECHAT_SECRET, clientRegistration.getClientSecret());
            }
            return parameters;
        }

        @Override
        public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
            ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
            if (WECHAT_ID.equals(clientRegistration.getRegistrationId())) {
                HttpHeaders httpHeaders = getDefaultRequestHeaders();
                String tokenUri = clientRegistration.getProviderDetails().getTokenUri();
                MultiValueMap<String, String> queryParameters = this.createParameters(authorizationGrantRequest);
                URI uri = UriComponentsBuilder.fromUriString(tokenUri).queryParams(queryParameters).build().toUri();
                return RequestEntity.get(uri).headers(httpHeaders).build();
            }
            return super.convert(authorizationGrantRequest);
        }
    }

    private static LinkedMultiValueMap<String, String> buildOAuth2UserQueryParams(OAuth2UserRequest userRequest) {
        LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        // access_token
        queryParams.add(OAuth2ParameterNames.ACCESS_TOKEN, userRequest.getAccessToken().getTokenValue());
        // openid
        queryParams.add(WECHAT_OPENID, String.valueOf(userRequest.getAdditionalParameters().get(WECHAT_OPENID)));
        // lang=zh_CN
        queryParams.add(WECHAT_LANG, WECHAT_DEFAULT_LANG);
        return queryParams;
    }

    private static HttpHeaders getDefaultRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        final MediaType contentType = MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        headers.setContentType(contentType);
        return headers;
    }

    /**
     * 默认情况下Spring Security会生成授权链接：https://open.weixin.qq.com/connect/oauth2/authorize?response_type=code
     * &client_id=xxx&scope=snsapi_userinfo&state=xxx&redirect_uri=xxx}
     *
     * 缺少了微信协议要求的{@code #wechat_redirect}，同时 {@code client_id}应该替换为{@code appid}
     */
    private static URI customize(UriBuilder builder) {
        String reqUri = builder.build().toString()
            .replaceAll("client_id=", "appid=")
            .concat("#wechat_redirect");
        return URI.create(reqUri);
    }

}
