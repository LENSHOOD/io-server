package zxh.demo.ioserver.handler.strategy;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public interface Action {

    boolean isStop();

    byte[] doAction(byte[] inputByteArray);
}
