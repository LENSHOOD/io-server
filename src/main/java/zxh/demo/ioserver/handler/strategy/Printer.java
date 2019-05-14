package zxh.demo.ioserver.handler.strategy;

import java.io.*;
import java.util.Objects;

/**
 * 
 * @author zhangxuhai
 * @date 2019-05-14
*/
public class Printer extends Action {

    Printer(InputStream is, OutputStream os) {
        super(is, os);
    }

    @Override
    public void doAction() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String info = reader.readLine();
            while (Objects.nonNull(info)) {
                System.out.println(info);
                info = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
