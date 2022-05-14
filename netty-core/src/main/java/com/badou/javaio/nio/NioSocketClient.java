package com.badou.javaio.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class NioSocketClient {

	public static void main(String[] args) throws Exception {
		SocketChannel channel = SocketChannel.open();
		channel.connect(new InetSocketAddress("127.0.0.1", 9000));
		channel.configureBlocking(false);

		Selector selector = Selector.open();
		channel.register(selector, SelectionKey.OP_CONNECT);
		channel.register(selector, SelectionKey.OP_READ);

		while (true) {
			selector.select();
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				if (selectionKey.isValid()) {
					if (selectionKey.isReadable()) {
						doRead(selectionKey);
					}
				}
			}
		}
	}

	public static void doRead(SelectionKey key) throws Exception {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int length = 0;
		while ((length = channel.read(buffer)) != 0) {
			buffer.flip();
			byte[] bytes = new byte[length];
			buffer.get(bytes);
			System.out.println(new String(bytes));
			buffer.clear();
		}
		TimeUnit.SECONDS.sleep(1);
		buffer.clear();
		byte[] content = ("God Son").getBytes();
		buffer.put(content);
		buffer.flip();
		
		channel.write(buffer);
	}

}
