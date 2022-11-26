package client;

import shared.CommandRequest;
import utils.ApplicationUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientTemplate {
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public ClientTemplate(DataInputStream inputStream, DataOutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void send(CommandRequest request) throws IOException {
       outputStream.writeUTF(ApplicationUtils.toJson(request));
    }

    public String receive() throws IOException {
      return inputStream.readUTF();
    }
}

