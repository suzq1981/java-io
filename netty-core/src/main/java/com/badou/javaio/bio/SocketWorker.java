package com.badou.javaio.bio;

import java.io.IOException;
import java.io.InputStream;
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
			byte[] bytes = new byte[1024];
			while (true) {
				int length = in.read(bytes);
				if (length != -1) {
					String content = new String(bytes, 0, length);
					System.out.println(this.getId() + "," + this.getName() + ", Content:" + content);
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
