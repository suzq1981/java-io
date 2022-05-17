package com.badou.javaio.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.Arrays;

public class NetByteBuf {

    public static void main(String[] args) {

        ByteBuf buf = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }

        buf.setIndex(2, 8);
        buf.writeByte(100);
        buf.writeByte(101);

        byte[] dst = new byte[10];
        buf.readBytes(dst, 0, buf.writerIndex() - buf.readerIndex());
        System.out.println(Arrays.toString(dst) + ", writerIndex=" + buf.writerIndex() + ", readerIndex=" + buf.readerIndex());

        for (int i = 0; i < buf.capacity(); i++) {
            System.out.print(buf.getByte(i) + " ");
        }
        System.out.println();

        ByteBuf buffer = Unpooled.copiedBuffer("Hello Godson 苏忠清", CharsetUtil.UTF_8);
        if (buffer.hasArray()) {
            byte[] content = buffer.array();
            System.out.println(new String(content, CharsetUtil.UTF_8));
            System.out.println("capacity: " + buffer.capacity());
            buffer.capacity(80);
            System.out.println("new capacity: " + buffer.capacity());
        }

    }
}
