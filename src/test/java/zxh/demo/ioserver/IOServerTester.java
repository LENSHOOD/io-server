package zxh.demo.ioserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void testListen() {

    }
}
