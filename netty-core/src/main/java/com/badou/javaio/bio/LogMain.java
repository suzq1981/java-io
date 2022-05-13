package com.badou.javaio.bio;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.LockSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMain {

	private static final Logger logger = LoggerFactory.getLogger(LogMain.class);

	public static void main(String[] args) {

		new Thread(() -> {
			while (true) {
				int random = ThreadLocalRandom.current().nextInt(1000, 2000);
				try {
					if (random > 1000 && random < 1010) {
						throw new NullPointerException("random > 1000 && random < 1010");
					}
					if (random > 1010 && random < 1020) {
						throw new ClassNotFoundException("random >C 1010 && random < 1020");
					}
					
					logger.info("Random value is {}", random);
				} catch (Exception ex) {
					logger.info(ex.getMessage(), ex);
				}
				LockSupport.parkNanos(50);
			}
		}, "Badou-Javaio-Log").start();
		
		new Thread(() -> {
			while (true) {
				int random = ThreadLocalRandom.current().nextInt(1000, 2000);
				try {
					if (random > 1590 && random < 1600) {
						throw new IllegalArgumentException("random > 1590 && random < 1600");
					}
					if (random > 1900 && random < 1910) {
						throw new RuntimeException("random > 1900 && random < 1910");
					}
					logger.info("Random value is {}", random);
				} catch (Exception ex) {
					logger.info(ex.getMessage(), ex);
				}
				LockSupport.parkNanos(50);
			}
		}, "Badou-Javaio-Print").start();
	}

}
