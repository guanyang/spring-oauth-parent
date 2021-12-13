package org.gy.framework.oauth.server.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * 资源服务器提供的受保护接口
     */
    @RequestMapping("/api/user")
    public ResponseEntity profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> profile = new HashMap<>();
        profile.put("username", authentication.getName());
        return ResponseEntity.ok(profile);
    }

}
