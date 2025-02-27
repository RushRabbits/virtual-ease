package com.awake.ve.common.ecs.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;

import java.io.FileWriter;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.EQUAL_MARK;
import static com.awake.ve.common.ecs.constants.ApiParamConstants.PROXY;
import static com.awake.ve.common.ecs.constants.VVFileConstants.*;
import static com.awake.ve.common.ecs.constants.VVFileConstants.RELEASE_CURSOR;

public class VVFileUtils {

    public static void createVVFile(BaseApiResponse response) {
        JSONObject jsonObject = JSONUtil.parseObj(response);
        StringBuilder vvConfig = new StringBuilder();
        vvConfig.append(VV_FILE_HEADER);
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (key.contains(CA)) {
                value = value.toString();
            }
            if (key.contains(PROXY)) {
                value = "http://192.168.1.139:3128";
            }
            if (key.contains("hostSubject")) {
                key = HOST_SUBJECT;
            }
            if (key.contains("secureAttention")) {
                key = SECURE_ATTENTION;
            }
            if (key.contains("toggleFullScreen")) {
                key = TOGGLE_FULLSCREEN;
            }
            if (key.contains("tlsPort")) {
                key = TLS_PORT;
            }
            if (key.contains("deleteThisFile")) {
                key = DELETE_THIS_FILE;
            }
            if (key.contains("releaseCursor")) {
                key = RELEASE_CURSOR;
            }
            vvConfig.append(key).append(EQUAL_MARK).append(value).append("\n");
        }

        String filename = "/home/" + jsonObject.getStr("title") + System.currentTimeMillis() + ".vv";

        String body = vvConfig.toString();
        System.out.println(body);
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
