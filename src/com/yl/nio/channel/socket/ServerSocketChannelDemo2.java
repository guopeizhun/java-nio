package com.yl.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/2114:00
 * @Description:
 */


public class ServerSocketChannelDemo2 {
    public static void main(String[] args) throws Exception {
        int port = 80;
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("www.baidu.com",port));

        //blocking mode
        sc.configureBlocking(false);

        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        sc.read(buffer);
        sc.close();
        System.out.println("read over");


    }
}
