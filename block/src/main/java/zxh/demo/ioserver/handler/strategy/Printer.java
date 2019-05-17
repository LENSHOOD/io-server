package zxh.demo.ioserver.handler.strategy;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public class Printer implements Action {

    @Override
    public boolean isStop() {
        return false;
    }

    @Override
    public byte[] doAction(byte[] inputByteArray) {
        System.out.println(new String(inputByteArray));
        return new byte[0];
    }
}
