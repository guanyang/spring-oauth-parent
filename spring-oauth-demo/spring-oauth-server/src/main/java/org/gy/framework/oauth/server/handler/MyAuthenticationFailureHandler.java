package org.gy.framework.oauth.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.gy.framework.core.dto.Response;
import org.gy.framework.core.exception.CommonErrorCode;
import org.gy.framework.util.response.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        ResponseUtil.ajaxJson(request, response,
            Response.asError(CommonErrorCode.USER_STATUS_ERROR.getError(), exception.getMessage()));
    }
}
