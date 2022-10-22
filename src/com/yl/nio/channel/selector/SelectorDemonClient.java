package com.yl.nio.channel.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/22 14:47
 * @Description: 客户端
 */


public class SelectorDemonClient {
    public static void main(String[] args) throws Exception {
        //获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8888));
        //非阻塞
        socketChannel.configureBlocking(false);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("客户端开启");
        //写入buffer
        while(true){
            Scanner sc = new Scanner(System.in);
            String data = sc.next();
            data += new Date().toString()+"->"+data;
            buffer.put(data.getBytes(StandardCharsets.UTF_8));
            //模式切换
            buffer.flip();
            //写入通道
            socketChannel.write(buffer);
            //清空关闭
//            socketChannel.close();
            buffer.clear();
        }
    }
}
