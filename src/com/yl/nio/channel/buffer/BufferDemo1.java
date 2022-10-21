package com.yl.nio.channel.buffer;

import java.nio.ByteBuffer;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/21 15:58
 * @Description: 子缓冲区分片
 */


public class BufferDemo1 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < 10; i++) {
            buffer.put((byte) i);
        }

        //子缓冲区分片
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i,b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.hasRemaining()){
            byte b = buffer.get();
            System.out.println(b);
        }
    }
}
