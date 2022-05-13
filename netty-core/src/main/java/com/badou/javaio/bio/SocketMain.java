package com.badou.javaio.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketMain {

	private static final Logger logger = LoggerFactory.getLogger(SocketMain.class);

	public static void main(String[] args) {
		logger.info("Start socket server port:{}", 9980);
		ServerSocket server = null;
		try {
			server = new ServerSocket(9980);

			ExecutorService threadPool = Executors.newCachedThreadPool();

			while (true) {
				Socket socket = server.accept();// 阻塞等待客户端连接
				logger.info("连接到客户端，启动线程去处理");
				threadPool.execute(new SocketWorker(socket));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
