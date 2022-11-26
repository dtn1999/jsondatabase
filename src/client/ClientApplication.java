package client;

import shared.CommandRequest;
import utils.ApplicationUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientApplication {

    private ClientApplication(){

    }

    public static void run(final String serverHost, final int serverPort, String ...argv){
        try (Socket client = new Socket(InetAddress.getByName(serverHost), serverPort);
             DataInputStream inputStream = new DataInputStream(client.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(client.getOutputStream())
        ) {
            System.out.println("Client started!");
            // the client template is responsible to send and receive data to the server
            ClientTemplate clientTemplate = new ClientTemplate(inputStream, outputStream);
            CommandRequest request = ApplicationUtils.parseCommandLineArgumentsToCommandRequest(argv);
            String message = ApplicationUtils.toJson(request);

            System.out.printf("Sent: %s\n", message);
            clientTemplate.send(request);
            String inComingMessage = clientTemplate.receive();
            System.out.printf("Received: %s\n", inComingMessage);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
