package zxh.demo.ioserver.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-11
*/
public class IOServer {

    private ServerSocketChannel serverSocketChannel = null;
    private static final int PORT = 12345;

    public void init() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new RuntimeException("Server socket init failed", e);
        }
    }

    public SocketChannel accept() {
        if (Objects.isNull(serverSocketChannel)) {
            throw new RuntimeException("Call init() first please!");
        }

        try {
            return serverSocketChannel.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        try {
            serverSocketChannel.close();
        } catch (IOException e) {
            // already closed
        }
    }
}
