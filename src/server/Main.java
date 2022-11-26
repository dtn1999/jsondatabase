package server;

import database.JsonFileBasedDataSource;
import server.core.ServerApplication;
import utils.ApplicationConfig;

public class Main {
    public static void main(String[] args) {
        JsonFileBasedDataSource.getInstance();
        ServerApplication.run(ApplicationConfig.SERVER_HOST, ApplicationConfig.SERVER_PORT);
    }
}
