package server.core.command;

import java.util.Optional;

public class ExitCommand<K, V> extends Command<K, V> {
    @Override
    public Optional<V> execute() {
        return Optional.empty();
    }
}
