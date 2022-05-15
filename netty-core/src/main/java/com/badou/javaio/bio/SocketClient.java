package com.badou.javaio.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketClient {

	private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("127.0.0.1", 9000);

		try {
			InputStream in = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			byte[] bytes = new byte[1024];
			while (true) {
				TimeUnit.SECONDS.sleep(1);
				String message = "GodSon " + System.currentTimeMillis();
				os.write(message.getBytes());
				os.flush();
				int length = in.read(bytes);// 阻塞读取
				if (length != -1) {
					String content = new String(bytes, 0, length);
					logger.info("服务端响应：{}", content);
				} else {
					break;
				}
			}
			in.close();
		} finally {
			socket.close();
		}

	}
}
