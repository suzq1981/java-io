package com.badou.javaio.nio.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.TimeUnit;

public class PipeMain {

    public static void main(String[] args) throws Exception {
        Pipe pipe = Pipe.open();

        Pipe.SinkChannel sink = pipe.sink();
        Pipe.SourceChannel source = pipe.source();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1024);
                for (int i = 1; i <= 5; i++) {
                    sink.write(ByteBuffer.wrap(("我来自客户端A " + i + "\r\n").getBytes()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1024);
                for (int i = 1; i <= 5; i++) {
                    sink.write(ByteBuffer.wrap(("我来自客户端B " + i + "\r\n").getBytes()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        TimeUnit.SECONDS.sleep(3);

        sink.close();

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int readLength = source.read(readBuffer);
        int time = 0;
        while (readLength > 0) {
            time++;
            System.out.println(new String(readBuffer.array(), 0, readLength));
            readLength = source.read(readBuffer);
        }
        source.close();
        System.out.println("time=" + time);
    }
}
