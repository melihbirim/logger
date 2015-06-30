package logger;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestLogManager {

    private Logger logger = LogManager.get("TEST", TestLogManager.class);
    private Logger logger3 = LogManager.get("TEST3", TestLogManager.class);

    @Test
    public void testLog4j() throws InterruptedException {

        logger.info("INFO for Elastic Search");
        logger.warn("WARN for Elastic Search");
        logger.debug("DEBUG for Elastic Search");
        logger.error("ERROR for Elastic Search", new Exception("TEST EXCEPTION"));
        logger.fatal("FATAL for Elastic Search");

    }

}
