package zxh.demo.ioserver.handler.strategy;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public class Echoer extends Action {

    Echoer(InputStream is, OutputStream os) {
        super(is, os);
    }

    @Override
    public void doAction() {

    }
}
