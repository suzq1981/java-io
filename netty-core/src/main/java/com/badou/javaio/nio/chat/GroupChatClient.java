package com.badou.javaio.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GroupChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1000;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            this.selector = Selector.open();
            this.socketChannel = SocketChannel.open();
            this.socketChannel.connect(new InetSocketAddress(HOST, PORT));
            this.socketChannel.configureBlocking(false);
            this.socketChannel.register(selector, SelectionKey.OP_READ);
            this.username = socketChannel.getLocalAddress().toString().substring(1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        final GroupChatClient chatClient = new GroupChatClient();

        new Thread(() -> {
            while (true) {
                try {
                    chatClient.readHandle();
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            chatClient.talk(message);
        }
        scanner.close();
    }

    public void talk(String content) throws IOException {
        String info = this.username + " Say: " + content;
        this.socketChannel.write(ByteBuffer.wrap(info.getBytes()));
    }

    public void readHandle() throws IOException {
        if (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int length = channel.read(buffer);
                    System.out.println(new String(buffer.array(), 0, length));
                }
                iterator.remove();
            }
        }
    }

}
