package com.example.demo.Logs;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogUtil {
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());

        // Avoid duplicate handlers
        if (logger.getHandlers().length == 0) {
            logger.addHandler(handler);
        }

        logger.setLevel(Level.ALL); 
        return logger;
    }
}

