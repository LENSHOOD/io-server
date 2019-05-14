package zxh.demo.ioserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public enum SocketHandler {
    /**
     * singleton
     */
    INSTANCE;

    public void handle(Socket socket) {
        try {
            if (socket.isClosed()) {
                return;
            }

            InputStream inputStream = socket.getInputStream();
            List<Integer> inputPool = new LinkedList<>();
            int readByte = inputStream.read();
            for (int i=0; readByte != -1; i++) {
                inputPool.add(readByte);

                if (readByte == 0xf || i>10000) {
                    break;
                }
                readByte = inputStream.read();
            }

            OutputStream outputStream = socket.getOutputStream();
            inputPool.forEach(
                    element -> {
                        try {
                            outputStream.write(element);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
