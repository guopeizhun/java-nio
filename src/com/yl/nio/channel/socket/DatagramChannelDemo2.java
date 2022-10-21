package com.yl.nio.channel.socket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/2114:17
 * @Description: recieve data
 */

public class DatagramChannelDemo2 {
    public static void main(String[] args) throws Exception {
        DatagramChannel recieveChannel = DatagramChannel.open();
        InetSocketAddress recieveAddress = new InetSocketAddress("localhost",8888);
        recieveChannel.bind(recieveAddress);

        //接收
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            buffer.clear();
            SocketAddress socketAddress = recieveChannel.receive(buffer);
            buffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(Charset.forName("utf-8").decode(buffer));

        }


    }
}
