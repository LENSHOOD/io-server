package zxh.demo.ioserver;

import zxh.demo.ioserver.handler.strategy.ActionFactory;

import java.util.Scanner;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-15
*/
public class Starter {
    public static void main(String[] args) {
        Coordinator coordinator = new Coordinator();
        coordinator.init(ActionFactory.ActionType.HTTP_REQUEST_ANALYZER);
        coordinator.startUp();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Anything To Stop.");
        scanner.hasNext();

        coordinator.stop();
    }
}
