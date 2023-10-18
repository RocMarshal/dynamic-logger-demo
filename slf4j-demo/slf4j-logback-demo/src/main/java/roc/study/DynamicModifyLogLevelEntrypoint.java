package roc.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DynamicModifyLogLevelEntrypoint {
    protected static final Logger LOG = LoggerFactory.getLogger(DynamicModifyLogLevelEntrypoint.class);

    public static void main(String[] args) throws InterruptedException {
        ChangeLogLevelUtil.init();

        int count = 0;
        while (count < 6) {
            LOG.info("info log");
            LOG.debug("debug log");
            TimeUnit.SECONDS.sleep(1);
            count++;
            if (count == 3) {
                ChangeLogLevelUtil.setLogLevel("DEBUG");
                LOG.info("Changed logger level from '{}' to '{}'", "info", "debug");
            }
        }
    }
}
