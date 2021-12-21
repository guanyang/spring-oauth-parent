package org.gy.framework.oauth.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 功能描述：
 *
 * @author gy
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum AuthorityEnum {

    //权限定义
    ADMIN("admin"),

    USER("user");

    private final String authority;
}
