package server.core.command;

import database.Datasource;

import java.util.Optional;

public class SetCommand<K, V> extends KeyBasedCommand<K, V> {
    private final V record;

    public SetCommand(K key, V record) {
        super(key);
        this.record = record;
    }

    public SetCommand(Datasource<K, V> datasource, K key, V record) {
        super(datasource, key);
        this.record = record;
    }

    public V getRecord() {
        return record;
    }

    @Override
    public Optional<V> execute() {
        datasource.set(key, record);
        return Optional.empty();
    }
}
