package zxh.demo.ioserver.handler.strategy;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public class EmptyAction extends Action {
    class EmptyActionException extends RuntimeException {
        EmptyActionException() {
            super("No action provided, please create action with valid action type!");
        }
    }

    EmptyAction(InputStream is, OutputStream os) {
        super(is, os);
    }

    @Override
    public void doAction() {
        throw new EmptyActionException();
    }
}
