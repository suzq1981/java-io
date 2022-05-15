package com.badou.javaio.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class GroupChatServer {

    private static final int PORT = 1000;
    private Selector selector = null;
    private ServerSocketChannel serverChannel;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(PORT));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    public void listen() throws Exception {
        while (true) {
            if (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverchannel = (ServerSocketChannel) key.channel();
                        SocketChannel channel = serverchannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                        System.out.println(channel.getRemoteAddress().toString().substring(1) + " 上线 ");
                    }
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        try {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int length = channel.read(buffer);
                            String content = new String(buffer.array(), 0, length);
                            readHandle(key, content);
                        } catch (IOException ex) {
                            System.out.println(channel.getRemoteAddress() + " 离线 ");
                            channel.close();
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }

    private void readHandle(SelectionKey key, String content) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        Iterator<SelectionKey> iterator = selector.keys().iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            if (selectionKey.channel() instanceof ServerSocketChannel || selectionKey.channel() == channel) {
                if (selectionKey.channel() == channel) {
                    System.out.println(content);
                }
                continue;
            }
            if (selectionKey.isValid()) {
                SocketChannel destChannel = (SocketChannel) selectionKey.channel();
                destChannel.write(ByteBuffer.wrap(content.getBytes()));
            }
        }
    }
}
