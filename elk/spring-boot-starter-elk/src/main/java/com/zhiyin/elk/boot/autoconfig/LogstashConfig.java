package com.zhiyin.elk.boot.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "elk.logstash")
public class LogstashConfig {

    private List<String> types;
//    private String customFields;
    private Map<String,String> customFields;
    private String host;
    private int port;
    private String level;
    private String name;
    private String filePath;
    // 过滤的mdc字段
//    @Value("#{logstash.checkMdcFields ?: ''}")
    private List<String> checkMdcFields;

}