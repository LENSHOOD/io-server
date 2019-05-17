package zxh.demo.ioserver;

import zxh.demo.ioserver.handler.SocketHandler;
import zxh.demo.ioserver.handler.strategy.ActionFactory;
import zxh.demo.ioserver.server.IOServer;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Coordinator {

    private IOServer ioServer = null;

    private ExecutorService taskController = null;
    private ExecutorService taskProcessor = null;
    private Selector socketSelector = null;

    private boolean stopFlag = false;
    private ActionFactory.ActionType actionType = null;

    public void init(ActionFactory.ActionType actionType) {
        ioServer = new IOServer();
        ioServer.init();

        taskController = Executors.newSingleThreadExecutor();
        taskProcessor = Executors.newSingleThreadExecutor();
        try {
            socketSelector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException("Error created selector.", e);
        }

        this.actionType = actionType;
    }

    public void startUp() {
        taskController.execute(this::listen);
        taskProcessor.execute(this::process);
    }

    public void stop() {
        stopFlag = true;

        taskProcessor.shutdown();
        taskController.shutdown();

        socketSelector.keys().forEach(
                key -> {
                    try {
                        ((SocketChannel) key.attachment()).close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
        );

        ioServer.destroy();
    }

    private void listen() {
        while (!stopFlag) {
            SocketChannel acceptedSocket = ioServer.accept();
            if (Objects.isNull(acceptedSocket)) {
                continue;
            }

            try {
                acceptedSocket.configureBlocking(false);
                acceptedSocket.register(socketSelector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, acceptedSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void process() {
        SocketHandler.INSTANCE.build(actionType);
        while (!stopFlag) {
            SocketHandler.INSTANCE.handle(socketSelector);
        }
    }
}
