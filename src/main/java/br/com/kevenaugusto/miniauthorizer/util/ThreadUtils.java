package br.com.kevenaugusto.miniauthorizer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUtils {

    private static final Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

    public static void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException error) {
            logger.error(error.getMessage(), error);
        }
    }

}
