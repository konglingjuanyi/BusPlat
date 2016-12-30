package com.zhiyin.elk.boot.autoconfig;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;

@Configuration
@ConditionalOnProperty(value = "elk.logstash.enabled", matchIfMissing = true)
@EnableConfigurationProperties(LogstashConfig.class)
public class LogstashAutoConfiguration {

    @Configuration
    public class LogstashConfiguration {

        @Autowired
        private LogstashConfig logstashConfig;

        @Autowired
        private Environment environment;

        @Value("${spring.application.name ?:appnameNull}")
        private String appName;

        @PostConstruct
        public void init() {

            Map<String, String> custom = Optional.fromNullable(logstashConfig.getCustomFields()).or(Maps.<String, String>newConcurrentMap());
            custom.put("appName",appName);
            logstashConfig.setCustomFields( custom );

            // 设置默认值
            if (Strings.isNullOrEmpty(logstashConfig.getName())) {
                logstashConfig.setName("elk-trace-");
            }

            if (Strings.isNullOrEmpty(logstashConfig.getFilePath())) {
                String fileName = appName + ".json";
                String filePath = "/opt/data/elk-trace/" + fileName;
                logstashConfig.setFilePath(filePath);
            }

            ArrayList<String> types = Lists.<String>newArrayList();
            types.add(AppenderTyps.File.name());
            for (String tmp : Optional.fromNullable(logstashConfig.getTypes()).or(types)) {
                if (AppenderTyps.Console.name().equals(tmp)) {
                    LogAppenderUtil.addConsoleAppender(logstashConfig);
                }
                if (AppenderTyps.File.name().equals(tmp)) {
                    LogAppenderUtil.addFileAppender(logstashConfig);
                }
                if (AppenderTyps.Tcp.name().equals(tmp)) {
                    LogAppenderUtil.addLogstashAppender(logstashConfig);
                }

            }
        }

    }
}