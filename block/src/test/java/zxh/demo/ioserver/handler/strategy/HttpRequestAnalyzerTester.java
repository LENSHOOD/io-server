package zxh.demo.ioserver.handler.strategy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

class HttpRequestAnalyzerTester {

    private String testHttpRequest =
            "GET /562f25980001b1b106000338.jpg HTTP/1.1\n" +
                    "Host: img.mukewang.com\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36\n" +
                    "Accept: image/webp,image/*,*/*;q=0.8\n" +
                    "Referer: http://www.imooc.com/\n" +
                    "Accept-Encoding: gzip, deflate, sdch\n" +
                    "Accept-Language: zh-CN,zh;q=0.8\n" +
                    "\n";

    @Test
    void testDoAction() {
        Action analyzer = ActionFactory.create(ActionFactory.ActionType.HTTP_REQUEST_ANALYZER);
        String response = new String(analyzer.doAction(testHttpRequest.getBytes()));
        System.out.println(response);
    }

    @Test
    void testDoActionWithMultipleCall() {
        byte[] testHttpRequestBytes = testHttpRequest.getBytes();

        Action analyzer = ActionFactory.create(ActionFactory.ActionType.HTTP_REQUEST_ANALYZER);
        int total = testHttpRequestBytes.length;
        int step = total/6;
        for (int i = 0; i < total; i += step) {
            int copyLength = (total - i) < step ? total - i : step;
            byte[] responseSection = analyzer.doAction(Arrays.copyOfRange(testHttpRequestBytes, i, i + copyLength));
            if (Objects.nonNull(responseSection)) {
                System.out.println(new String(responseSection));
            }
        }
    }
}
