package org.gy.framework.oauth.server.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
@RestController
public class UserController {

    @RequestMapping("/api/v2/user")
    public ResponseEntity user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(((OAuth2Authentication) authentication).getUserAuthentication());
    }

    /**
     * 资源服务器提供的受保护接口
     */
    @RequestMapping("/api/user")
    public ResponseEntity profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> profile = new HashMap<>();
        profile.put("name", authentication.getName());
        return ResponseEntity.ok(profile);
    }

}
