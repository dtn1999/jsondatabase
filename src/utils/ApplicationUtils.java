package utils;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import shared.InputArgs;
import shared.CommandRequest;

import java.io.*;
import java.util.Objects;
import java.util.Optional;


final public class ApplicationUtils {

    /**
     * Gets the user command arguments and try to parse them according to the {@link InputArgs}
     * @param arguments command line argument send by the user
     * @return {@link InputArgs} the parsed command line arguments in form of an {@link InputArgs} object
     */
    public static InputArgs parse(String[] arguments) {
        InputArgs inputArgs = new InputArgs();
        JCommander.newBuilder()
                .addObject(inputArgs)
                .build()
                .parse(arguments);
        return inputArgs;
    }


    /**
     * Take an object as argument and return its json representation
     * @param object the object to serialize into json
     * @return {@link String} (JSON) representation of the object
     */
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Transform the command line arguments given by the user in the a {@link CommandRequest} object that can then be
     * serialized into json and send to the server for processing
     * @param argv command line arguments of the user
     * @return {@link CommandRequest}
     */
    public static CommandRequest parseCommandLineArgumentsToCommandRequest(String[] argv) {
        InputArgs inputArgs = ApplicationUtils.parse(argv);
        if (Objects.isNull(inputArgs.getFile())) {
            CommandRequest.CommandRequestBuilder builder = new CommandRequest.CommandRequestBuilder();
            builder.type(inputArgs.getType());
            if(!Objects.isNull(inputArgs.getKey())){
               builder.key(new JsonPrimitive(inputArgs.getKey()));
            }
            if (!Objects.isNull(inputArgs.getValue())){
               builder.value(new JsonPrimitive(inputArgs.getValue()));
            }
            return builder.build();
        } else {
            return fromJsonFile(ApplicationConfig.CLIENT_DATA_PATH + inputArgs.getFile(),
                    CommandRequest.class, CommandRequest.builder()
                            .type("unknown").build());
        }
    }

    /**
     * Get a file location and object and save a json representation of the object to the file given by the {@param fileLocation}
     * @param fileLocation the location of the file to store the object (as json)
     * @param object the object to persist into the file
     */
    public static void writeToFile(String fileLocation, Object object) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation));
            writer.write(json);
            writer.close();
        } catch (IOException exception) {
            throw new RuntimeException();
        }
    }

    /**
     * Read Content of the given file
     * @param filLocation file for which we want the content
     * @return {@link String} file content
     */
    public static String readFileContentAsString(String filLocation) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filLocation);
            String fileContent = new String(fileInputStream.readAllBytes());
            fileInputStream.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Read the content of the file given by the file location, and using Gson try to unmarshal it as Object of the given
     * class. If Gson is not able to transform it the specified class the method return the default value passed as
     * argument
     * @param fileLocation file we want to read the content
     * @param clazz the class of the object we want to return
     * @param defaultValue default value in case the file content could not be parsed and transform to and instance of
     *                     clazz by Gson
     * @return {@link T}
     */
    public static <T> T fromJsonFile(String fileLocation, Class<T> clazz, T defaultValue) {
        Gson gson = new Gson();
        T result = gson.fromJson(readFileContentAsString(fileLocation), clazz);
        return Optional.ofNullable(result).orElse(defaultValue);
    }

}
