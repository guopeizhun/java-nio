package com.yl.nio.channel.buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/21 16:10
 * @Description: 直接缓冲区复制文件
 */


public class BufferDemon3 {
    public static void main(String[] args) throws Exception {
        //直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        FileInputStream in = new FileInputStream("C:\\testFile\\fileA.txt");
        FileChannel inChannel = in.getChannel();

        FileOutputStream out = new FileOutputStream("C:\\testFile\\fileB.txt");
        FileChannel outChannel = out.getChannel();

        while (true) {
            buffer.clear();
            int read = inChannel.read(buffer);
            if (read == -1) {
                break;
            }
            buffer.flip();
            outChannel.write(buffer);
        }
    }
}
