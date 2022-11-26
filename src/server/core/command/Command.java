package server.core.command;

import database.Datasource;

import java.util.Optional;

public abstract class Command<K, V> {
    protected Datasource<K, V> datasource;

    public Command() {

    }

    public Command(Datasource<K, V> database) {
        this.datasource = database;
    }

    abstract public Optional<V> execute();

    public void setDatasource(Datasource<K, V> datasource) {
        this.datasource = datasource;
    }

}
