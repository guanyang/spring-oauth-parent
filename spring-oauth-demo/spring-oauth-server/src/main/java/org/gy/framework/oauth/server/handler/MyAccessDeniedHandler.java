package org.gy.framework.oauth.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.gy.framework.core.dto.Response;
import org.gy.framework.core.exception.CommonErrorCode;
import org.gy.framework.util.response.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseUtil.ajaxJson(request, response,
            Response.asError(CommonErrorCode.DATA_NOT_EXIST.getError(), accessDeniedException.getMessage()));
    }
}
