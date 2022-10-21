package com.yl.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/2113:44
 * @Description:
 */


public class ServerSocketChannelDemo1 {
    public static void main(String[] args) throws Exception {
        int port = 8888;

        //buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        //ServerSocketCHannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //bind
        ssc.socket().bind(new InetSocketAddress(port));
        //设置非阻塞模式
        ssc.configureBlocking(false);

        //listen to connnection
        while (true){
            System.out.println("waiting for connection");
            SocketChannel sc = ssc.accept();
            if(null == sc){
                System.out.println("no connection");
                Thread.sleep(2000);
            }else {
                System.out.println("get connection from"+sc.socket().getRemoteSocketAddress());
                buffer.rewind();//指针0
                sc.write(buffer);
                sc.close();
            }

        }
    }
}
