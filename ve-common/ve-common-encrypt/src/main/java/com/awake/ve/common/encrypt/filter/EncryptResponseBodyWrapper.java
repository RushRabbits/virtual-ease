package com.awake.ve.common.encrypt.filter;

import cn.hutool.core.util.RandomUtil;
import com.awake.ve.common.encrypt.utils.EncryptUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 加密响应体的包装类
 *
 * @author wangjiaxing
 * @date 2025/2/10 17:54
 */
public class EncryptResponseBodyWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final ServletOutputStream servletOutputStream;
    private final PrintWriter printWriter;

    public EncryptResponseBodyWrapper(HttpServletResponse response) throws IOException {
        super(response);
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.servletOutputStream = this.getOutputStream();
        this.printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream));
    }

    /**
     * 获取加密内容
     *
     * @param response   {@link HttpServletResponse}
     * @param publicKey  公钥
     * @param headerFlag 响应头
     * @return 加密后的内容
     * @author wangjiaxing
     * @date 2025/2/10 17:58
     */
    public String getEncryptContent(HttpServletResponse response, String publicKey, String headerFlag) throws IOException {
        // 生成AES密钥
        String AESPassword = RandomUtil.randomString(32);

        // AES密钥使用BASE64编码
        String encryptAES = EncryptUtils.encryptByBase64(AESPassword);

        // RSA公钥加密BASE64编码
        String encryptPassword = EncryptUtils.encryptByRsa(encryptAES, publicKey);

        // 设置响应头
        response.setHeader(headerFlag, encryptPassword);
        response.addHeader("Access-Control-Expose-Headers", headerFlag);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        // 获取原始内容
        String content = this.getContent();

        // 对原始内容加密
        return EncryptUtils.encryptByAes(content, AESPassword);
    }

    @Override
    public PrintWriter getWriter() {
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (servletOutputStream != null) {
            servletOutputStream.flush();
        }
        if (printWriter != null) {
            printWriter.flush();
        }
    }

    @Override
    public void reset() {
        byteArrayOutputStream.reset();
    }

    public byte[] getResponseData() throws IOException {
        flushBuffer();
        return byteArrayOutputStream.toByteArray();
    }

    public String getContent() throws IOException {
        flushBuffer();
        return byteArrayOutputStream.toString();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }

            @Override
            public void write(byte[] b) throws IOException {
                byteArrayOutputStream.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                byteArrayOutputStream.write(b, off, len);
            }
        };
    }

}
