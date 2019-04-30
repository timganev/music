package com.packt.app.logger;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerServiceImpl implements LoggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerServiceImpl.class);

    @Override
    public void doStuff(final String value) {
        LOGGER.trace("doStuff needed more information - {}", value);
        LOGGER.debug("doStuff needed to debug - {}", value);
        LOGGER.info("doStuff took input - {}", value);
        LOGGER.warn("doStuff needed to warn - {}", value);
        LOGGER.error("doStuff encountered an error with value - {}", value);
    }
}