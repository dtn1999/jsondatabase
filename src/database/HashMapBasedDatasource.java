package database;

import com.google.gson.*;
import database.exceptions.KeyNotFoundException;
import utils.ApplicationConfig;
import utils.ApplicationUtils;
import utils.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HashMapBasedDatasource implements Datasource<String, Object> {
    private final Logger log = Logger.getLogger(HashMapBasedDatasource.class);
    private final Map<String, Object> store;

    private static HashMapBasedDatasource INSTANCE;

    private HashMapBasedDatasource() {
        store = new HashMap<>();
        setUp();
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

    public static HashMapBasedDatasource getInstance() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new HashMapBasedDatasource();
        }
        return INSTANCE;
    }

    @Override
    public void set(String key, Object value) {
        store.put(key, value);
        HashMap<Object, Object> store =
                ApplicationUtils.fromJsonFile(ApplicationConfig.DB_FILE_RELATIVE_LOCATION, HashMap.class, new HashMap<>());
        store.put(key, value);
        ApplicationUtils.writeToFile(ApplicationConfig.DB_FILE_RELATIVE_LOCATION, store);
    }

    @Override
    public Object get(String key) {
            if (!store.containsKey(key)) {
                throw new KeyNotFoundException();
            }
            return store.get(key);
    }

    @Override
    public void delete(String key) {
        if (!store.containsKey(key)) {
            throw new KeyNotFoundException();
        }
        store.remove(key);
    }
}
