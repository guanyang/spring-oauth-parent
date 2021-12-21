package org.gy.framework.oauth.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.StringUtils;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        String redirectUrl = request.getParameter(OAuth2Utils.REDIRECT_URI);
        SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        if (StringUtils.hasText(redirectUrl)) {
            urlLogoutSuccessHandler.setDefaultTargetUrl(redirectUrl);
        }
        urlLogoutSuccessHandler.onLogoutSuccess(request, response, authentication);
    }
}
