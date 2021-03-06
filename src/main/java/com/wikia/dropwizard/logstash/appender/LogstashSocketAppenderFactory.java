package com.wikia.dropwizard.logstash.appender;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.net.SyslogConstants;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.logstash.logback.appender.LogstashSocketAppender;

import java.util.HashMap;

@JsonTypeName("logstash-socket")
public class LogstashSocketAppenderFactory extends AbstractLogstashAppenderFactory {
  public LogstashSocketAppenderFactory() {
    port = SyslogConstants.SYSLOG_PORT;
  }

  @Override
  public Appender<ILoggingEvent> build(LoggerContext context, String applicationName, Layout<ILoggingEvent> layout) {
    final LogstashSocketAppender appender = new LogstashSocketAppender();

    appender.setName("logstash-socket-appender");
    appender.setContext(context);
    appender.setHost(host);
    appender.setPort(port);

    appender.setIncludeCallerData(includeCallerInfo);
    appender.setIncludeContext(includeContext);
    appender.setIncludeMdc(includeMdc);

    if (customFields == null) {
      customFields = new HashMap<>();
    }

    if (customFields.containsKey("applicationName")){
    	customFields.put("applicationName", applicationName);
    }
    appender.setCustomFields(LogstashAppenderFactoryHelper.getCustomFieldsFromHashMap(customFields));

    if (fieldNames != null) {
      appender.setFieldNames(LogstashAppenderFactoryHelper.getFieldNamesFromHashMap(fieldNames));
    }

    addThresholdFilter(appender, threshold);
    appender.start();

    return wrapAsync(appender);
  }
}
