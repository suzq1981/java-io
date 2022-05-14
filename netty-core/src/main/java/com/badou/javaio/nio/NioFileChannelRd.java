package com.badou.javaio.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannelRd {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("D:/fileChannel.log");

        FileChannel channel = inputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int length = 0;

        while ((length = channel.read(buffer)) != -1) {
            buffer.flip();
            System.out.print(new java.lang.String(buffer.array(), 0, length));
            buffer.clear();
        }
        inputStream.close();
    }
}
