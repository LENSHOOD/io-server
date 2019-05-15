package zxh.demo.ioserver.handler.strategy;

import java.io.*;
import java.util.*;

public class HttpRequestAnalyzer extends Action{

    class RequestLine {
        private String method;
        private String requestTarget;
        private String httpVersion;

        public RequestLine(String method, String requestTarget, String httpVersion) {
            this.method = method;
            this.requestTarget = requestTarget;
            this.httpVersion = httpVersion;
        }

        public String getMethod() {
            return method;
        }

        public String getRequestTarget() {
            return requestTarget;
        }

        public String getHttpVersion() {
            return httpVersion;
        }
    }

    HttpRequestAnalyzer(InputStream is, OutputStream os) {
        super(is, os);
    }

    @Override
    public void doAction() {
        try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))
        ) {
            RequestLine requestLine = analyzeRequestLine(reader.readLine());

            List<String> headersRaw = new ArrayList<>();
            while (true) {
                String line = reader.readLine();
                if ("".equals(line)) {
                    break;
                }

                headersRaw.add(line);
            }
            Map<String, String> headers = analyzeHeaders(headersRaw);

            if (hasContent(headers)) {
                // ignore
            }

            String response = buildResponse(requestLine, headers);
            writer.write(response, 0, response.length());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private RequestLine analyzeRequestLine(String requestLineRaw) {
        String[] requestLineArray = requestLineRaw.split(" ");
        return new RequestLine(requestLineArray[0], requestLineArray[1], requestLineArray[2]);
    }

    private Map<String, String> analyzeHeaders(List<String> headersRaw) {
        Map<String, String> resultMap = new HashMap<>();
        headersRaw.forEach(
                header -> {
                    String[] headerKV = header.split(":");
                    resultMap.put(headerKV[0].trim(), headerKV[1].trim());
                });

        return resultMap;
    }

    private boolean hasContent(Map<String, String> headers) {
        return !(Objects.isNull(headers) || !headers.containsKey("Content-Length"));
    }

    private String buildResponse(RequestLine requestLine, Map<String, String> headers) {
        String contentResponse = String.format(
                "{\"method\":\"%s\",\"request-url\":\"%s\",\"http-version\":\"%s\"}",
                requestLine.getMethod(),
                requestLine.getRequestTarget(),
                requestLine.getHttpVersion()
        );

        return
                "HTTP/1.1 200 OK"
                        + "\nDate: " + Calendar.getInstance(TimeZone.getDefault()).getTime()
                        + "\nContent-Type: application/json"
                        + "\nContent-Length: " + contentResponse.length()
                        + "\nAccess-Control-Allow-Origin: *"
                        + "\n\n"
                        + contentResponse;

    }
}
