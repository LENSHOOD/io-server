package zxh.demo.ioserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CoordinatorTester {

    private Coordinator coordinator = null;

    @BeforeEach
    void preInit() {
        coordinator = new Coordinator();
        coordinator.init();
    }

    @AfterEach
    void postTest() {
        coordinator.stop();
    }

    @Test
    void testStartup() {
        coordinator.startUp();
        try(Socket testSocket = new Socket("127.0.0.1", 12345)) {
            OutputStream os = testSocket.getOutputStream();
            InputStream is = testSocket.getInputStream();

            byte[] expectedMsg = {0xa, 0xb, 0xf};
            os.write(expectedMsg);

            Thread.sleep(1000);

            byte[] actualMsg = {0x0, 0x0, 0x0};
            is.read(actualMsg);

            Assertions.assertArrayEquals(expectedMsg, actualMsg);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
