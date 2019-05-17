package zxh.demo.ioserver.handler.strategy;

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

    public static Action create(ActionType type) {
        switch (type) {
            case PRINTER:
                return new Printer();
            case ECHOER:
                return new Echoer();
            case HTTP_REQUEST_ANALYZER:
                return new HttpRequestAnalyzer();
            default:
                return new EmptyAction();
        }
    }
}
