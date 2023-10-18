package roc.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.util.HashMap;
import java.util.Map;

public class ChangeLogLevelUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(ChangeLogLevelUtil.class);
    private static final Map<String, Object> loggerMap = new HashMap<>();
    private static LogFrameworkType logFrameworkType;

    public static void init() {
        String type = StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr();
        LOG.info("Log type={}", type);
        switch (type) {
            case LogConstant.LOG4J1_LOGGER_FACTORY:
                // placeholder lines...
                break;
            case LogConstant.LOGBACK_LOGGER_FACTORY:
                // placeholder lines...
                break;
            case LogConstant.LOG4J2_LOGGER_FACTORY:
                logFrameworkType = LogFrameworkType.LOG4J2;
                org.apache.logging.log4j.core.LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
                Map<String, org.apache.logging.log4j.core.config.LoggerConfig> map = loggerContext.getConfiguration().getLoggers();
                for (org.apache.logging.log4j.core.config.LoggerConfig loggerConfig : map.values()) {
                    String key = loggerConfig.getName();
                    if (key == null || "".equals(key.trim())) {
                        key = "root";
                    }
                    loggerMap.put(key, loggerConfig);
                }
                break;
            default:
                logFrameworkType = LogFrameworkType.UNKNOWN;
                LOG.error("Failed to detect the logger framework: type={}", type);
        }
    }

    public static String setLogLevel(String loggerLevel) {
        for (Object logger : loggerMap.values()) {
            if (logger == null) {
                throw new RuntimeException("The target logger is null");
            }
            switch (logFrameworkType) {
                case LOG4J:
                    // placeholder lines...
                    break;
                case LOGBACK:
                    // placeholder lines...
                    break;
                case LOG4J2:
                    org.apache.logging.log4j.core.config.LoggerConfig loggerConfig = (org.apache.logging.log4j.core.config.LoggerConfig) logger;
                    org.apache.logging.log4j.Level targetLevel = org.apache.logging.log4j.Level.toLevel(loggerLevel);
                    loggerConfig.setLevel(targetLevel);
                    org.apache.logging.log4j.core.LoggerContext ctx = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
                    ctx.updateLoggers(); // This causes all Loggers to refetch information from their LoggerConfig.
                    break;
                default:
                    throw new RuntimeException("UnKnown logger type!");
            }
        }
        return "success";
    }
}
