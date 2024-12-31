package com.awake.ve.common.translation.core.impl;

import com.awake.ve.common.translation.annotation.TranslationType;
import com.awake.ve.common.translation.constant.TransConstant;
import com.awake.ve.common.translation.core.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 字典翻译实现
 *
 * @author Lion Li
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DICT_TYPE_TO_LABEL)
public class DictTypeTranslationImpl implements TranslationInterface<String> {

    // private final DictService dictService;

    @Override
    public String translation(Object key, String other) {
        // if (key instanceof String dictValue && StringUtils.isNotBlank(other)) {
        //     return dictService.getDictLabel(other, dictValue);
        // }
        return null;
    }
}
