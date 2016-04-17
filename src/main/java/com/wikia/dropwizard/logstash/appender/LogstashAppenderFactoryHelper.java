package com.wikia.dropwizard.logstash.appender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.fieldnames.LogstashFieldNames;

import java.util.HashMap;

public class LogstashAppenderFactoryHelper {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static LogstashFieldNames getFieldNamesFromHashMap(HashMap<String, String> map) {
    LogstashFieldNames fieldNames = new LogstashFieldNames();

    fieldNames.setTimestamp(getOrDefault(map, "timestamp", "@timestamp"));
    fieldNames.setVersion(getOrDefault(map, "version", "@version"));
    fieldNames.setMessage(getOrDefault(map, "message", "message"));
    fieldNames.setLogger(getOrDefault(map, "logger", "logger_name"));
    fieldNames.setThread(getOrDefault(map, "thread", "thread_name"));
    fieldNames.setLevel(getOrDefault(map, "level", "level"));
    fieldNames.setLevelValue(getOrDefault(map, "levelValue", "level_value"));
    fieldNames.setCaller(getOrDefault(map, "caller", null));
    fieldNames.setCallerClass(getOrDefault(map, "callerClass", "caller_class_name"));
    fieldNames.setCallerMethod(getOrDefault(map, "callerMethod", "caller_method_name"));
    fieldNames.setCallerFile(getOrDefault(map, "callerFile", "caller_file_name"));
    fieldNames.setCallerLine(getOrDefault(map, "callerLine", "caller_line_number"));
    fieldNames.setStackTrace(getOrDefault(map, "stackTrace", "stack_trace"));
    fieldNames.setTags(getOrDefault(map, "tags", "tags"));
    fieldNames.setMdc(getOrDefault(map, "mdc", null));
    fieldNames.setContext(getOrDefault(map, "context", null));

    return fieldNames;
  }

  public static String getOrDefault(HashMap<String, String> map, Object key, String value){
	  if (!map.containsKey(key)){
		  return value;
	  }
	  return map.get(key);
  }
  
  public static String getCustomFieldsFromHashMap(HashMap<String, String> map) {
    try {
      return objectMapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      return "{}";
    }
  }
}
