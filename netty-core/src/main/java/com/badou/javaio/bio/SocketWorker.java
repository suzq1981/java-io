package com.badou.javaio.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWorker extends Thread {

	private Socket socket;

	public SocketWorker(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			byte[] bytes = new byte[1024];
			while (true) {
				int length = in.read(bytes);// 阻塞读取
				if (length != -1) {
					String content = new String(bytes, 0, length);
					System.out.println(this.getId() + "," + this.getName() + ", Content:" + content);
					os.write(this.getName().getBytes());
					os.flush();
				} else {
					break;
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
