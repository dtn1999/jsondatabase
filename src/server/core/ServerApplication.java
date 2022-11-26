package server.core;

import utils.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Objects;

public class ServerApplication {
    private final Logger log = Logger.getLogger(ServerApplication.class);
    private final String address;
    private final int port;
    public static void run(String address, int port){
        new ServerApplication(address, port).start();
    }
    private ServerApplication(final String address, final int port) {
        this.address = address;
        this.port = port;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            log.info("Server started!");
            ApplicationContext.getInstance().setServerSocket(server);
            new Dispatcher().dispatch();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ServerBuilder builder() {
        return new ServerBuilder();
    }

    public static class ServerBuilder {
        private String address;
        private Integer port;

        public ServerBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ServerBuilder port(Integer port) {
            this.port = port;
            return this;
        }

        public void validate() {
            if (Objects.isNull(port) || Objects.isNull(address)) {
                throw new IllegalArgumentException();
            }
        }

        public ServerApplication build() {
            validate();
            return new ServerApplication(address, port);
        }
    }
}
