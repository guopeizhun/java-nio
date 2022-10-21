package com.yl.nio.channel.buffer;

import java.nio.ByteBuffer;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/21 16:05
 * @Description:
 */


public class BufferDemo2 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < 10; i++) {
            buffer.put((byte) i);
        }
        //创建只读缓冲区
        ByteBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();

        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i,b);
        }

        asReadOnlyBuffer.position(0);
        asReadOnlyBuffer.limit(asReadOnlyBuffer.capacity());

        //数据会随着缓冲区的数据变化而变化
        for (int i = 0; i < asReadOnlyBuffer.capacity(); i++) {
            System.out.println(asReadOnlyBuffer.get());
        }
    }
}
