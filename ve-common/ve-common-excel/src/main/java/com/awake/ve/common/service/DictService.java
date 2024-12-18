package com.awake.ve.common.service;

import java.util.Map;

public interface DictService {
    String getDictValue(String dictType, String label, String separator);

    String getDictLabel(String dictType, String value, String separator);

    Map<String , String> getAllDictByDictType(String dictType);
}
