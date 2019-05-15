package zxh.demo.ioserver.handler.strategy;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public class ActionFactory {

    public enum ActionType {
        PRINTER, ECHOER, HTTP_REQUEST_ANALYZER;
    }

    private ActionFactory() {}

    public static Action create(ActionType type, InputStream is, OutputStream os) {
        switch (type) {
            case PRINTER:
                return new Printer(is, os);
            case ECHOER:
                return new Echoer(is, os);
            case HTTP_REQUEST_ANALYZER:
                return new HttpRequestAnalyzer(is, os);
            default:
                return new EmptyAction(is, os);
        }
    }
}
