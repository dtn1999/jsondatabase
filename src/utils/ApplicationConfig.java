package utils;

import java.io.File;

public final class ApplicationConfig {
    public static String SERVER_HOST = "localhost";
    public static int SERVER_PORT = 23456;
    public static final String CLIENT_DATA_PATH = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "client" + File.separator +
            "data" + File.separator;
//    public static final String CLIENT_DATA_PATH = "src/client/data/";

    public static final String STORE_LOCATION_DIRECTORY = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "server" + File.separator +
            "data" + File.separator;
//    public static final String STORE_LOCATION_DIRECTORY = "src/server/data/";
    public static final String DB_FILE_NAME = "db.json";
    public static final String DB_FILE_RELATIVE_LOCATION = STORE_LOCATION_DIRECTORY + DB_FILE_NAME;
}
