package zxh.demo.ioserver.handler.strategy;

import org.junit.jupiter.api.Test;

import java.io.*;

class HttpRequestAnalyzerTester {

    @Test
    void testDoAction() {
        String testHttpRequest =
                "GET /562f25980001b1b106000338.jpg HTTP/1.1\n" +
                "Host: img.mukewang.com\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36\n" +
                "Accept: image/webp,image/*,*/*;q=0.8\n" +
                "Referer: http://www.imooc.com/\n" +
                "Accept-Encoding: gzip, deflate, sdch\n" +
                "Accept-Language: zh-CN,zh;q=0.8\n" +
                "\n";

        ByteArrayInputStream is = new ByteArrayInputStream(testHttpRequest.getBytes());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Action analyzer = ActionFactory.create(ActionFactory.ActionType.HTTP_REQUEST_ANALYZER, is, os);

        analyzer.doAction();
        String response = new String((os.toByteArray()));
        System.out.println(response);
    }
}
