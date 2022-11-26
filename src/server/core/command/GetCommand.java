package server.core.command;

import database.Datasource;

import java.util.Optional;

public class GetCommand<K, V> extends KeyBasedCommand<K, V> {
    public GetCommand(K key) {
        super(key);
    }

    public GetCommand(Datasource<K, V> datasource, K index) {
        super(datasource, index);
    }

    @Override
    public Optional<V> execute() {
        return Optional.of(datasource.get(key));
    }
}
