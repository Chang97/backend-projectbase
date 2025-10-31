package com.base.platform.config;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.util.StringUtils;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6SpySqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {
        if (!StringUtils.hasText(sql)) {
            return "";
        }
        String formattedSql = FormatStyle.BASIC.getFormatter().format(sql);
        return String.format("[%d ms] %s | conn %d%n%s", elapsed, category, connectionId, formattedSql);
    }
}
