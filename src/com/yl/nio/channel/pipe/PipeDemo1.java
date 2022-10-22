package com.yl.nio.channel.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/22 16:07
 * @Description:
 */


public class PipeDemo1 {
    public static void main(String[] args) throws Exception {
        //获取管道
        Pipe pipe = Pipe.open();
        //获取sink通道
        Pipe.SinkChannel sinkChannel = pipe.sink();
        //创建缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        buffer1.put("abv123".getBytes(StandardCharsets.UTF_8));
        buffer1.flip();

        //写入管道
        sinkChannel.write(buffer1);
        //获取source通道
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        int length = sourceChannel.read(buffer2);
        System.out.println(new String(buffer2.array(), 0, length));

        //关闭通道
        sourceChannel.close();
        sinkChannel.close();
    }
}
