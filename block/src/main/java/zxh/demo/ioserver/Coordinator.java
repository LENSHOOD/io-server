package zxh.demo.ioserver;

import zxh.demo.ioserver.handler.SocketHandler;
import zxh.demo.ioserver.handler.strategy.ActionFactory;
import zxh.demo.ioserver.server.IOServer;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Coordinator {

    private IOServer ioServer = null;

    private ExecutorService taskController = Executors.newSingleThreadExecutor();
    private ExecutorService taskPool = Executors.newCachedThreadPool();
    private boolean stopFlag = false;

    private ActionFactory.ActionType actionType = null;

    private List<Socket> sockets = new ArrayList<>();

    public void init(ActionFactory.ActionType actionType) {
        ioServer = new IOServer();
        ioServer.init();

        this.actionType = actionType;
    }

    public void startUp() {
        taskController.execute(this::listen);
    }

    public void stop() {
        stopFlag = true;

        taskPool.shutdown();
        taskController.shutdown();

        sockets.forEach(socket -> {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ioServer.destroy();
    }

    private void listen() {
        while (true) {
            if (stopFlag) {
                break;
            }

            try {
                Socket acceptedSocket = ioServer.accept();
                taskPool.execute(
                        () -> SocketHandler.INSTANCE.handle(acceptedSocket, actionType)
                );
                sockets.add(acceptedSocket);
            } catch (SocketTimeoutException e) {
                break;
            }

        }
    }
}
