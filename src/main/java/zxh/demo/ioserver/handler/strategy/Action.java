package zxh.demo.ioserver.handler.strategy;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public abstract class Action {

    protected InputStream is;
    protected OutputStream os;

    Action(InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
    }

    public abstract void doAction();
}
