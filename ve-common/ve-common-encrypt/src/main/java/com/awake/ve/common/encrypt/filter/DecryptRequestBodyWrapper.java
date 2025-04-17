package com.awake.ve.common.encrypt.filter;

import cn.hutool.core.io.IoUtil;
import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.encrypt.utils.EncryptUtils;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 解密请求参数工具类
 *
 * @author wangjiaxing
 * @date 2025/2/10 17:37
 */
public class DecryptRequestBodyWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public DecryptRequestBodyWrapper(HttpServletRequest request, String privateKey, String headerFlag) throws IOException {
        super(request);
        // 获取AES密钥  采用了RSA加密
        String headerRSA = request.getHeader(headerFlag);
        String decryptAES = EncryptUtils.decryptByRsa(headerRSA, privateKey);

        // 解密AES密钥
        String AESPassword = EncryptUtils.decryptByBase64(decryptAES);
        request.setCharacterEncoding(Constants.UTF8);

        // 获取请求体
        byte[] readBytes = IoUtil.readBytes(request.getInputStream(), false);
        String requestBody = new String(readBytes, StandardCharsets.UTF_8);

        // 通过AESPassword解密请求体requestBody
        String decryptBody = EncryptUtils.decryptByAes(requestBody, AESPassword);
        // 此时的body为解密后的请求体
        this.body = decryptBody.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    @Override
    public int getContentLength() {
        return body.length;
    }

    @Override
    public long getContentLengthLong() {
        return body.length;
    }

    @Override
    public String getContentType() {
        return MediaType.APPLICATION_JSON_VALUE;
    }


    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public int available() {
                return body.length;
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}
