package com.yl.nio.channel.socket;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Author:letg(pz)
 * @Date: 2022/11/4 9:57
 * @Description:
 */


public class ClientDemo3 {
    public static void main(String[] args) throws Exception {
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress("localhost", 8999));
        System.out.println("connected");
        String msg = new Scanner(System.in).next();
        open.write(Charset.defaultCharset().encode(msg));

    }
}
