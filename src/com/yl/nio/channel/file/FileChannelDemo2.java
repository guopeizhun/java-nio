package com.yl.nio.channel.file;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/2110:23
 * @Description: 通过向buffer中写数据，然后使用channel读取buffer中的数据写入文件
 */


public class FileChannelDemo2 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\86158\\Desktop\\新建文本文档.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String str = "abc123";
        buffer.clear();

        //写入数据
        buffer.put(str.getBytes());

        //读写转换
        buffer.flip();
        while (buffer.hasRemaining()){
            channel.write(buffer);
        }
        System.out.println("操作结束");

        //关闭channel
        channel.close();

    }
}
