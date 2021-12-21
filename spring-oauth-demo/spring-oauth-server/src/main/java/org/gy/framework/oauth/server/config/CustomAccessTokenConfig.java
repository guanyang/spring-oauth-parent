package org.gy.framework.oauth.server.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.gy.framework.oauth.common.enums.AuthorityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
@Configuration
public class CustomAccessTokenConfig {

    @Autowired(required = false)
    private AccessTokenConverter tokenConverter;

    @Bean
    public TokenEnhancer tokenEnhancer() {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = Lists.newArrayList(new MyTokenEnhancer());
        if (tokenConverter != null && tokenConverter instanceof JwtAccessTokenConverter) {
            delegates.add((TokenEnhancer) tokenConverter);
        }
        tokenEnhancerChain.setTokenEnhancers(delegates);
        return tokenEnhancerChain;
    }

    public static class MyTokenEnhancer implements TokenEnhancer {

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            final Map<String, Object> additionalInfo = Maps.newHashMapWithExpectedSize(2);
            Authentication userAuthentication = authentication.getUserAuthentication();
            Set<String> authoritySet = AuthorityUtils.authorityListToSet(userAuthentication.getAuthorities());
            if (authoritySet.contains(AuthorityEnum.ADMIN.getAuthority())) {
                additionalInfo.put("authorities", authoritySet);
                additionalInfo.put("userInfo", userAuthentication.getPrincipal());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            }
            return accessToken;
        }
    }

}
