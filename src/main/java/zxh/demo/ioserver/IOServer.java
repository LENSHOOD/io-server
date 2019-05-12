package zxh.demo.ioserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-11
*/
public class IOServer {

    private ServerSocket serverSocket = null;
    private static final int PORT = 12345;

    public void init() {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            this.serverSocket = serverSocket;
        } catch (IOException e) {
            throw new RuntimeException("Server socket init failed", e);
        }
    }

    public Socket listen() {
        if (Objects.isNull(serverSocket)) {
            throw new RuntimeException("Call init() first please!");
        }

        try (Socket clientSocket = this.serverSocket.accept()) {
            return clientSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            // already closed
        }
    }
}
