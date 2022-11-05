package com.yl.nio.channel.multiThreading;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author:letg(pz)
 * @Date: 2022/11/5 11:23
 * @Description:
 */


public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8999));
        Thread.sleep(2000);
        sc.write(Charset.defaultCharset().encode("你好"));
        System.in.read();
    }
}
