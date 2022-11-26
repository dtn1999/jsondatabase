package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import server.core.command.*;
import shared.CommandRequest;

import java.util.Objects;
import java.util.Optional;

public final class CommandUtils {

    private static final Gson gson = new Gson();
    /**
     * Gets the command Object corresponding to the command the client has sent
     * @param json json representation of the client command
     */
    public static Optional<Command<String, JsonElement>> parseToCommand(String json) {
        CommandRequest commandRequest = gson.fromJson(json, CommandRequest.class);

        String type = commandRequest.getType();
        String tempKey = "";
        JsonElement tempValue = null;
        if(!Objects.isNull(commandRequest.getKey())){
            tempKey = ((JsonElement)commandRequest.getKey()).toString();
        }
        if(!Objects.isNull(commandRequest.getValue())){
            tempValue = (JsonElement) commandRequest.getValue();
        }

        if (type.isEmpty()) {
            return Optional.empty();
        }

        final String key = tempKey;
        final JsonElement value = tempValue;
        return switch (type) {
            case "get" -> Optional.of(new GetCommand<>(key));
            // Just try to check nullable and construct the command without having to open a bloc
            case "set" -> Optional.of(
                    Optional.of(commandRequest.getValue())
                            .map(rc -> new SetCommand<>(key, value))
                            .orElseThrow(() -> new IllegalArgumentException("The command type is set but doesn't have any value to set")));
            case "delete" -> Optional.of(new DeleteCommand<>(key));
            case "exit" -> Optional.of(new ExitCommand<>());
            default -> Optional.empty();
        };
    }

    /**
     * Parse the key into and JsonElement. In the context of that application the JsonElement will either be an
     * {@link com.google.gson.JsonArray} or {@link com.google.gson.JsonPrimitive} (String)
     * @param key the key to parse
     * @return {@link JsonElement}
     */
    public static JsonElement parseKey(final String key){
       return gson.fromJson(key, JsonElement.class);
    }
}
