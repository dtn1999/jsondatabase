package server.core.command;

import database.Datasource;

import java.util.Optional;

public class CommandController<K, V> {
    private final Command<K, V> command;

    public CommandController(Datasource<K, V> datasource, Command<K, V> command) {
        this.command = command;
        this.command.setDatasource(datasource);
    }

    public Optional<V> executeCommand() {
        return command.execute();
    }
}
