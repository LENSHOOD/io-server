package zxh.demo.ioserver.handler.strategy;

import java.util.*;

public class HttpRequestAnalyzer implements Action{

    class RequestLine {
        private String method;
        private String requestTarget;
        private String httpVersion;

        RequestLine(String method, String requestTarget, String httpVersion) {
            this.method = method;
            this.requestTarget = requestTarget;
            this.httpVersion = httpVersion;
        }

        String getMethod() {
            return method;
        }

        String getRequestTarget() {
            return requestTarget;
        }

        String getHttpVersion() {
            return httpVersion;
        }
    }

    private boolean stopFlag = false;
    private boolean isFirstTimeEntry = true;
    private StringBuilder concatBuilder = null;

    private RequestLine requestLine = null;
    private Map<String, String> headers = new HashMap<>();

    @Override
    public boolean isStop() {
        return stopFlag;
    }

    @Override
    public byte[] doAction(byte[] inputByteArray) {

        String[] requestRawLines = new String(inputByteArray).split("\r?\n", -1);
        if (isFirstTimeEntry) {
            requestLine = analyzeRequestLine(requestRawLines[0]);
        }

        List<String> headersRaw = new ArrayList<>();
        for (int i = 0; i < requestRawLines.length; i++) {
            if (isFirstTimeEntry) {
                isFirstTimeEntry = false;
                continue;
            }

            String line = requestRawLines[i];
            if ("".equals(line)) {
                stopFlag = true;
                break;
            }

            headersRaw.add(line);
        }


        if (!Objects.isNull(concatBuilder)) {
            concatBuilder.append(headersRaw.get(0));
            headersRaw.set(0, concatBuilder.toString());
        }

        if (!stopFlag) {
            int lastIndex = headersRaw.size() - 1;
            concatBuilder = new StringBuilder(headersRaw.get(lastIndex));
            headersRaw.remove(lastIndex);
        }

        headers.putAll(analyzeHeaders(headersRaw));

        if (hasContent(headers)) {
            // ignore
        }

        return stopFlag ? buildResponse(requestLine, headers).getBytes() : null;
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
