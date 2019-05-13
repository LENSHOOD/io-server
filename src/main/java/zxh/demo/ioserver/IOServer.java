package zxh.demo.ioserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-11
*/
public class IOServer {

    private ServerSocket serverSocket = null;
    private static final int PORT = 12345;
    private static final int TIME_OUT = 1000;

    public void init() {
        try {
            serverSocket = new ServerSocket(PORT);
            this.serverSocket.setSoTimeout(TIME_OUT);
        } catch (IOException e) {
            throw new RuntimeException("Server socket init failed", e);
        }
    }

    public Socket accept() throws SocketTimeoutException {
        if (Objects.isNull(serverSocket)) {
            throw new RuntimeException("Call init() first please!");
        }

        try {
            return serverSocket.accept();
        } catch (SocketTimeoutException timeoutE) {
            throw  timeoutE;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            // already closed
        }
    }
}
