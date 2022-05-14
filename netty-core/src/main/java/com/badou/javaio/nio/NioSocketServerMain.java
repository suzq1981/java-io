package com.badou.javaio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class NioSocketServerMain {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(9000), 150);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isValid()) {
                    if (selectionKey.isAcceptable()) {
                        doAccept(selectionKey);
                    }
                    if (selectionKey.isReadable()) {
                        doRead(selectionKey);
                    }
                } else {
                    iterator.remove();
                }
            }
        }

    }

    public static void doRead(SelectionKey key) throws Exception {
        TimeUnit.MILLISECONDS.sleep(300);
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int length = -1;
        try {
            while ((length = channel.read(buffer)) > 0) {
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, length));
                buffer.clear();
            }
            byte[] content = (channel.hashCode() + " Server Mo").getBytes();
            buffer.put(content);
            buffer.flip();
            channel.write(buffer);
        } catch (Exception ex) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void doAccept(SelectionKey key) throws Exception {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = channel.accept();
        if (socketChannel != null) {
            System.out.println("客户端已连接上");
            socketChannel.configureBlocking(false);
            socketChannel.socket().setKeepAlive(true);
            socketChannel.socket().setTcpNoDelay(true);
            socketChannel.register(key.selector(), SelectionKey.OP_READ);

            ByteBuffer buffer = ByteBuffer.allocate(100);
            buffer.put("Welcom to the Heaven.".getBytes());
            buffer.flip();
            socketChannel.write(buffer);
        }
    }
}
