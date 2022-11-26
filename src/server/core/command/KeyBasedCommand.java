package server.core.command;

import database.Datasource;

public abstract class KeyBasedCommand<K, V> extends Command<K, V> {
    protected final K key;

    public K getKey() {
        return key;
    }

    public KeyBasedCommand(K key) {
        this.key = key;
    }

    public KeyBasedCommand(Datasource<K, V> datasource, K key) {
        super(datasource);
        this.key = key;
    }
}
