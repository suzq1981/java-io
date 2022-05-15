package com.badou.javaio.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class NioSocketClientMain {

    public static void main(String[] args) throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.socket().connect(new InetSocketAddress("127.0.0.1", 9000));
        channel.configureBlocking(false);

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    doRead(key);
                }
            }
        }
    }

    public static void doRead(SelectionKey selectionKey) throws Exception {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();

        int length = -1;
        while ((length = channel.read(buffer)) > 0) {
            buffer.flip();
            System.out.println(System.currentTimeMillis() + " " + new String(buffer.array(), 0, length));
        }

        buffer.clear();
        buffer.put("I am Godson 001".getBytes());
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
        TimeUnit.SECONDS.sleep(1);
    }
}
