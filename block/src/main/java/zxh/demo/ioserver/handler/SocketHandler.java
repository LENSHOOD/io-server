package zxh.demo.ioserver.handler;

import zxh.demo.ioserver.handler.strategy.Action;
import zxh.demo.ioserver.handler.strategy.ActionFactory;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

public enum SocketHandler {
    /**
     * singleton
     */
    INSTANCE;

    public void handle(Socket socket, ActionFactory.ActionType type) {
        if (socket.isClosed()) {
            return;
        }

        Action action = ActionFactory.create(type);
        try (BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
             BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {

            byte[] inputBytes = new byte[1024];
            while (!action.isStop()) {
                int length = is.read(inputBytes);
                byte[] responseSection = action.doAction(Arrays.copyOf(inputBytes, length));
                if (Objects.nonNull(responseSection)) {
                    os.write(responseSection);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
