package com.badou.javaio.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannelDemo2 {

	public static void main(String[] args) throws Exception {

		FileInputStream fis = new FileInputStream("D:/netty-core-2022-05-13.13.log");
		FileOutputStream fos = new FileOutputStream("D:/hello.log");

		FileChannel srcChannel = fis.getChannel();
		FileChannel tarChannel = fos.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		while (srcChannel.read(buffer) != -1) {
			buffer.flip();

			// byte[] bytes = buffer.array();
			// String content = new String(bytes, 0, buffer.limit());
			// System.out.print(content);

			tarChannel.write(buffer);
			buffer.clear();
		}
		
		fos.close();
		fis.close();

	}

}
