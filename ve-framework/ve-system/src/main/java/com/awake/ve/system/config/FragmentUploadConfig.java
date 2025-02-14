package com.awake.ve.system.config;

import com.awake.ve.system.config.properties.FragmentUploadProperties;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@EnableConfigurationProperties(FragmentUploadProperties.class)
public class FragmentUploadConfig {
}
