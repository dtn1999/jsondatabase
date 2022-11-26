package server.core.command;


import database.Datasource;

import java.util.Optional;

public class DeleteCommand<K, V> extends KeyBasedCommand<K, V> {

    public DeleteCommand(K key) {
        super(key);
    }

    public DeleteCommand(Datasource<K, V> datasource, K key) {
        super(datasource, key);
    }

    @Override
    public Optional<V> execute() {
        datasource.delete(key);
        return Optional.empty();
    }
}
