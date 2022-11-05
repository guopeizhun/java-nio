package com.yl.nio.channel.allChat.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/24 13:39
 * @Description:
 */


public class ChatClient {
    public void startClient() throws Exception {
       //连接服务端
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8888));

        //接收服务端数据
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        new Thread(()->{
            for (; ; ) {
                int readChannels = 0;
                try {
                    readChannels = selector.select();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (readChannels == 0) {
                    continue;
                }

                //获取可用的channel
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if (next.isReadable()) {
//                        readOperator(channel, next, selector);
                        //todo

                    }
                }
            }
        }).start();

        //向服务端发送数据
        while (true){
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            channel.write(Charset.forName("UTF-8").encode(msg));
        }


    }

    public static void main(String[] args) throws Exception {
        new ChatClient().startClient();
    }

    private void readOperator(ServerSocketChannel channel, SelectionKey selectionKey, Selector selector) throws Exception {
        //获取已经就绪的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int readLen;
        String msg = "";
        while ((readLen = socketChannel.read(buffer)) > 0) {
            buffer.flip();
            msg += Charset.forName("UTF-8").decode(buffer);
            socketChannel.register(selector, SelectionKey.OP_READ);

            if (msg.length() > 0) {
                System.out.println(msg);
//                castToOtherClient(msg, selector, socketChannel);
            }
        }

    }
}
