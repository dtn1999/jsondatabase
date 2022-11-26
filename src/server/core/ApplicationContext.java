package server.core;

import utils.Logger;

import java.net.ServerSocket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ApplicationContext {
    private final Logger log = Logger.getLogger(ApplicationContext.class);
    private volatile boolean shutdown = false;
    private static ApplicationContext INSTANCE;

    private final ExecutorService executorService;
    private ServerSocket server;
    private ApplicationContext(){
        // get the number of processors
        int numberOfAvailableProcessor = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(numberOfAvailableProcessor);
    }

    public void setServerSocket(final ServerSocket serverSocket){
        this.server = serverSocket;
    }
    public static ApplicationContext getInstance() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new ApplicationContext();
        }
        return INSTANCE;
    }

    public synchronized void shutDown() {
        shutdown = true;
        executorService.shutdown();
        try {
            server.close();
            Thread.currentThread().interrupt();
            boolean e = executorService.awaitTermination(10, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error(String.format("The following error occurred while shutting down the server. { %s }:{ %s}",
                    e.getClass().getSimpleName(), e.getMessage()), e);
        }
    }

    public synchronized boolean isRunning() {
        return !shutdown;
    }

    public ExecutorService getExecutorService(){
        return executorService;
    }

    public boolean getShutdown() {
        return shutdown;
    }

    public ServerSocket getServerSocket() {
        if(Objects.isNull(server)){
            throw new IllegalStateException("A null server socket cannot be return");
        }
        return server;
    }
}
