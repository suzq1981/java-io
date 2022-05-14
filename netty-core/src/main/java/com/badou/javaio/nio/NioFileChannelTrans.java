package com.badou.javaio.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NioFileChannelTrans {

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("D:/from.jpg");
        FileOutputStream fos = new FileOutputStream("D:/to.jpg");

        FileChannel srcChannel = fis.getChannel();
        FileChannel destChannel = fos.getChannel();

        srcChannel.transferTo(0, srcChannel.size(), destChannel);

        srcChannel.close();
        destChannel.close();
        fis.close();
        fos.close();

    }
}
