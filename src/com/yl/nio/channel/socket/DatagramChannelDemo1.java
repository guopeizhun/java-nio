package com.yl.nio.channel.socket;

import jdk.nashorn.internal.ir.WhileNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/2114:10
 * @Description: send Data
 */


public class DatagramChannelDemo1 {
    public static void main(String[] args) throws Exception {
        DatagramChannel sendChannel = DatagramChannel.open();

        InetSocketAddress sendAddress = new InetSocketAddress("localhost",8888);

        while(true) {
            ByteBuffer buffer = ByteBuffer.wrap("发送buffer".getBytes(StandardCharsets.UTF_8));
            sendChannel.send(buffer, sendAddress);
            System.out.println("完成发送");
            Thread.sleep(2000);
        }


    }

}
