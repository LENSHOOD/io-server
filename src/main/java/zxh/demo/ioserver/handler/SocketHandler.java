package zxh.demo.ioserver.handler;

import zxh.demo.ioserver.handler.strategy.ActionFactory;

import java.io.IOException;
import java.net.Socket;

public enum SocketHandler {
    /**
     * singleton
     */
    INSTANCE;

    public void handle(Socket socket, ActionFactory.ActionType type) {
        try {
            if (socket.isClosed()) {
                return;
            }

            ActionFactory.create(
                        type,
                        socket.getInputStream(),
                        socket.getOutputStream())
                    .doAction();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
