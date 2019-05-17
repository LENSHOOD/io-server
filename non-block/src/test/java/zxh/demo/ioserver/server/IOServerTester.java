package zxh.demo.ioserver.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zxh.demo.ioserver.server.IOServer;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-11
*/
class IOServerTester {

    private IOServer ioServer;

    @BeforeEach
    void preInit() {
        ioServer = new IOServer();
        ioServer.init();
    }

    @AfterEach
    void postTest() {
        ioServer.destroy();
    }

    @Test
    void testAccept() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Socket testSocket = new Socket("127.0.0.1", 12345);
                testSocket.getOutputStream().write(0x57);
                testSocket.close();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();

        int result;
        while (true) {
            try(SocketChannel acceptedSocketChannel = ioServer.accept()) {
                if (Objects.isNull(acceptedSocketChannel)) {
                    return;
                }

                ByteBuffer byteBuffer = ByteBuffer.allocate(1);
                acceptedSocketChannel.read(byteBuffer);

                byteBuffer.flip();
                result = byteBuffer.get();

                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Assertions.assertEquals(0x57, result);
    }
}
