package org.gy.framework.oauth.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.gy.framework.core.dto.Response;
import org.gy.framework.util.response.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        ResponseUtil.ajaxJson(request, response, Response.asSuccess(principal));
    }
}
