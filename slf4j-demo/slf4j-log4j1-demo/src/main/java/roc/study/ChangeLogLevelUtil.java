package roc.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.util.Enumeration;
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
                logFrameworkType = LogFrameworkType.LOG4J;
                Enumeration enumeration = org.apache.log4j.LogManager.getCurrentLoggers();
                while (enumeration.hasMoreElements()) {
                    org.apache.log4j.Logger logger = (org.apache.log4j.Logger) enumeration.nextElement();
                    if (logger.getLevel() != null) {
                        loggerMap.put(logger.getName(), logger);
                    }
                }
                org.apache.log4j.Logger rootLogger = org.apache.log4j.LogManager.getRootLogger();
                loggerMap.put(rootLogger.getName(), rootLogger);
                break;
            case LogConstant.LOGBACK_LOGGER_FACTORY:
                // placeholder lines...
                break;
            case LogConstant.LOG4J2_LOGGER_FACTORY:
                // placeholder lines...
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
                    org.apache.log4j.Logger targetLogger = (org.apache.log4j.Logger) logger;
                    org.apache.log4j.Level targetLevel = org.apache.log4j.Level.toLevel(loggerLevel);
                    targetLogger.setLevel(targetLevel);
                    break;
                case LOGBACK:
                    // placeholder lines...
                    break;
                case LOG4J2:
                    // placeholder lines...
                    break;
                default:
                    throw new RuntimeException("UnKnown logger type!");
            }
        }
        return "success";
    }
}
