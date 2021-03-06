package com.badou.javaio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioSocketServerMain {

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9000), 150);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            doAccept(selectionKey);
                        }
                        if (selectionKey.isReadable()) {
                            doRead(selectionKey);
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException ex) {

            }
        }
    }

    public static void doRead(SelectionKey key) throws Exception {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int length = -1;
        while ((length = channel.read(buffer)) > 0) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, length));
            buffer.clear();
        }
        byte[] content = (channel.hashCode() + " Server Mo").getBytes();
        buffer.put(content);
        buffer.flip();
        channel.write(buffer);
    }

    public static void doAccept(SelectionKey key) throws Exception {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = channel.accept();
        if (socketChannel != null) {
            System.out.println("?????????????????????");
            socketChannel.configureBlocking(false);
            socketChannel.socket().setKeepAlive(true);
            socketChannel.socket().setTcpNoDelay(true);
            socketChannel.register(key.selector(), SelectionKey.OP_READ);

            //wrap??????ByteBuffer,position???0,limit???????????????,???????????????buffer??????flip??????
            ByteBuffer buffer = ByteBuffer.wrap("Welcome to the Heaven.".getBytes());
            
            socketChannel.write(buffer);
        }
    }
}
