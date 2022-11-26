package database;
import database.exceptions.EmptyCellException;
import utils.Logger;

import java.util.Arrays;
import java.util.Objects;

public class ArrayBasedDatasource implements Datasource<Integer, String> {
    private final Logger log = new Logger(getClass());
    private final String[] records;

    private static ArrayBasedDatasource INSTANCE;

    public ArrayBasedDatasource(Integer size) {
        log.info("Initializing the internal data structure to store records");
        records = new String[size];
        Arrays.fill(records, "");
    }

    public synchronized static ArrayBasedDatasource getInstance() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new ArrayBasedDatasource(1000);
        }
        return INSTANCE;
    }

    public synchronized void set(Integer index, String record) {
        log.info(String.format("Save the record {%s} at position %d", record, index));
        validateRecordIndex(index);
        int zeroBased = index - 1;
        records[zeroBased] = record;
    }

    public synchronized String get(Integer index) {
        log.info(String.format("Retrieve record at index %d", index));
        int zeroBased = index - 1;
        validateRecordIndex(index);
        if (records[zeroBased].isEmpty()) {
            String message = String.format("Record at index %d is empty", index);
            log.warn(message);
            throw new EmptyCellException(message);
        }
        return records[zeroBased];
    }

    public synchronized void delete(Integer index) {
        log.info(String.format("Delete record at index %d", index));
        validateRecordIndex(index);
        int zeroBased = index - 1;
        if (!records[zeroBased].isEmpty()) {
            records[zeroBased] = "";
        }
    }

    private void validateRecordIndex(int index) {
        if (index < 1 || index > records.length) {
            log.warn(
                    String.format("Try to save a record at index %s, which is out of " +
                            "bound. Make sure the index is in the interval [1-%d]", index, records.length));
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
