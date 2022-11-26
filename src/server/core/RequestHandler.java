package server.core;

import com.google.gson.JsonElement;
import database.HashMapBasedDatasource;
import database.JsonFileBasedDataSource;
import database.exceptions.KeyNotFoundException;
import server.core.command.Command;
import server.core.command.CommandController;
import server.core.command.ExitCommand;
import shared.CommandResponse;
import utils.ApplicationUtils;
import utils.CommandUtils;
import utils.Logger;

import java.io.*;
import java.util.Optional;

public class RequestHandler implements Runnable {
    Logger log = Logger.getLogger(RequestHandler.class);
    private final InputStream inputStream;
    private final OutputStream outputStream;
    public RequestHandler(final InputStream inputStream, final OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void run() {
        Optional<Command<String, JsonElement>> optional = Optional.empty();
        try {
            optional = CommandUtils.parseToCommand(((DataInputStream)inputStream).readUTF());
            optional.ifPresent(command -> {
                CommandController<String, JsonElement> controller =
                        new CommandController<>(JsonFileBasedDataSource.getInstance(), command);
                if (command instanceof ExitCommand<String, JsonElement>) {
                    handleExitCommand((DataOutputStream) outputStream);
                } else {
                    handleNotExitCommand(controller, (DataOutputStream) outputStream);
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }


    private void handleExitCommand(DataOutputStream outputStream) {
        String outgoingMessage = ApplicationUtils.toJson(CommandResponse.builder()
                .response("OK"));
        try {
            outputStream.writeUTF(outgoingMessage);
            ApplicationContext.getInstance().shutDown();
        } catch (IOException ioException) {
            log.error(String.format("The following error occurred: %s ", ioException.getMessage()), ioException);
        }
    }

    private void handleNotExitCommand(CommandController<String, JsonElement> controller, DataOutputStream outputStream) {
        CommandResponse.CommandResponseBuilder builder =
                new CommandResponse.CommandResponseBuilder();
        try {
            Optional<JsonElement> response = controller.executeCommand();
            response.ifPresent(builder::value);
            builder.response("OK");
        } catch (KeyNotFoundException e) {
            builder.response("ERROR");
            builder.reason("No such key");
        }
        String outgoingMessage = ApplicationUtils.toJson(builder.build());
        try{
            outputStream.writeUTF(outgoingMessage);
        }catch (IOException ioException){
             log.error(String.format("The following error occurred: %s ", ioException.getMessage()), ioException);
        }
    }
}
