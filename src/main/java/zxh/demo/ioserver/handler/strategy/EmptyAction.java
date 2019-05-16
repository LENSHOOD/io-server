package zxh.demo.ioserver.handler.strategy;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public class EmptyAction implements Action {
    class EmptyActionException extends RuntimeException {
        EmptyActionException() {
            super("No action provided, please create action with valid action type!");
        }
    }

    @Override
    public boolean isStop() {
        return false;
    }

    @Override
    public byte[] doAction(byte[] inputByteArray) {
        throw new EmptyActionException();
    }

}
