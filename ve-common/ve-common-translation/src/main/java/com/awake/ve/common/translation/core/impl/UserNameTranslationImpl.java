package com.awake.ve.common.translation.core.impl;

import lombok.AllArgsConstructor;
import com.awake.ve.common.core.service.UserService;
import com.awake.ve.common.translation.annotation.TranslationType;
import com.awake.ve.common.translation.constant.TransConstant;
import com.awake.ve.common.translation.core.TranslationInterface;

/**
 * 用户名翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.USER_ID_TO_NAME)
public class UserNameTranslationImpl implements TranslationInterface<String> {

    private final UserService userService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof Long id) {
            return userService.selectUserNameById(id);
        }
        return null;
    }
}
