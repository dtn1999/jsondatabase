package client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.utils.JsonFileBasedDataSourceUtils;
import utils.ApplicationConfig;

import java.util.List;

public class Main {

    public static void main(String[] argv) {
        /*
        String storeFileContent = "{}";
        Gson gson = new Gson();
        JsonObject store = gson.fromJson(storeFileContent, JsonObject.class);
        List<String> fieldPath = List.of("person1", "inside1", "inside2");
        String stringValue = "{\n" +
                "  \"id1\": 12,\n" +
                "  \"id2\": 14\n" +
                "}";
        JsonObject value = gson.fromJson(stringValue, JsonObject.class);
        JsonFileBasedDataSourceUtils.setFieldInJson(store, fieldPath, value);
         */
        ClientApplication.run(ApplicationConfig.SERVER_HOST, ApplicationConfig.SERVER_PORT, argv);
    }

}
