package server.core;

import utils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Dispatcher {
    private final Logger log = Logger.getLogger(Dispatcher.class);
    public void dispatch() throws IOException {
        // Get the server socket instance from the application context
        final ServerSocket server = ApplicationContext.getInstance().getServerSocket();
        while (ApplicationContext.getInstance().isRunning()) {
            // we want to execute each request on a separated thread
            // The RequestHandler class is responsible for handling a request
            try{
                Socket socket = server.accept();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                ApplicationContext.getInstance().getExecutorService().submit(new RequestHandler(inputStream, outputStream));
            }catch (SocketException socketException){
               log.error(
                       String.format("The server couldn't accept connection because of the following error: { %s }:{ %s }",
                       socketException.getClass().getSimpleName(), socketException.getMessage()), socketException);
            }
        }
    }
}
