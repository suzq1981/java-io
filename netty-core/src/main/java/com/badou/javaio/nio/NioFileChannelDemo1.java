package com.badou.javaio.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NioFileChannelDemo1 {

	private static final Logger logger = LoggerFactory.getLogger(NioFileChannelDemo1.class);

	public static void main(String[] args) throws Exception {
		logger.info("FileChannel Demo.");
		// 创建一个输出流
		FileOutputStream fos = new FileOutputStream("D:/hello.txt");
		// 通过输出流获取对应的文件channel
		FileChannel fileChannel = fos.getChannel();
		//创建缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		buffer.put("Hello Godson 苏忠清".getBytes());
		
		buffer.flip();
		//把buffer数据写入到通道
		fileChannel.write(buffer);
		
		//关闭流
		fos.close();
	}

}
