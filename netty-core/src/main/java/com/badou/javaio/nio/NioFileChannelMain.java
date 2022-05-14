package com.badou.javaio.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannelMain {

    public static void main(String[] args) throws Exception{

        FileInputStream fis = new FileInputStream("D:/netty-core-2022-05-13.13.log");
        FileOutputStream fos = new FileOutputStream("D:/fileChannel.log");

        FileChannel srcFileChannel = fis.getChannel();
        FileChannel tarFileChannel = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while(srcFileChannel.read(buffer)!=-1){
            buffer.flip();
            tarFileChannel.write(buffer);
            buffer.position(0);
            buffer.limit(buffer.capacity());
        }

        fos.close();
        fis.close();
    }
}
