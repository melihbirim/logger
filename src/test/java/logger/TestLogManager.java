package logger;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestLogManager {

    private Logger logger = LogManager.get("TEST", TestLogManager.class);
    private Logger logger3 = LogManager.get("TEST3", TestLogManager.class);

    @Test
    public void testLog4j() throws InterruptedException {
        while (true) {
            logger.info("Log4j for Elastic Search");
            logger.warn("Log4j for Elastic Search");
            logger.debug("Log4j for Elastic Search");
            logger.error("Log4j for Elastic Search", new Exception("TEST EXCEPTION"));
            logger.fatal("Log4j for Elastic Search");
            Thread.sleep(1000);

            logger3.info("Log4j for Elastic Search");
            logger3.warn("Log4j for Elastic Search");
            logger3.debug("Log4j for Elastic Search");
            logger3.error("Log4j for Elastic Search", new Exception("TEST3 EXCEPTION"));
            logger3.fatal("Log4j for Elastic Search");
        }
    }

}
