package database;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import database.utils.JsonFileBasedDataSourceUtils;
import utils.ApplicationConfig;
import utils.ApplicationUtils;
import utils.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class JsonFileBasedDataSource implements Datasource<String, JsonElement> {
    private final Logger log = Logger.getLogger(JsonFileBasedDataSource.class);
    private static JsonFileBasedDataSource INSTANCE;
    private final ReentrantReadWriteLock readWriteLock;

    public JsonFileBasedDataSource(){
        readWriteLock = new ReentrantReadWriteLock();
        setUp();
    }
    public static JsonFileBasedDataSource getInstance() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new JsonFileBasedDataSource();
        }
        return INSTANCE;
    }

    private void setUp() {
        File file = new File(ApplicationConfig.STORE_LOCATION_DIRECTORY);
        if (!file.exists()) {
            try {
                Files.createDirectory(Path.of(ApplicationConfig.STORE_LOCATION_DIRECTORY));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void set(String key, JsonElement value) {
        readWriteLock.writeLock().lock();
        try {
            JsonObject store = JsonFileBasedDataSourceUtils.readFileDatabase();
            List<String> fieldPath = JsonFileBasedDataSourceUtils.parseKey(key);
            JsonFileBasedDataSourceUtils.setFieldInJson(store, fieldPath, value);
            ApplicationUtils.writeToFile(ApplicationConfig.DB_FILE_RELATIVE_LOCATION, store);
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public JsonElement get(String key) {
        readWriteLock.readLock().lock();
        try {
            JsonObject store = JsonFileBasedDataSourceUtils.readFileDatabase();
            List<String> fieldPath = JsonFileBasedDataSourceUtils.parseKey(key);
            return JsonFileBasedDataSourceUtils
                    .getFieldFromJson(store, fieldPath);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void delete(String key) {
        readWriteLock.writeLock().lock();
        try {
            JsonObject store = JsonFileBasedDataSourceUtils.readFileDatabase();
            List<String> fieldPath = JsonFileBasedDataSourceUtils.parseKey(key);
            JsonFileBasedDataSourceUtils.removeFieldFomJson(store, fieldPath);
            ApplicationUtils.writeToFile(ApplicationConfig.DB_FILE_RELATIVE_LOCATION, store);
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
