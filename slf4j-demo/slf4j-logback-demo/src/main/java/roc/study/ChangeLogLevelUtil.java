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
                logFrameworkType = LogFrameworkType.LOGBACK;
                ch.qos.logback.classic.LoggerContext loggerContext = (ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
                for (ch.qos.logback.classic.Logger logger : loggerContext.getLoggerList()) {
                    if (logger.getLevel() != null) {
                        loggerMap.put(logger.getName(), logger);
                    }
                }
                ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
                loggerMap.put(rootLogger.getName(), rootLogger);
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
                    // placeholder lines...
                    break;
                case LOGBACK:
                    ch.qos.logback.classic.Logger targetLogger = (ch.qos.logback.classic.Logger) logger;
                    ch.qos.logback.classic.Level targetLevel = ch.qos.logback.classic.Level.toLevel(loggerLevel);
                    targetLogger.setLevel(targetLevel);
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
