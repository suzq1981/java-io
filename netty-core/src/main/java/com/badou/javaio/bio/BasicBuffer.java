package com.badou.javaio.bio;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicBuffer {

	private static final Logger logger = LoggerFactory.getLogger(BasicBuffer.class);

	public static void main(String[] args) {

		IntBuffer intBuffer = IntBuffer.allocate(5);

		// 向buffer存数据
		for (int i = 10; i < 15; i++) {
			intBuffer.put(i);
		}

		// 从buffer取数据
		// flip 是对buffer进行读写切换

		intBuffer.flip();
		while (intBuffer.hasRemaining()) {
			logger.info("buffer value: {}", intBuffer.get());
		}

	}

}
