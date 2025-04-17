package com.awake.ve.common.translation.core.impl;

import lombok.AllArgsConstructor;
import com.awake.ve.common.core.service.OssService;
import com.awake.ve.common.translation.annotation.TranslationType;
import com.awake.ve.common.translation.constant.TransConstant;
import com.awake.ve.common.translation.core.TranslationInterface;

/**
 * OSS翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

    private final OssService ossService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String ids) {
            return ossService.selectUrlByIds(ids);
        } else if (key instanceof Long id) {
            return ossService.selectUrlByIds(id.toString());
        }
        return null;
    }
}
