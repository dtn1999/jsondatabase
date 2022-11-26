package utils;

import java.util.Date;

public class Logger {
    private final Class<?> clazz;

    public static Logger getLogger(Class<?> clazz){
        return new Logger(clazz);
    }

    public Logger(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void debug(String message) {
        System.out.printf("%s: %s [%s]: %s%n", new Date(), clazz.getName(), LogLevel.INFO, message);
    }

    public void info(String message) {
        System.out.printf("%s: %s [%s]: %s%n", new Date(), clazz.getName(), LogLevel.INFO, message);
    }

    public void warn(String message) {
        System.out.printf("%s: %s [%s]: %s%n", new Date(), clazz.getName(), LogLevel.WARN, message);
    }

    public void error(String message, Throwable throwable) {
        System.out.printf("%s: %s [%s]: %s%n", new Date(), clazz.getName(), LogLevel.ERROR, message);

    }

    public void fatal(String message, Throwable throwable) {
        System.out.printf("%s: %s [%s]: %s%n", new Date(), clazz.getName(), LogLevel.FATAL, message);
        System.out.printf("%s", (Object) throwable.getStackTrace());
    }

    public static enum LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR,
        FATAL;
    }
}
