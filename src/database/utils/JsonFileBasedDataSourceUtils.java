package database.utils;


import com.google.gson.*;
import database.exceptions.KeyNotFoundException;
import utils.ApplicationConfig;
import utils.ApplicationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class JsonFileBasedDataSourceUtils {

    public static JsonObject readFileDatabase() {
        return ApplicationUtils
                .fromJsonFile(
                        ApplicationConfig.DB_FILE_RELATIVE_LOCATION,
                        JsonObject.class,
                        new JsonObject());
    }

    public static List<String> parseKey(String key) {
        final List<String> result = new ArrayList<>();
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(key, JsonElement.class);
        if (jsonElement.isJsonPrimitive()) {
            result.add(jsonElement.getAsString());
        } else {
            ((JsonArray) jsonElement).forEach(element -> {
                result.add(element.getAsString());
            });
        }
        return result;
    }

    public static JsonElement getFieldFromJson(JsonObject root, List<String> fieldPath) {
        if (fieldPath.size() == 1) {
            if (!(root.has(fieldPath.get(0)))) {
                throw new KeyNotFoundException();
            }
            return root.get(fieldPath.get(0));
        }
        JsonElement result = root.get(fieldPath.get(0));
        for (int i = 1; i < fieldPath.size(); i++) {
            String field = fieldPath.get(i);
            if (!((JsonObject) result).has(field)) {
                throw new KeyNotFoundException();
            }
            result = ((JsonObject) result).get(field);
        }
        return result;
    }


    public static void setFieldInJson(JsonObject root, List<String> fieldPath, JsonElement value) {
        if (!fieldPath.isEmpty()) {
            try {
                JsonObject parentOfField = findParentOfFieldInJson(root, fieldPath);
                String fieldToSet = fieldPath.get(fieldPath.size() - 1);
                parentOfField.add(fieldToSet, value);
            } catch (KeyNotFoundException e) {
                String key = e.getKey();
                int indexOfKey = fieldPath.indexOf(key);
                // find parent of key

                JsonObject parentOfKey = root;
                // navigate till the parent of key
                for (int i = 0; i < indexOfKey; i++) {
                    parentOfKey = (JsonObject) root.get(fieldPath.get(i));
                }
                int indexOfLastElement = fieldPath.size() - 1;
                for (int i = indexOfKey; i < indexOfLastElement; i++) {
                    String field = fieldPath.get(i);
                    parentOfKey.add(field, new JsonObject());
                    parentOfKey = (JsonObject) parentOfKey.get(field);
                }
                parentOfKey.add(fieldPath.get(indexOfLastElement), value);
            }
        }
    }

    public static void removeFieldFomJson(JsonObject root, List<String> fieldPath) {
        if (!fieldPath.isEmpty()) {
            JsonObject parentOfField = (JsonObject) findParentOfFieldInJson(root, fieldPath);
            String fieldToSet = fieldPath.get(fieldPath.size() - 1);
            parentOfField.remove(fieldToSet);
        }
    }

    private static JsonObject findParentOfFieldInJson(JsonObject root, List<String> fieldPath) {
        if (fieldPath.size() < 1) {
            throw new IllegalArgumentException("The path to the field you are interested in must not be empty");
        }

        String rootKey = fieldPath.get(0);

        if (!(root.has(rootKey))) {
            throw new KeyNotFoundException(rootKey);
        }

        JsonElement result = root;
        int indexOfLastElement = fieldPath.size() - 1;
        // we need to go just until the parent of the field we want to set
        for (int i = 0; i < indexOfLastElement; i++) {
            String field = fieldPath.get(i);
            if (Objects.isNull(result) || !((JsonObject) result).has(field)) {
                throw new KeyNotFoundException(field);
            }
            result = ((JsonObject) result).get(field);
        }
        // check if the field that we want to set exist
        String fieldToSet = fieldPath.get(indexOfLastElement);
        if (Objects.isNull(result) || !((JsonObject) result).has(fieldToSet)) {
            throw new KeyNotFoundException(fieldToSet);
        }
        return (JsonObject) result;
    }
}
