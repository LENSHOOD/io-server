package zxh.demo.ioserver.handler.strategy;

import java.io.*;
import java.util.Objects;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public class Echoer extends Action {

    private static final int STOP_SIGN = -0x80;

    Echoer(InputStream is, OutputStream os) {
        super(is, os);
    }

    @Override
    public void doAction() {
        try(
                BufferedInputStream inputStream = new BufferedInputStream(is);
                BufferedOutputStream outputStream = new BufferedOutputStream(os)) {

            byte byteValue = (byte) inputStream.read();
            while (byteValue != -1) {
                outputStream.write(byteValue);
                if (byteValue == STOP_SIGN) {
                    break;
                }
                byteValue = (byte) inputStream.read();
            }

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
