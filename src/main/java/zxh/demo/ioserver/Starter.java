package zxh.demo.ioserver;

import java.util.Scanner;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-15
*/
public class Starter {
    public static void main(String[] args) {
        Coordinator coordinator = new Coordinator();
        coordinator.init();
        coordinator.startUp();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Anything To Stop.");
        while (!scanner.hasNext());

        coordinator.stop();
    }
}
