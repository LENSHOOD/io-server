package zxh.demo.ioserver.handler;

import zxh.demo.ioserver.handler.strategy.Action;
import zxh.demo.ioserver.handler.strategy.ActionFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

public enum SocketHandler {
    /**
     * singleton
     */
    INSTANCE;

    private Map<SelectionKey, byte[]> resultMap = new HashMap<>();
    private Action action = null;

    public void build(ActionFactory.ActionType type) {
        if (Objects.isNull(action)) {
            action = ActionFactory.create(type);
        }
    }

    public void handle(Selector socketSelector) {
        if (Objects.isNull(action)) {
            throw new RuntimeException("Socket Handler haven't build yet, call build() first!");
        }

        try {
            if (socketSelector.selectNow() == 0) {
                return;
            }

            Set<SelectionKey> selectionKeys = socketSelector.selectedKeys();
            selectionKeys.forEach(this::handleReady);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReady(SelectionKey key) {
        if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.attachment();
            byte[] result = new byte[0];
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int length = socketChannel.read(buffer);
                while (length > 0 && !action.isStop()) {
                    buffer.flip();
                    byte[] resultSection = action.doAction(Arrays.copyOf(buffer.array(), length));
                    result = new byte[result.length + resultSection.length];
                    System.arraycopy(resultSection, 0, result, result.length - resultSection.length, resultSection.length);
                    buffer.clear();
                    length = socketChannel.read(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            resultMap.put(key, result);
        }

        if (key.isWritable()) {
            if (!resultMap.containsKey(key)) {
                return;
            }

            ByteBuffer buffer = ByteBuffer.wrap(resultMap.get(key));

            SocketChannel socketChannel = (SocketChannel) key.attachment();
            try {
                socketChannel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            resultMap.remove(key);
        }
    }
}
