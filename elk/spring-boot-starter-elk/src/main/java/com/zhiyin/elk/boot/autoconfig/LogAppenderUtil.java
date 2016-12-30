package com.zhiyin.elk.boot.autoconfig;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by wangqinghui on 2016/12/27.
 */
public class LogAppenderUtil {

    public static void addFileAppender(LogstashConfig config) {

        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        FileAppender appender = new FileAppender();
        appender.setName(config.getName() + "file");
        appender.setFile(config.getFilePath());

        appender.addFilter(newFileter(config));
        appender.setEncoder(newEncoder(config));
        appender.setContext(rootLogger.getLoggerContext());
        appender.start();
        rootLogger.addAppender(appender);
//        rootLogger.setLevel(Level.toLevel(logstashProperties.getLevel()));
    }


    public static void addConsoleAppender(LogstashConfig logstashConfig) {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setName(logstashConfig.getName() + "console");
        consoleAppender.setContext(rootLogger.getLoggerContext());
        consoleAppender.addFilter(newFileter(logstashConfig));
        consoleAppender.setEncoder(newEncoder(logstashConfig));
        consoleAppender.start();

        rootLogger.addAppender(consoleAppender);
    }

    public static Filter<ILoggingEvent> newFileter(final LogstashConfig config) {

        return new Filter<ILoggingEvent>() {
            @Override
            public FilterReply decide(ILoggingEvent event) {

                if (event.getLevel() == Level.DEBUG) {
                    return FilterReply.DENY;
                }

                // mdc key not null
                List<String> fields = Optional.fromNullable(config.getCheckMdcFields()).or(Lists.<String>newArrayList());
                for (String tmp : fields) {
                    if (Strings.isNullOrEmpty(tmp)) {
                        continue;
                    }
                    String str = event.getMDCPropertyMap().get("traceId");
                    if (Strings.isNullOrEmpty(str)) {
                        return FilterReply.DENY;
                    }
                }
                return FilterReply.ACCEPT;
            }
        };
    }

    public static void addLogstashAppender(LogstashConfig config) {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LogstashTcpSocketAppender appender = new LogstashTcpSocketAppender();
        appender.setName(config.getName() + "logstash");
        appender.addDestinations(new InetSocketAddress(config.getHost(), config.getPort()));
        appender.addFilter(newFileter(config));
        appender.setEncoder(newEncoder(config));
        appender.setContext(rootLogger.getLoggerContext());
        appender.start();
        rootLogger.addAppender(appender);
        rootLogger.setLevel(Level.toLevel(config.getLevel()));
    }

    public static Encoder newEncoder(LogstashConfig config) {
        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setIncludeCallerData(true);
        encoder.setCustomFields(JSON.toJSONString(config.getCustomFields()));//服务名会在日志中显示（可以方便的知道该日志是哪个服务的）
        encoder.start();
        return encoder;
    }
}
