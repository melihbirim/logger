package logger;

import org.apache.log4j.Logger;

import java.util.Hashtable;
import java.util.Map;

public class LogManager {

    private static final String DEFAULT = "default";

    private static final Map<String, Logger> applicationLoggerMap = new Hashtable<>();

    public synchronized static Logger get(Class clazz) {
        return get(DEFAULT, clazz);
    }

    public synchronized static Logger get(String applicationName, Class clazz) {
        Logger logger = null;
        if (applicationLoggerMap.containsKey(applicationName)) {
            logger = applicationLoggerMap.get(applicationName);
        }

        if (logger == null) {
            logger = org.apache.log4j.LogManager.getLogger(clazz);
            ElasticsearchLoggerAppender eap = new ElasticsearchLoggerAppender();
            eap.activateOptions();
            eap.setApplicationName(applicationName);
            logger.addAppender(eap);
        }

        return logger;
    }

}
