package com.awake.ve.admin;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * web容器中部署
 *
 * @author wangjiaxing
 * @date 2024/12/12 19:22
 */
public class VirtualEaseServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(VirtualEaseApplication.class);
    }
}
