package com.yl.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/2110:08
 * @Description:
 */


public class FileChannelDemo1 {
    public static void main(String[] args) throws Exception {
        //创建FileChannel
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\86158\\Desktop\\新建文本文档.txt", "rw");
        FileChannel fileChannel = file.getChannel();
        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读取数据到buffer
        int byteRead = fileChannel.read(buffer);
        while (byteRead != -1){
            System.out.println("读取数据");
            //读写转换
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.println((char)buffer.get());
            }
            buffer.clear();
            byteRead = fileChannel.read(buffer);
        }
        System.out.println("操作结束");
    }
}
