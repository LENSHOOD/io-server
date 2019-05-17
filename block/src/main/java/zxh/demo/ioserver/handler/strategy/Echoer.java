package zxh.demo.ioserver.handler.strategy;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public class Echoer implements Action {

    private boolean stopFlag = false;
    private static final int STOP_SIGN = -0x80;

    @Override
    public boolean isStop() {
        return stopFlag;
    }

    @Override
    public byte[] doAction(byte[] inputByteArray) {
        for (byte b : inputByteArray) {
            if (b == STOP_SIGN) {
                stopFlag = true;
            }
        }
        return inputByteArray;
    }
}
